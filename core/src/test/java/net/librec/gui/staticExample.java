package net.librec.gui;

public class staticExample {
	private static int cnt;
	public static void setCnt(int cnt)
	{
		cnt = cnt;
		
	}
	public static void printCnt()
	{
		cnt +=1;
		System.out.println(cnt);
	}
}
