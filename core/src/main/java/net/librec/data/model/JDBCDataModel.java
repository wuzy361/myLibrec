
package net.librec.data.model;

import com.google.common.collect.BiMap;
import net.librec.common.LibrecException;
import net.librec.math.structure.DataSet;


public class JDBCDataModel extends AbstractDataModel {

    @Override
    protected void buildConvert() throws LibrecException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public BiMap<String, Integer> getUserMappingData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BiMap<String, Integer> getItemMappingData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataSet getDatetimeDataSet() {
        // TODO Auto-generated method stub
        return null;
    }

}
