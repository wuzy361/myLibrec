package net.librec.entrace;

import java.io.IOException;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.gui.DataModel;
import net.librec.job.RecommenderJob;

public class BHFree{
    private static DataModel dataModel;
 //  dataset name
 	private static  String[] inputDir = {"filmtrust/rating","movielens/ml-100k"};
 // dataset index
 	private int index;
 // set result directory
 	private  String resultDir = "../result";
 //setting dataset format(UIR, UIRT)
 	private   String format = "UIR";
 //setting method of split data
 	private   String splitter = "ratio";
 //value can be ratio, loocv, given, KCV
 	private  String ratio = "rating";
 //setting the dataset is saved by what
 	private  String mformat = "text";
 //setting	the ratio of trainset , this value should in (0,1)
 	private  String tratio = "0.8";
 //setting the random set
 	private  String seed = "1";
 //setting the threshold
 	private  String threshold ="-1";
 //setting evaluation the result or not
 	private  String evalEnable = "true";
	public  void start() throws ClassNotFoundException, LibrecException, IOException
	{
		Configuration conf = new Configuration();
	    Configuration.Resource resource = new Configuration.Resource("rec/cf/bhfree-test.properties");
	    conf.addResource(resource);
	    conf.set("dfs.data.dir", "../data");
	    conf.set("data.input.path", inputDir[1]);
	    conf.set("dfs.result.dir", resultDir);
	    conf.set("data.column.format", format);
	    conf.set("data.model.splitter", splitter);
	    conf.set("data.splitter.ratio", ratio);
	    conf.set("data.model.format", mformat);
	    conf.set("data.splitter.trainset.ratio", tratio);
	    conf.set("rec.random.seed", seed);
	    conf.set("data.convert.binarize.threshold", threshold);
	    conf.set("rec.eval.enable", evalEnable);

	    RecommenderJob job = new RecommenderJob(conf);
	    job.runJob();
	}
	public  void setindex(int index) {
		this.index = index;
	}
	public  void setResultDir(String resultDir) {
		this.resultDir = resultDir;
	}
	public  void setFormat(String format) {
		this.format = format;
	}
	public  void setSplitter(String splitter) {
		this.splitter = splitter;
	}
	public  void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public  void setMformat(String mformat) {
		this.mformat = mformat;
	}
	public  void setTratio(String tratio) {
		this.tratio = tratio;
	}
	public  void setSeed(String seed) {
		this.seed = seed;
	}
	public  void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public  void setEvalEnable(String evalEnable) {
		this.evalEnable = evalEnable;
	}
	
	
}

