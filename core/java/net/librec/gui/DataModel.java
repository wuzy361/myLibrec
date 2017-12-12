package net.librec.gui;
import javax.swing.*;

import net.librec.entrace.BHFree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class DataModel extends JFrame{
	private static  BHFree bhfree;
    private static  DataModel dataModel;
 // setting dataset name
 	private String inputDir = "filmtrust/rating";
 // set result directory
 	private String resultDir = "../result";
 //setting dataset format(UIR, UIRT)
 	private String format = "UIR";
 //setting method of split data
 	private String splitter = "ratio";
 //value can be ratio, loocv, given, KCV
 	private String ratio = "rating";
 //setting the dataset is saved by what
 	private  String mformat = "text";
 //setting	the ratio of trainset , this value should in (0,1)
 	private  String tratio = "0.8";
 //setting the random set
 	private String seed = "2017";
 //setting the threshold
 	private String threshold ="-1";
 //setting evaluation the result or not
 	private String evalEnable = "true";
    public DataModel(){
        this.setLayout(null);
        final JComboBox cb1=new JComboBox();
        cb1.addItem("filmtrust");
        cb1.addItem("movielens");
        cb1.setBounds(130, 30, 180, 30);
        JLabel l1 = new JLabel();
        l1.setText("数据文件目录");
        l1.setBounds(20, 30, 100, 30);
        JLabel l2 = new JLabel();
        l2.setText("*自定义文件路径");
        l2.setBounds(20, 70, 100, 30);
        JLabel l3 = new JLabel();
        l3.setText("*自定义文件格式");
        l3.setBounds(20, 110, 100, 30);
        JLabel l4 = new JLabel();
        l4.setText("*自定义数据类型");
        l4.setBounds(20, 150, 100, 30);
        JLabel l5 = new JLabel();
        l5.setText("*自定义数据分割");
        l5.setBounds(20, 190, 100, 30);
        JLabel l6 = new JLabel();
        l6.setText("*自定义分割子配置");
        l6.setBounds(20, 230, 100, 30);
        JLabel l7 = new JLabel();
        l7.setText("二值化阈值");
        l7.setBounds(330, 30, 100, 30);
        JLabel l8 = new JLabel();
        l8.setText("设置随机种子");
        l8.setBounds(330, 70, 100, 30);
        JLabel l9 = new JLabel();
        l9.setText("设置输出目录");
        l9.setBounds(330, 110,100, 30);
        final JTextField tf1 = new JTextField();
        tf1.setBounds(130, 30, 180, 30);
        JTextField tf2 = new JTextField();
        tf2.setBounds(130, 70, 180, 30);
        final JComboBox cb2=new JComboBox();
        cb2.addItem("txt");
        cb2.addItem("arff");
        cb2.addItem("mat");
        cb2.setBounds(130, 110, 180, 30);
        JComboBox tf4 = new JComboBox();
        tf4.addItem("UIR");
        tf4.addItem(("UIRT"));
        tf4.setBounds(130, 150, 180, 30);
        JComboBox tf5 = new JComboBox();
        tf5.addItem("ratio");
        tf5.addItem("loocv");
        tf5.addItem("given");
        tf5.addItem("KCV");
        
        tf5.setBounds(130, 190, 180, 30);
        JTextField tf6 = new JTextField();
        tf6.setBounds(130, 230, 180, 30);
        JTextField tf7 = new JTextField();
        tf7.setBounds(430, 30, 180, 30);
        tf7.setText("-1");
        final JTextField tf8 = new JTextField();
        tf8.setBounds(430, 70, 180, 30);
        tf8.setText("2017");
        final JTextField tf9 = new JTextField();
        tf9.setBounds(430, 110, 180, 30);
        tf9.setText("../results");
        
        JButton b1 = new JButton();
        b1.setText("确认");
        b1.setBounds(400, 350, 100, 25);

        this.add(cb1);
        this.add(l1);
        this.add(l2);
        this.add(l3);
        this.add(l4);
        this.add(l5);
        this.add(l6);
        this.add(l7);
        this.add(l8);
        this.add(l9);
        this.add(tf1);
        this.add(tf2);
        this.add(cb2);
        this.add(tf4);
        this.add(tf5);
        this.add(tf6);
        this.add(tf7);
        this.add(tf8);
        this.add(tf9);
        this.add(b1);

        
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(tf9.getText());
                BHFree.setResultDir(tf9.getText());
                
               int scb1 = cb1.getSelectedIndex();
               BHFree.setindex1(scb1);
               int scb2 = cb2.getSelectedIndex();
               dataModel.setVisible(false);
               
               BHFree.setSeed(tf8.getText());
               
            }
        });


        this.setBounds(100, 100, 650, 450);
        this.setResizable(false);
        this.setTitle("配置数据模型");
        this.setVisible(true);
    }

    public static DataModel getDataModel( ) {
        if (dataModel == null){
            dataModel = new DataModel();
        }
         return dataModel;
        }
} 

