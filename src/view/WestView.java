package view;

import awt.MLabel;
import awt.MScrollPane;
import model.Music;
import model.MusicSheet;
import sun.plugin.javascript.navig.Image;

import javax.naming.Context;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class WestView extends JPanel {
    int WIDTH = 250;

    ArrayList<MLabel> mLabels = new ArrayList<>();
    ArrayList<MusicSheet> musicSheets = new ArrayList<>();



    public WestView(){
        super();
        this.setPreferredSize(new Dimension(WIDTH, 820));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(new EmptyBorder(10,10,10,10));
        this.setBackground(Color.white);

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel1.setBackground(Color.white);
        MLabel lb_others = new MLabel("others","别人都在听","src/pic/others.png");
        lb_others.setBorder(new EmptyBorder(0,3,0,0));
        jPanel1.add(lb_others);
        jPanel1.add(new MLabel("download","下载","src/pic/download.png"));
        jPanel1.setPreferredSize(new Dimension(200,100));

        //自己的歌单列表
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel.setBackground(Color.white);
        //jPanel.setPreferredSize(new Dimension(200, 900));

        MLabel lb_newList = new MLabel("nList","新建歌单","src/pic/add.png");
        lb_newList.setBorder(new EmptyBorder(1,2,1,1));
        lb_newList.setHorizontalAlignment(SwingConstants.LEFT);

        jPanel.add(lb_newList);

        musicSheets = MusicSheet.getSheets();
        int i = 0;
        for (i = 0; i < musicSheets.size();i++)
            jPanel.add(new MLabel("list",musicSheets.get(i).getName(),"src/pic/list.png"));

        jPanel.setPreferredSize(new Dimension(200,40 * (i + 4)));

        //保证宽高大于JScrollPane的宽高
        MScrollPane scrollPane = new MScrollPane(jPanel);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 15,300));


        this.add(jPanel1);
        this.add(scrollPane);
    }

    private void sendRequestWithOkhttp(){

    }
}
