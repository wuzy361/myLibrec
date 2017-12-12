
package net.librec.data.model;

import java.io.IOException;

import com.google.common.collect.BiMap;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.conf.Configured;
import net.librec.data.DataModel;
import net.librec.data.convertor.TextDataConvertor;
import net.librec.math.structure.DataSet;
import org.apache.commons.lang.StringUtils;


public class TextDataModel extends AbstractDataModel implements DataModel {

    
    public TextDataModel() {
    }

    
    public TextDataModel(Configuration conf) {
        this.conf = conf;
    }

    
    @Override
    public void buildConvert() throws LibrecException {
//        String inputDataPath = conf.get(Configured.CONF_DFS_DATA_DIR) + "/" + conf.get(Configured.CONF_DATA_INPUT_PATH);
        String[] inputDataPath = conf.get(Configured.CONF_DATA_INPUT_PATH).trim().split(" ");
        for(int i = 0 ; i < inputDataPath.length; i ++){
            inputDataPath[i]=conf.get(Configured.CONF_DFS_DATA_DIR)+"/"+inputDataPath[i];
        }
        String dataColumnFormat = conf.get(Configured.CONF_DATA_COLUMN_FORMAT, "UIR");
        dataConvertor = new TextDataConvertor(dataColumnFormat, StringUtils.join(inputDataPath," "), conf.getDouble("data.convert.binarize.threshold", -1.0));
        try {
            dataConvertor.processData();
        } catch (IOException e) {
            throw new LibrecException(e);
        }
    }

    
    @Override
    public void loadDataModel() throws LibrecException {

    }

    
    @Override
    public void saveDataModel() throws LibrecException {

    }

    
    @Override
    public BiMap<String, Integer> getUserMappingData() {
        return ((TextDataConvertor) dataConvertor).getUserIds();
    }

    
    @Override
    public BiMap<String, Integer> getItemMappingData() {
        return ((TextDataConvertor) dataConvertor).getItemIds();
    }

    
    @Override
    public DataSet getDatetimeDataSet() {
        return ((TextDataConvertor) dataConvertor).getDatetimeMatrix();
    }
}
