package net.librec.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

//import net.librec.gui.GUIPrintStream;
import net.librec.common.LibrecException;
import net.librec.entrace.BHFree;
public class Index extends JFrame{

	private DataModel dataModel = null;
	private DataModel similarity = null;
	private DataModel recommender = null;

    public Index(){
        this.setLayout(null);
        JLabel l1 = new JLabel();
        l1.setText("执行结果");
        l1.setBounds(20, 10, 100,50);
        JButton b1 = new JButton();
        b1.setText("配置数据模型");
        b1.setBounds(500, 60, 125, 30);
        JButton b2 = new JButton();
        b2.setText("配置相似度");
        b2.setBounds(500, 100, 125, 30);
        JButton b3 = new JButton();
        b3.setText("配置推荐算法");
        b3.setBounds(500, 140, 125, 30);
        JButton b4 = new JButton();
        b4.setText("配置评估器");
        b4.setBounds(500, 180, 125, 30);
        JButton b5 = new JButton();
        b5.setText("配置过滤器");
        b5.setBounds(500, 220, 125, 30);
        JButton b6 = new JButton();
        b6.setText("执行推荐算法");
        b6.setBackground(Color.pink);
        b6.setBounds(500, 260, 125, 30);
        this.add(l1);
//        this.add(ta);
        this.add(b1);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (dataModel ==null)
            	{
            	     dataModel = DataModel.getDataModel();
            	}
            	else
            	{
            		dataModel.setVisible(true);
            		System.out.println("Visible");
            		
            	}
//                dataModel.
            }
        });
        this.add(b2);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if (similarity ==null)
            	{
            		Similarity similarity = Similarity.getSimilartiy();
            	}
            	else
            	{
            		similarity.setVisible(true);
            	}
                
            }
        });
        this.add(b3);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (recommender ==null)
            	{
            		Recommender recommender = Recommender.getRecommeder();
            	}
            	else
            	{
            		recommender.setVisible(true);
            	}
           
            }
        });
        b6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					GUIConsole guiconsole = GUIConsole.getGUIConsole();
				    Thread1 t1 = new Thread1("t1");
					t1.start();
//					try {
//						t1.stop();
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
			}
		});
        this.add(b4);
        this.add(b5);
        this.add(b6);
        this.setBounds(100, 100, 650, 450);
        this.setResizable(false);
        this.setTitle("RecSystem");
        this.setVisible(true);
    }
}

class Thread1 extends Thread {
	   private Thread t;
	   private String threadName;
	   private BHFree bhfree = null;
	   
	   Thread1( String name ) {
		  
	      threadName = name;
	   }
	   
	   public void run() {
		    InputStream ins = null;
			try
		       {
				BHFree.start();
		       }
		         catch (Exception e)
				{
					e.printStackTrace();
				}
			}
	   
	   public void start () {
	      System.out.println("Starting!" );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
}
