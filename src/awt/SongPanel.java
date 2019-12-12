package awt;

import com.sun.org.apache.bcel.internal.generic.NEW;
import model.Music;
import model.MusicSheet;
import model.Player;
import util.Downloader;
import view.CenterDownloadView;
import view.CenterListView;
import view.MainView;
import view.SouthView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SongPanel extends JPanel implements MouseListener {
    //private JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private String song;
    private String player;
    private String album;
    private String duration;
    private int num;
    private Color color;
    private int WIDTH;
    private Music music;
    private boolean isMyMusic = false;
    private MusicSheet musicSheet;

    public SongPanel(int num, Music music,boolean isMyMusic,MusicSheet musicSheet){
        super();
        this.num = num;
        this.music = music;
        this.isMyMusic = isMyMusic;
        this.musicSheet = musicSheet;
        this.setOpaque(true);
        setFocusable(true);
        requestFocus();
        setBorder(new EmptyBorder(4,7,0,5));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(1200,50));
        if (num % 2 == 0){
            color = new Color(245,245,245);
        }
        else color = Color.white;
        setBackground(color);

        //填数据
        JLabel lb_name = new JLabel(music.getName());
        lb_name.setPreferredSize(new Dimension(330,30));
        JLabel lb_player = new JLabel(music.getSinger());
        lb_player.setPreferredSize(new Dimension(330,30));
        JLabel lb_album = new JLabel(music.getAlbum());
        lb_album.setPreferredSize(new Dimension(330,30));
        JLabel lb_duration = new JLabel();
        if (music.getDuration() != 0){
            lb_duration.setText(Integer.toString((int) music.getDuration() / 60) + ":" + Integer.toString((int) music.getDuration() % 60));
        }

        lb_duration.setPreferredSize(new Dimension(70,30));
        JLabel lb_num = new JLabel(String.valueOf(num));
        lb_num.setPreferredSize(new Dimension(75,30));
        lb_album.setFont(new Font (Font.DIALOG, Font.PLAIN, 17));
        lb_player.setFont(new Font (Font.DIALOG, Font.PLAIN, 17));
        lb_name.setFont(new Font (Font.DIALOG, Font.PLAIN, 17));
        lb_num.setFont(new Font (Font.DIALOG, Font.PLAIN, 17));
        lb_duration.setFont(new Font (Font.DIALOG, Font.PLAIN, 17));
//        lb_album.setOpaque(true);
//        lb_duration.setOpaque(true);
//        lb_player.setOpaque(true);
//        lb_name.setOpaque(true);
//        lb_album.setBackground(color);
//        lb_name.setBackground(color);
//        lb_player.setBackground(color);
//        lb_duration.setBackground(color);
        this.add(lb_num);
        this.add(lb_name);
        this.add(lb_player);
        this.add(lb_album);
        this.add(lb_duration);

        this.addMouseListener(this);
    }
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3){
        }
        else if (e.getButton() == MouseEvent.BUTTON1){
            Player.getInstance().selectMusicSheetAndMusic(musicSheet,num - 1);
            SouthView.startPlay();//进度条开始播放
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        if (e.isMetaDown() && isMyMusic) {
            showDeleteMenu(e.getComponent(), e.getX(), e.getY(),musicSheet);
        }
        else if (e.isMetaDown() && (!isMyMusic)){
            showDownloadMenu(e.getComponent(),e.getX(),e.getY(),music);
        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        this.setBackground(new Color(245, 245, 245));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        this.setBackground(color);
    }

    private void showDeleteMenu(Component invoker, int x, int y, MusicSheet musicSheet) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();
        // 创建 一级菜单
        JMenuItem deleteMenuItem = new JMenuItem("删除");

        popupMenu.add(deleteMenuItem);

        // 添加菜单项的点击监听器
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicSheet.deleteMusicFromMusicSheet(num - 1);
                MainView.mJpanel.remove(MainView.center);
                MainView.center = new CenterListView(musicSheet,true);
                MainView.mJpanel.add(MainView.center,BorderLayout.CENTER);
                MainView.mJpanel.updateUI();
                System.out.println("点击了歌单");
            }
        });
        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }
    private static void showDownloadMenu(Component invoker, int x, int y,Music music) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();
        // 创建 一级菜单
        JMenuItem deleteMenuItem = new JMenuItem("下载");

        popupMenu.add(deleteMenuItem);

        // 添加菜单项的点击监听器
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CenterDownloadView.music.add(music);
                CenterDownloadView.isDownloading = true;
                Downloader.downloadMusic(music);
            }
        });
        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }
}
