package net.librec.gui;

import java.awt.BorderLayout;
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
    private GUIConsole(){
        this.setLayout(null);
        JLabel j1= new JLabel(" Outout" );
        j1.setBounds(20, 20, 100, 20);
        this.add(j1);
        JTextArea ta = new JTextArea();
        ta.setBounds(20, 40, 800, 600);
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );
        this.add(ta);
        this.pack();
        this.setVisible( true );
        this.setSize(800,600);
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
