package net.librec.entrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class CMD{
	public static void main(String[] args)
	   {
       InputStream ins = null;
       String[] cmd = new String[]{ "cmd.exe", "/c", "dir" };

	try
       {
//			Runtime.getRuntime().exec("cmd /c cd bin");
////		String path = System.getProperty("java.library.path"); 
//		    String cpath = System.getProperty("user.dir")+"\\bin\\librec";
//		    String str1 = cpath.replaceAll("\\\\", "\\\\\\\\");
//		    String cmd1 = "cmd /c "+str1; 
//		    System.out.println(cmd1);
			Process process = Runtime.getRuntime().exec("cmd /c ..\\bin\\librec rec -exec");
			// cmd 的信息
			ins = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
			ins));
			String line = null;
			while ((line = reader.readLine()) != null)
			{
			// 输出
			System.out.println(line);
			}
			int exitValue = process.waitFor();
			System.out.println("返回值：" + exitValue);
			// 关闭
			process.getOutputStream().close();
       }
         catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
