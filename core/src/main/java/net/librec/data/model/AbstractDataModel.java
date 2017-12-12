
package net.librec.data.model;

import net.librec.common.LibrecException;
import net.librec.conf.Configured;
import net.librec.data.*;
import net.librec.data.splitter.KCVDataSplitter;
import net.librec.math.structure.DataSet;
import net.librec.util.DriverClassUtil;
import net.librec.util.ReflectionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;


public abstract class AbstractDataModel extends Configured implements DataModel {
    
    protected final Log LOG = LogFactory.getLog(this.getClass());
    
    protected DataContext context;
    
    protected DataSet trainDataSet;
    
    protected DataSet testDataSet;
    
    protected DataSet validDataSet;

    
    protected DataConvertor dataConvertor;

    
    public DataSplitter dataSplitter;
    
    public DataAppender dataAppender;

    
    protected abstract void buildConvert() throws LibrecException;

    
    protected void buildSplitter() throws LibrecException {
        String splitter = conf.get("data.model.splitter");
        try {
        	if (dataSplitter == null){
        		dataSplitter = (DataSplitter) ReflectionUtil.newInstance(DriverClassUtil.getClass(splitter), conf);	
        	}
            if (dataSplitter != null) {
                dataSplitter.setDataConvertor(dataConvertor);
                if (dataSplitter instanceof KCVDataSplitter) {
                    ((KCVDataSplitter) dataSplitter).splitFolds();
                }
                dataSplitter.splitData();
                trainDataSet = dataSplitter.getTrainData();
                testDataSet = dataSplitter.getTestData();
            }
        } catch (ClassNotFoundException e) {
            throw new LibrecException(e);
        }
    }

    
    protected void buildFeature() throws LibrecException {
        String feature = conf.get("data.appender.class");
        if (StringUtils.isNotBlank(feature)) {
            try {
                dataAppender = (DataAppender) ReflectionUtil.newInstance(DriverClassUtil.getClass(feature), conf);
                dataAppender.setUserMappingData(getUserMappingData());
                dataAppender.setItemMappingData(getItemMappingData());
                dataAppender.processData();
            } catch (ClassNotFoundException e) {
                throw new LibrecException(e);
            } catch (IOException e) {
                throw new LibrecException(e);
            }
        }
    }

    
    @Override
    public void buildDataModel() throws LibrecException {
        context = new DataContext(conf);
        if (!conf.getBoolean("data.convert.read.ready")) {
            buildConvert();
            LOG.info("Transform data to Convertor successfully!");
            conf.setBoolean("data.convert.read.ready", true);
        }
        buildSplitter();
        LOG.info("Split data to train Set and test Set successfully!");
        if (trainDataSet != null && trainDataSet.size() > 0 && testDataSet != null && testDataSet.size() > 0) {
            LOG.info("Data size of training is " + trainDataSet.size());
            LOG.info("Data size of testing is " + testDataSet.size());
        }
        if (StringUtils.isNotBlank(conf.get("data.appender.class")) && !conf.getBoolean("data.appender.read.ready")) {
            buildFeature();
            LOG.info("Transform data to Feature successfully!");
            conf.setBoolean("data.appender.read.ready", true);
        }
    }

    
    @Override
    public void loadDataModel() throws LibrecException {
        // TODO Auto-generated method stub

    }

    
    @Override
    public void saveDataModel() throws LibrecException {
        // TODO Auto-generated method stub

    }

    
    @Override
    public DataSet getTrainDataSet() {
        return trainDataSet;
    }

    
    @Override
    public DataSet getTestDataSet() {
        return testDataSet;
    }

    
    @Override
    public DataSet getValidDataSet() {
        return validDataSet;
    }

    
    @Override
    public DataSplitter getDataSplitter() {
        return dataSplitter;
    }

    
    public DataAppender getDataAppender() {
        return dataAppender;
    }

    
    @Override
    public DataContext getContext() {
        return context;
    }
}
