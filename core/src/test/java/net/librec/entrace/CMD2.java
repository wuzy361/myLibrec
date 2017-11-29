package net.librec.entrace;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CMD2 {
					 	 
	public static void main(String[] args) throws IOException {  

		      Thread1 T1 = new Thread1( "Thread-1");
		      T1.start();
		      
//	      ThreadDemo2 T2 = new ThreadDemo2( "Thread-2");
//		      T2.start();

	}
	

}
class Thread1 extends Thread {
	   private Thread t;
	   private String threadName;
	   
	   Thread1( String name) {
	      threadName = name;
	      System.out.println("Creating " +  threadName );
	   }
	   
	   public void run() {
		    InputStream ins = null;
			try
		       {
					Process process = Runtime.getRuntime().exec("cmd /c ..\\bin\\librec rec -exec");
				
					ins = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins));
					String line = null;
					while ((line = reader.readLine()) != null)
					{
				
					System.out.println(line);
					}
					int exitValue = process.waitFor();
					System.out.println("return value：" + exitValue);
				
					process.getOutputStream().close();
		       }
		         catch (Exception e)
				{
					e.printStackTrace();
				}
			}
	   
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
}

