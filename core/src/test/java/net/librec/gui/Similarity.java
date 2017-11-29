package net.librec.gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Similarity extends JFrame {
    private static Similarity similarity;
    private Similarity(){
        this.setLayout(null);
        JLabel l1 = new JLabel();
        l1.setText("度量对象");
        l1.setBounds(30, 30, 100, 30);
        JLabel l2 = new JLabel();
        l2.setText("相似度类型");
        l2.setBounds(30, 70, 100, 30);
        JRadioButton s1 = new JRadioButton("用户",true);
        s1.setBounds(130, 30, 60, 30);
        JRadioButton s2 = new JRadioButton("物品");
        s2.setBounds(200, 30, 60, 30);
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(s1);
        bg1.add(s2);
        JComboBox tf2 = new JComboBox();
        tf2.addItem("cos");
        tf2.addItem("person");
        tf2.setBounds(130, 70, 180, 30);
        JButton b1 = new JButton();
        b1.setText("确认");
        b1.setBounds(400, 350, 100, 25);
        JButton b2 = new JButton();
        b2.setBounds(510, 350, 100, 25);
        b2.setText("返回");
        this.add(l1);
        this.add(l2);
        this.add(s1);
        this.add(s2);
        this.add(tf2);
        this.add(b1);
        this.add(b2);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                similarity.setVisible(false);
                similarity = null ;
            }
        });
        this.setBounds(100, 100, 650, 450);
        this.setResizable(false);
        this.setTitle("配置相似度");
        this.setVisible(true);
    }

    public static Similarity getSimilartiy() {
        if (similarity == null){
            similarity = new Similarity();
        }
        return similarity;
    }
}
