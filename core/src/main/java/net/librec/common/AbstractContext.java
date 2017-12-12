
package net.librec.common;

import net.librec.conf.Configuration;


public class AbstractContext implements LibrecContext {

    protected Configuration conf;

    @Override
    public Configuration getConf() {
        return this.conf;
    }


    @Override
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

}
