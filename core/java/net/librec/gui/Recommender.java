package net.librec.gui;
import javax.swing.*;

import net.librec.entrace.BHFree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Recommender extends JFrame {
    private static Recommender recommender;
    private Recommender(){
        this.setLayout(null);
        JLabel l1 = new JLabel();
        l1.setText("选择算法");
        l1.setBounds(30, 30, 100, 30);
        final JComboBox cb = new JComboBox();
        cb.addItem("constantguess");
        cb.addItem("globalaverage");
        cb.addItem("itemaverage");
        cb.addItem("itemcluster");
        cb.addItem("mostpopular");
        cb.addItem("randomguess");
        cb.addItem("useraverage");
        cb.addItem("usercluster");
        cb.addItem("bhfree");
        cb.addItem("itemknn");
        cb.addItem("Bitemknn");
        cb.addItem("trustsvd");
        cb.addItem("ptrustsvd");
        cb.setBounds(130, 30, 180, 30);
        JTextPane tp = new JTextPane();
        tp.setBounds(30, 70, 300, 300);
        JButton b1 = new JButton();
        b1.setText("确认");
        b1.setBounds(400, 350, 100, 25);
        JButton b2 = new JButton();

        this.add(l1);
        this.add(cb);
        this.add(tp);
        this.add(b1);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cb1 = cb.getSelectedIndex();
                BHFree.setAlindex(cb1);
                recommender.setVisible(false);
            }
        });
   
        this.setBounds(100, 100, 650, 450);
        this.setResizable(false);
        this.setTitle("配置推荐算法");
        this.setVisible(true);
    }

    public static Recommender getRecommeder() {
        if (recommender == null){
            recommender = new Recommender();
        }
        return recommender;
    }
}
