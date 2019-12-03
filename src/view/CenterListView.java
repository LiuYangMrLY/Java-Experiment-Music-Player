package view;

import awt.MScrollPane;
import awt.SongPanel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Music;
import model.MusicSheet;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CenterListView extends JPanel {
    private JLabel lb_list_pic = new JLabel(new ImageIcon("src/pic/list_pic.png"));
    private JPanel mPanel = new JPanel();
    private int WIDTH = 1200;
    private int HEIGHT = 820;
    private String listName = "我喜欢的音乐";
    private String userName = "本地";
    private ArrayList<SongPanel> songList = new ArrayList<>();


    public CenterListView(ArrayList<Music> music){
        super();

        setBackground(new Color(245,245,245));
        setBorder(new EmptyBorder(40,0,0,0));
        mPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT + 500));
        mPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mPanel.setBackground(new Color(245,245,245));

        //存放歌单图片
        lb_list_pic.setBorder(new EmptyBorder(0,60,0,0));
        mPanel.add(lb_list_pic);

        //中间的分割线
//        JLabel aaa = new JLabel();
//        aaa.setPreferredSize(new Dimension(2,130));
//        aaa.setOpaque(true);
//        aaa.setBorder(new EmptyBorder(0,0,0,0));
//        aaa.setBackground(new Color(187, 185, 180, 135));
//        mPanel.add(aaa);

        //jPanel1用来放歌单名和用户名
        JPanel jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(245,245,245));
        jPanel1.setPreferredSize(new Dimension(300,100));
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel1.setBorder(new EmptyBorder(0,0,0,0));

        JLabel lb_user = new JLabel("创建者：" + userName);
        JLabel lb_list = new JLabel(listName);
        lb_user.setPreferredSize(new Dimension(300,60));
        lb_list.setPreferredSize(new Dimension(300,40));
        lb_user.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        lb_list.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        lb_list.setBorder(new EmptyBorder(0,50,0,0));
        lb_user.setBorder(new EmptyBorder(0,50,0,0));
        lb_list.setOpaque(true);
        lb_user.setOpaque(true);
        lb_list.setBackground(new Color(245,245,245));
        lb_user.setBackground(new Color(245,245,245));

        jPanel1.add(lb_list);
        jPanel1.add(lb_user);

        mPanel.add(jPanel1);

        //歌曲列表
        JPanel jPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setVgap(0);
        jPanel.setLayout(flowLayout);
        jPanel.setPreferredSize(new Dimension(1180,1000));
        jPanel.setBorder(new EmptyBorder(50,0,0,0));
        jPanel.setBackground(new Color(245,245,245));
        for (int i = 1;i < music.size() + 1;i ++){
            jPanel.add(new SongPanel(i,music.get(i - 1)));
        }

        mPanel.add(jPanel);

        MScrollPane scrollPane = new MScrollPane(mPanel);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 15,HEIGHT));

        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
    }


    public CenterListView(){
        super();
        setBackground(new Color(245,245,245));
        setBorder(new EmptyBorder(40,0,0,0));
        mPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT + 500));
        mPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mPanel.setBackground(new Color(245,245,245));

        //存放歌单图片
        lb_list_pic.setBorder(new EmptyBorder(0,60,0,0));
        mPanel.add(lb_list_pic);

        //中间的分割线
//        JLabel aaa = new JLabel();
//        aaa.setPreferredSize(new Dimension(2,130));
//        aaa.setOpaque(true);
//        aaa.setBorder(new EmptyBorder(0,0,0,0));
//        aaa.setBackground(new Color(187, 185, 180, 135));
//        mPanel.add(aaa);

        //jPanel1用来放歌单名和用户名
        JPanel jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(245,245,245));
        jPanel1.setPreferredSize(new Dimension(300,100));
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel1.setBorder(new EmptyBorder(0,0,0,0));

        JLabel lb_user = new JLabel("创建者：" + userName);
        JLabel lb_list = new JLabel(listName);
        lb_user.setPreferredSize(new Dimension(300,60));
        lb_list.setPreferredSize(new Dimension(300,40));
        lb_user.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        lb_list.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        lb_list.setBorder(new EmptyBorder(0,50,0,0));
        lb_user.setBorder(new EmptyBorder(0,50,0,0));
        lb_list.setOpaque(true);
        lb_user.setOpaque(true);
        lb_list.setBackground(new Color(245,245,245));
        lb_user.setBackground(new Color(245,245,245));

        jPanel1.add(lb_list);
        jPanel1.add(lb_user);

        mPanel.add(jPanel1);

        //歌曲列表
        JPanel jPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setVgap(0);
        jPanel.setLayout(flowLayout);
        jPanel.setPreferredSize(new Dimension(1180,1000));
        jPanel.setBorder(new EmptyBorder(50,0,0,0));
        jPanel.setBackground(new Color(245,245,245));
        for (int i = 1;i < 2;i ++){
//            jPanel.add(new SongPanel(i,"Goodbyes", "Post Malone",
//                    "Hollywood Bleeding","3:40"));
        }

        mPanel.add(jPanel);

        MScrollPane scrollPane = new MScrollPane(mPanel);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 15,HEIGHT));

        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
    }
}
