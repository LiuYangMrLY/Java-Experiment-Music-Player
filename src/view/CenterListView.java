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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CenterListView extends JPanel {
    private JLabel lb_list_pic = new JLabel(new ImageIcon("src/pic/list_pic.png"));
    private JPanel mPanel = new JPanel();
    private int WIDTH = 1200;
    private int HEIGHT = 820;
    private String listName = "我喜欢的音乐";
    private String userName = "本地";
    private JButton btn_addMusic = new JButton("添加歌曲");
    private JButton btn_delete = new JButton("删除歌单");

    private boolean isMyList = false;
    private MusicSheet musicSheet;


    public CenterListView(MusicSheet musicSheet,boolean isMyList){
        super();

        this.isMyList = isMyList;
        this.listName = musicSheet.getName();
        this.userName = musicSheet.getCreator();
        this.musicSheet = musicSheet;


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

        btn_addMusic.setBackground(Color.WHITE);
        btn_delete.setFont(new Font (Font.DIALOG, Font.BOLD, 15));
        btn_addMusic.setFont(new Font (Font.DIALOG, Font.BOLD, 15));
        btn_delete.setBackground(Color.WHITE);
        initBtn();
        mPanel.add(btn_addMusic);
        mPanel.add(new JLabel());
        mPanel.add(new JLabel());
        mPanel.add(btn_delete);

        if (!isMyList){
            btn_delete.setVisible(false);
            btn_addMusic.setVisible(false);
        }

        //歌曲列表
        JPanel jPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setVgap(0);
        jPanel.setLayout(flowLayout);
        jPanel.setPreferredSize(new Dimension(1180,1000));
        jPanel.setBorder(new EmptyBorder(50,0,0,0));
        jPanel.setBackground(new Color(245,245,245));
        for (int i = 1;i < musicSheet.getMusicArray().size() + 1;i ++){
            jPanel.add(new SongPanel(i,musicSheet.getMusicArray().get(i - 1),false,musicSheet));
        }

        mPanel.add(jPanel);

        MScrollPane scrollPane = new MScrollPane(mPanel);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 15,HEIGHT));

        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
    }

    private void initBtn() {
        btn_addMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileAddDialog(MainView.mJpanel,musicSheet);
            }
        });
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        MainView.mJpanel,
                        "确认删除？",
                        "提示",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );
                System.out.println("选择结果: " + result);
            }
        });
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

    private static void showFileAddDialog(Component parent,MusicSheet musicSheet) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        //fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            File[] files = fileChooser.getSelectedFiles();

            musicSheet.addMusicIntoMusicSheet(Music.newMusics(files));

            try {
                Thread.sleep(100 * files.length);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            MainView.mJpanel.remove(MainView.center);
            MainView.center = new CenterListView(musicSheet,true);
            MainView.mJpanel.add(MainView.center,BorderLayout.CENTER);

            MainView.mJpanel.updateUI();
            System.out.println("点击了歌单");

            System.out.println("打开文件: " + file.getAbsolutePath() + "\n\n");
        }
    }
}
