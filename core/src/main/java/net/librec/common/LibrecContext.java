
package net.librec.common;

import net.librec.conf.Configuration;


public interface LibrecContext {
    /**
     * get Configuration
     * 
     * @return Configuration
     */
    public Configuration getConf();

    /**
     * set Configuration
     * @param conf  a given configuration
     */
    public void setConf(Configuration conf);
}
