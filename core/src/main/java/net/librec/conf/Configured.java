
package net.librec.conf;


public class Configured implements Configurable {

    public static final String CONF_DATA_COLUMN_FORMAT = "data.column.format";

    public static final String CONF_DFS_DATA_DIR = "dfs.data.dir";

    public static final String CONF_DATA_INPUT_PATH = "data.input.path";

    protected Configuration conf;

    
    public Configured() {
        this(null);
    }

    
    public Configured(Configuration conf) {
        setConf(conf);
    }

    // inherit javadoc
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    // inherit javadoc
    public Configuration getConf() {
        return conf;
    }

}
