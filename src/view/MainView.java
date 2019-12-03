package view;

import awt.MSlider;

import javax.naming.Context;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MainView extends JFrame{

    public static BorderLayout borderLayout= new BorderLayout();
    public static JPanel mJpanel = new JPanel(borderLayout);
    public static JPanel center = new JPanel();
    public static JPanel south = new JPanel();
    public static JPanel west = new JPanel();

    public MainView(String string){
        super(string);
    }

    public static void main(String[] args){
        MainView mainView = new MainView("MusicPlayer");
        mainView.setSize(1450, 900);
        mainView.setLocationRelativeTo(null);// 把窗口位置设置到屏幕中心
        mainView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mJpanel.setBackground(new Color(245,245,245));
        mJpanel.setPreferredSize(new Dimension(1450,900));

        center = new CenterOthersView();
        west = new WestView();
        south = new SouthView(10);
        mJpanel.add(center,BorderLayout.CENTER);
        mJpanel.add(west,BorderLayout.WEST);
        mJpanel.add(south,BorderLayout.SOUTH);



        mainView.setResizable(false);
        mainView.setContentPane(mJpanel);
        mainView.setVisible(true);
        mainView.pack();

    }
}
