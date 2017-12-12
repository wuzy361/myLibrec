
package net.librec.data;

import com.google.common.collect.BiMap;

import java.io.IOException;


public interface DataAppender {
    
    public void processData() throws IOException;

    
    public void setUserMappingData(BiMap<String, Integer> userMappingData);

    
    public void setItemMappingData(BiMap<String, Integer> itemMappingData);
}
