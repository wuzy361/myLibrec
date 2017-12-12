package net.librec.entrace;

import java.io.IOException;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.gui.DataModel;
import net.librec.job.RecommenderJob;

public class BHFree{
	
    private  DataModel dataModel;
 // baseline algorithm
    private static  String[] baselineAlg = {
    		                                  "rec/baseline/constantguess-test",
    		                                  "rec/baseline/globalaverage",
    		                                  "rec/baseline/itemaverage",
    		                                  "rec/baseline/itemcluster",
    		                                  "rec/baseline/mostpopular",
    		                                  "rec/baseline/randomguess",
    		                                  "rec/baseline/useraverage",
    		                                  "rec/baseline/usercluster",
    		                                  "rec/cf/bhfree-test",
    		                                  "rec/cf/itemknn-test",
    		                                  "rec/cf/Bitemknn-test",
    		                                  "rec/context/rating/trustsvd-test",
    		                                  "rec/context/rating/pttrustsvd-test"};
 //  dataset name
 	private static  String[] inputDir = {"filmtrust/rating","movielens/ml-100k"};
 // dataset index
 	private static int index1 = 0;
 	private static int index2 = 0;
 	private static int algindex = 0;
 // set result directory
 	private  static String resultDir = "../results";
 //setting dataset format(UIR, UIRT)
 	private  static  String format = "UIR";
 //setting method of split data
 	private  static String splitter = "ratio";
 //value can be ratio, loocv, given, KCV
 	private  static String ratio = "rating";
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
	    Configuration.Resource resource = new Configuration.Resource(baselineAlg[algindex]+".properties");
	    conf.addResource(resource);
	    conf.set("dfs.data.dir", "../data");
	    conf.set("data.input.path", inputDir[index1]);
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
//	    System.out.println(index1);
	    job.runJob();
		System.out.println("finished!");	
	}
	public static  void setindex1(int idx) {
		index1 = idx;
//		System.out.print("in bhf");
//		System.out.println(BHFree.index1);

	}
	public static  void setindex2(int idx) {
		index2 = idx;
//		System.out.print("in bhf");
//		System.out.println(BHFree.index1);

	}
	public static void setAlindex(int idx){
		algindex = idx;
	}
	public static void setResultDir(String res) {
		
		if(res != null)
			{
	        	resultDir = res;
			}
		else{
			res ="../result";
		}
	}
	public static void setFormat(String fmt) {
		format = fmt;
	}
	public static void setSplitter(String spl) {
		splitter = spl;
	}
	public  static void setRatio(String rat) {
		ratio = rat;
	}
	public static void setMformat(String mfmt) {
		mformat = mfmt;
	}
	public static void setTratio(String tra) {
		tratio = tra;
	}
	public static void setSeed(String sd) {
		seed = sd;
	}
	public static void setThreshold(String th) {
		threshold = th;
	}
	public static void setEvalEnable(String eval) {
		evalEnable = eval;
	}
	public static int getindex()
	{
		return index1;
	}
	
}

