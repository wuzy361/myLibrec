package net.librec.gui;
import  net.librec.entrace.BHFree;
public class Client {
    public static void main(String args[]){
    	BHFree bhf = new BHFree();
    	System.out.println(bhf);
        Index index = new Index(bhf);
    }

}
