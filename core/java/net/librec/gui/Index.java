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

public class Index extends JFrame {

	private DataModel dataModel = null;
	private Similarity similarity = null;
	private Recommender recommender = null;
	private GUIConsole guiconsole = null;
	private Thread1 t1 = new Thread1("t1");

	public Index() {
		this.setLayout(null);
		JLabel l1 = new JLabel();
		l1.setText("执行结果");
		l1.setBounds(20, 10, 100, 50);
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

		JButton b6 = new JButton();
		b6.setText("执行推荐算法");
		b6.setBackground(Color.pink);
		b6.setBounds(500, 260, 125, 30);
		this.add(l1);

		this.add(b1);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataModel == null) {
					dataModel = DataModel.getDataModel();
				} else {
					dataModel.setVisible(true);
					System.out.println("Visible");

				}

			}
		});
		this.add(b2);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (similarity == null) {
					Similarity similarity = Similarity.getSimilartiy();
				} else {
					similarity.setVisible(true);
				}

			}
		});
		this.add(b3);
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (recommender == null) {
					Recommender recommender = Recommender.getRecommeder();
					recommender.setVisible(true);
				} else {
					System.out.println("recommender visible");
					recommender.setVisible(true);
				}

			}
		});
		b6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				
					if (guiconsole == null) {
						guiconsole = GUIConsole.getGUIConsole();

						guiconsole.setVisible(true);
					} else {

						guiconsole.setVisible(true);

					}
					guiconsole.ta.setText("");

					if (t1.isAlive()) {
						t1.interrupt();
					}
					t1 = new Thread1("t1");
					t1.start();

			}
		});
		this.add(b4);
		this.add(b6);
		this.setBounds(100, 100, 650, 450);

		
		
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation( (int) (width - this.getWidth()) / 2,(int) (height - this.getHeight()) / 2);
  
		
        this.setResizable(false);
		
		this.setTitle("RecSystem");
		this.setVisible(true);
	}
}

class Thread1 extends Thread {
	private Thread t;
	private String threadName;
	private BHFree bhfree = null;

	Thread1(String name) {

		threadName = name;
	}

	public void run() {
		InputStream ins = null;
		try {
			BHFree.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		System.out.println("Starting!");
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
