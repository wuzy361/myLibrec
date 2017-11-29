package net.librec.entrace;

import java.io.IOException;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.gui.DataModel;
import net.librec.job.RecommenderJob;

public class BHFree{
    private static DataModel dataModel;
 // setting dataset name
 	private static  String inputDir = "filmtrust/rating";
 // set result directory
 	private static String resultDir = "../result";
 //setting dataset format(UIR, UIRT)
 	private  static String format = "UIR";
 //setting method of split data
 	private static  String splitter = "ratio";
 //value can be ratio, loocv, given, KCV
 	private static String ratio = "rating";
 //setting the dataset is saved by what
 	private static String mformat = "text";
 //setting	the ratio of trainset , this value should in (0,1)
 	private static String tratio = "0.8";
 //setting the random set
 	private static String seed = "1";
 //setting the threshold
 	private static String threshold ="-1";
 //setting evaluation the result or not
 	private static String evalEnable = "true";
	public static void start() throws ClassNotFoundException, LibrecException, IOException
	{
		Configuration conf = new Configuration();
	    Configuration.Resource resource = new Configuration.Resource("rec/cf/bhfree-test.properties");
	    conf.addResource(resource);
	    conf.set("data.input.path", inputDir);
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
}

