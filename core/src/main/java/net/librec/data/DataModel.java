
package net.librec.data;

import com.google.common.collect.BiMap;
import net.librec.common.LibrecException;
import net.librec.math.structure.DataSet;


public interface DataModel {
    
    public void buildDataModel() throws LibrecException;

    
    public void loadDataModel() throws LibrecException;

    
    public void saveDataModel() throws LibrecException;

    
    public DataSplitter getDataSplitter();

    
    public DataSet getTrainDataSet();

    
    public DataSet getTestDataSet();

    
    public DataSet getValidDataSet();

    
    public DataSet getDatetimeDataSet();

    
    public BiMap<String, Integer> getUserMappingData();

    
    public BiMap<String, Integer> getItemMappingData();

    
    public DataAppender getDataAppender();
    

    
    public DataContext getContext();
    
}
