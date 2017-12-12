
package net.librec.data.model;

import com.google.common.collect.BiMap;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.conf.Configured;
import net.librec.data.DataModel;
import net.librec.data.DataSplitter;
import net.librec.data.convertor.ArffDataConvertor;
import net.librec.math.structure.DataSet;
import net.librec.math.structure.MatrixEntry;
import net.librec.math.structure.SparseMatrix;
import net.librec.math.structure.SparseTensor;
import net.librec.util.DriverClassUtil;
import net.librec.util.ReflectionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ArffDataModel extends AbstractDataModel implements DataModel {

    
    // private ArffDataConvertor dataConvertor;

    
    public ArffDataModel() {
    }

    
    public ArffDataModel(Configuration conf) {
        this.conf = conf;
    }

    
    @Override
    public void buildConvert() throws LibrecException {
        String splitter = conf.get("data.model.splitter");
        String dfsDataDir = conf.get(Configured.CONF_DFS_DATA_DIR);
        String inputDataPath = dfsDataDir + "/" + conf.get(Configured.CONF_DATA_INPUT_PATH);
        dataConvertor = new ArffDataConvertor(inputDataPath);
        try {
            dataConvertor.processData();
            dataSplitter = (DataSplitter) ReflectionUtil.newInstance(DriverClassUtil.getClass(splitter), conf);
        } catch (IOException e) {
            throw new LibrecException(e);
        } catch (ClassNotFoundException e) {
            throw new LibrecException(e);
        }
    }

    
    @Override
    protected void buildSplitter() throws LibrecException {
        super.buildSplitter();
        if (dataConvertor != null && dataSplitter != null) {
            SparseTensor totalTensor = dataConvertor.getSparseTensor();
            // SparseMatrix trainMatrix = dataSplitter.getTrainData();
            SparseMatrix testMatrix = dataSplitter.getTestData();
            // construct train/test tensor from test sparse matrix
            SparseTensor trainTensor = totalTensor.clone();
            int[] dimensions = trainTensor.dimensions();
            SparseTensor testTensor = new SparseTensor(dimensions);
            testTensor.setUserDimension(trainTensor.getUserDimension());
            testTensor.setItemDimension(trainTensor.getItemDimension());

            for (MatrixEntry me : testMatrix) {
                int u = me.row();
                int i = me.column();

                List<Integer> indices = totalTensor.getIndices(u, i);

                for (int index : indices) {
                    int[] keys = totalTensor.keys(index);
                    try {
                        testTensor.set(totalTensor.value(index), keys);
                        trainTensor.remove(keys);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            trainDataSet = trainTensor;
            testDataSet = testTensor;
        }
    }
    
    @Override
    public BiMap<String, Integer> getUserMappingData() {
        return ((ArffDataConvertor)dataConvertor).getUserIds();
    }

    
    @Override
    public BiMap<String, Integer> getItemMappingData() {
        return ((ArffDataConvertor)dataConvertor).getItemIds();
    }

    @Override
    public DataSet getDatetimeDataSet() {
        return null;
    }

    
    public ArrayList<BiMap<String, Integer>> getAllFeaturesMappingData() {
        return ((ArffDataConvertor)dataConvertor).getAllFeatureIds();
    }
}
