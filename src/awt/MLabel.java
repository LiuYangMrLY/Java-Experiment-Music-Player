package awt;

import model.MusicSheet;
import view.*;

import javax.naming.Context;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.html.parser.ContentModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MLabel extends JLabel implements MouseListener {
    private String name;
    private boolean isSelected = false;
    private Context context;
    private MusicSheet musicSheet = new MusicSheet();

    public MLabel(String name,String text,String path){
        super();
        this.name = name;
        this.setOpaque(true);
        this.setIcon(new ImageIcon(path));
        this.setText("   " + text);
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(235,34));
        this.setBorder(new EmptyBorder(0,0,0,0));
        this.addMouseListener(this);
        this.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
    }

    public MLabel(MusicSheet musicSheet){
        this.musicSheet = musicSheet;
        this.name = "list";
        this.setOpaque(true);
        this.setIcon(new ImageIcon("src/pic/list.png"));
        this.setText("   " + musicSheet.getName());
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(235,34));
        this.setBorder(new EmptyBorder(0,0,0,0));
        this.addMouseListener(this);
        this.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        System.out.println("rferfe");
        switch (name){
            case "list":
                //MainView.mJpanel.removeAll();
//                MainView.mJpanel.add(new WestView(),BorderLayout.WEST);
//                MainView.mJpanel.add(new SouthView(4),BorderLayout.SOUTH);
                //MainView.mJpanel.remove(CENTER);
                //MainView.mJpanel.remove();

                //MainView.mJpanel.add(new CenterListView(),BorderLayout.CENTER);
//                if (e.getButton() == MouseEvent.BUTTON3){
//                    showPopupMenu(e.getComponent(), e.getX(), e.getY());
//                }
//                else {
                    MainView.mJpanel.remove(MainView.center);
                    MainView.center = new CenterListView(musicSheet,true);
                    MainView.mJpanel.add(MainView.center,BorderLayout.CENTER);

                    MainView.mJpanel.updateUI();
                    System.out.println("点击了歌单");
               // }
                break;
            case "others":
                //MainView.mJpanel.removeAll();
//                MainView.mJpanel.add(new WestView(),BorderLayout.WEST);
//                MainView.mJpanel.add(new SouthView(4),BorderLayout.SOUTH);
                //MainView.mJpanel.add(new CenterOthersView(),BorderLayout.CENTER);
                MainView.mJpanel.remove(MainView.center);
                MainView.center = new CenterOthersView();
                MainView.mJpanel.add(MainView.center,BorderLayout.CENTER);
                MainView.mJpanel.updateUI();
                System.out.println("点击了推荐");
                break;
            case "nList":
                //showFileAddDialog(MainView.mJpanel);
                String inputContent = JOptionPane.showInputDialog(
                        MainView.mJpanel,
                        "请输入歌单名字:",
                        "我的歌单"
                );
                System.out.println("输入的内容: " + inputContent);
                MusicSheet musicSheet = new MusicSheet(inputContent,"粉色龙类物种","1114","src/pic/list_pic.png");
                musicSheet.saveMusicSheet();
                MainView.mJpanel.remove(MainView.west);
                MainView.west = new WestView();
                MainView.mJpanel.add(MainView.west,BorderLayout.WEST);
                MainView.mJpanel.updateUI();
                break;
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        this.setBackground(new Color(224,224,224));
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        this.setBackground(new Color(245,245,245));
//        this.setForeground(new Color(216,30,6));
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        this.setBackground(new Color(245,245,245));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        this.setBackground(Color.white);
    }
}
