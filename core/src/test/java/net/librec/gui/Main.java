package net.librec.gui;
import javax.swing.*;

import net.librec.common.LibrecException;
import net.librec.entrace.BHFree;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main{
    public static void start(  ) throws InterruptedException, ClassNotFoundException, LibrecException, IOException  {
        JFrame frame = new JFrame();
        frame.add( new JLabel(" Outout" ), BorderLayout.NORTH );

        JTextArea ta = new JTextArea();
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );


        frame.add( new JScrollPane( ta )  );

        frame.pack();
        frame.setVisible( true );
        frame.setSize(800,600);

        BHFree.start();
        }
 
    }
    

    
   