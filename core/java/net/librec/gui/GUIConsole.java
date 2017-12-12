package net.librec.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUIConsole extends JFrame {
    private static GUIConsole guiconsole;
    public static JTextArea ta ;
    private GUIConsole(){
        this.setLayout(null);
        JLabel j1= new JLabel(" Outout" );
        j1.setBounds(20, 20, 100, 20);
        this.add(j1);
        ta = new JTextArea();
        JScrollPane scrol=new JScrollPane(ta);
        scrol.setBounds(20, 40, 800, 600);
        
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 600 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );
        this.add(scrol);
        this.pack();
        
        this.setSize(1000,800);       
        
        
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation( (int) (width - this.getWidth()) / 2,(int) (height - this.getHeight()) / 2);
  
        this.setResizable(false);
        
        this.setVisible( true );
        
    }

    public static GUIConsole getGUIConsole() {
        
        if (guiconsole == null){
        	guiconsole = new GUIConsole();
        }

        return guiconsole;
    }
    public static void main(String [] args)
    {
    	GUIConsole gui = GUIConsole.getGUIConsole();
    }

}
