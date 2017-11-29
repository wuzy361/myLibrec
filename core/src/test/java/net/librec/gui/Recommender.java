package net.librec.gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Recommender extends JFrame {
    private static Recommender recommender;
    private Recommender(){
        this.setLayout(null);
        JLabel l1 = new JLabel();
        l1.setText("选择算法");
        l1.setBounds(30, 30, 100, 30);
        JComboBox cb = new JComboBox();
        cb.addItem("suanfa1");
        cb.addItem("suanfa2");
        cb.setBounds(130, 30, 180, 30);
        JTextPane tp = new JTextPane();
        tp.setBounds(30, 70, 300, 300);
        JButton b1 = new JButton();
        b1.setText("确认");
        b1.setBounds(400, 350, 100, 25);
        JButton b2 = new JButton();
        b2.setBounds(510, 350, 100, 25);
        b2.setText("返回");
        this.add(l1);
        this.add(cb);
        this.add(tp);
        this.add(b1);
        this.add(b2);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recommender.setVisible(false);
                recommender = null ;
            }
        });
        this.setBounds(100, 100, 650, 450);
        this.setResizable(false);
        this.setTitle("配置推荐算法");
        this.setVisible(true);
    }

    public static Recommender getSimilartiy() {
        if (recommender == null){
            recommender = new Recommender();
        }
        return recommender;
    }
}
