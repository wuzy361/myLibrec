
package net.librec.data.convertor;

import com.google.common.collect.*;
import net.librec.math.structure.SparseMatrix;
import net.librec.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TextDataConvertor extends AbstractDataConvertor {

    
    private static final Log LOG = LogFactory.getLog(TextDataConvertor.class);

    
    private static final int BSIZE = 1024 * 1024;

    
    private static final String DATA_COLUMN_DEFAULT_FORMAT = "UIR";

    
    private String dataColumnFormat;

    
    private String inputDataPath;

    
    private double binThold = -1.0;

    
    private BiMap<String, Integer> userIds, itemIds;

    
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    
    private float loadFilePathRate;

    
    private float loadDataFileRate;

    
    private float loadAllFileRate;

    
    public TextDataConvertor(String inputDataPath) {
        this(DATA_COLUMN_DEFAULT_FORMAT, inputDataPath, -1.0);
    }

    
    public TextDataConvertor(String dataColumnFormat, String inputDataPath) {
        this(dataColumnFormat, inputDataPath, -1.0);
    }

    
    public TextDataConvertor(String dataColumnFormat, String inputDataPath, double binThold) {
        this.dataColumnFormat = dataColumnFormat;
        this.inputDataPath = inputDataPath;
        this.binThold = binThold;
    }

    
    public TextDataConvertor(String dataColumnFormat, String inputDataPath, double binThold,
                             BiMap<String, Integer> userIds, BiMap<String, Integer> itemIds) {
        this(dataColumnFormat, inputDataPath, binThold);
        this.userIds = userIds;
        this.itemIds = itemIds;
    }

    
    public void processData() throws IOException {
        readData(dataColumnFormat, inputDataPath, binThold);
    }

    
    private void readData(String dataColumnFormat, String inputDataPath, double binThold) throws IOException {
        LOG.info(String.format("Dataset: %s", StringUtil.last(inputDataPath, 38)));
        // Table {row-id, col-id, rate}
        Table<Integer, Integer, Double> dataTable = HashBasedTable.create();
        // Table {row-id, col-id, timestamp}
        Table<Integer, Integer, Long> timeTable = null;
        // Map {col-id, multiple row-id}: used to fast build a rating matrix
        Multimap<Integer, Integer> colMap = HashMultimap.create();

        if (this.userIds == null) {
            this.userIds = HashBiMap.create();
        }
        if (this.itemIds == null) {
            this.itemIds = HashBiMap.create();
        }
        final List<File> files = new ArrayList<>();
        final ArrayList<Long> fileSizeList = new ArrayList<>();
        SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileSizeList.add(file.toFile().length());
                files.add(file.toFile());
                return super.visitFile(file, attrs);
            }
        };
        for (String path : inputDataPath.trim().split(" ")) {
            Files.walkFileTree(Paths.get(path), finder);
        }

        LOG.info("All dataset files " + files.toString());
        long allFileSize = 0;
        for (Long everyFileSize : fileSizeList) {
            allFileSize = allFileSize + everyFileSize;
        }
        LOG.info("All dataset files size " + Long.toString(allFileSize));
        int readingFileCount = 0;
        long loadAllFileByte = 0;
        // loop every dataFile collecting from walkFileTree

        for (File dataFile : files) {
            LOG.info("Now loading dataset file " + dataFile.toString().substring(dataFile.toString().lastIndexOf(File.separator) + 1, dataFile.toString().lastIndexOf(".")));

            readingFileCount += 1;
            loadFilePathRate = readingFileCount / (float) files.size();
            long readingOneFileByte = 0;
            FileInputStream fis = new FileInputStream(dataFile);
            FileChannel fileRead = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
            int len;
            String bufferLine = "";
            byte[] bytes = new byte[BSIZE];

            while ((len = fileRead.read(buffer)) != -1) {
                readingOneFileByte += len;
                loadDataFileRate = readingOneFileByte / (float) fileRead.size();
                loadAllFileByte += len;
                loadAllFileRate = loadAllFileByte / (float) allFileSize;
                buffer.flip();
                buffer.get(bytes, 0, len);
                bufferLine = bufferLine.concat(new String(bytes, 0, len));
                bufferLine = bufferLine.replaceAll("\r", "\n");
                String[] bufferData = bufferLine.split("(\n)+");
                boolean isComplete = bufferLine.endsWith("\n");
                int loopLength = isComplete ? bufferData.length : bufferData.length - 1;
                for (int i = 0; i < loopLength; i++) {
                    String line = bufferData[i];
                    String[] data = line.trim().split("[ \t,]+");
                    String user = data[0];
                    String item = data[1];
                    Double rate = ((dataColumnFormat.equals("UIR") || dataColumnFormat.equals("UIRT")) && data.length >= 3) ? Double.valueOf(data[2]) : 1.0;

                    // binarize the rating for item recommendation task
                    if (binThold >= 0) {
                        rate = rate > binThold ? 1.0 : 0.0;
                    }

                    // inner id starting from 0
                    int row = userIds.containsKey(user) ? userIds.get(user) : userIds.size();
                    userIds.put(user, row);

                    int col = itemIds.containsKey(item) ? itemIds.get(item) : itemIds.size();
                    itemIds.put(item, col);

                    dataTable.put(row, col, rate);
                    colMap.put(col, row);
                    // record rating's issuing time
                    if (StringUtils.equals(dataColumnFormat, "UIRT") && data.length >= 4) {
                        if (timeTable == null) {
                            timeTable = HashBasedTable.create();
                        }
                        // convert to million-seconds
                        long mms = 0L;
                        try {
                            mms = Long.parseLong(data[3]); // cannot format
                            // 9.7323480e+008
                        } catch (NumberFormatException e) {
                            mms = (long) Double.parseDouble(data[3]);
                        }
                        long timestamp = timeUnit.toMillis(mms);
                        timeTable.put(row, col, timestamp);
                    }
                }
                if (!isComplete) {
                    bufferLine = bufferData[bufferData.length - 1];
                }
                buffer.clear();
            }
            fileRead.close();
            fis.close();
        }
        int numRows = numUsers(), numCols = numItems();
        // build rating matrix
        preferenceMatrix = new SparseMatrix(numRows, numCols, dataTable, colMap);
        if (timeTable != null)
            datetimeMatrix = new SparseMatrix(numRows, numCols, timeTable, colMap);
        // release memory of data table
        dataTable = null;
        timeTable = null;
    }

    
    @Override
    public void progress() {
        getJobStatus().setProgress(loadAllFileRate);
    }

    
    public double getFilePathRate() {
        return loadFilePathRate;
    }

    
    public double getDataFileRate() {
        return loadDataFileRate;
    }

    
    public double getLoadAllFileRate() {
        return loadAllFileRate;
    }

    
    public int numUsers() {
        return userIds.size();
    }

    
    public int numItems() {
        return itemIds.size();
    }

    
    public int getUserId(String rawId) {
        return userIds.get(rawId);
    }

    
    public int getItemId(String rawId) {
        return itemIds.get(rawId);
    }

    
    public BiMap<String, Integer> getUserIds() {
        return userIds;
    }

    
    public BiMap<String, Integer> getItemIds() {
        return itemIds;
    }

    
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

}
