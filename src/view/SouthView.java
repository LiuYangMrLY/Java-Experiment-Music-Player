package view;

import awt.MProgressBar;
import awt.MSlider;
import awt.MVolSlider;
import model.Music;
import model.Player;
import org.omg.CORBA.portable.ValueOutputStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SouthView extends JPanel{

    static JButton btn_play = new JButton();
    static JButton btn_next = new JButton();
    static JButton btn_front = new JButton();
    public static MSlider mSlider = new MSlider();
    static boolean isPlaying = false;
    private JLabel btn_playStyle = new JLabel();
    private JLabel btn_vol = new JLabel(new ImageIcon("src/pic/vol.png"));
    private static int playStyle = 0;//0为列表循环，1为单曲循环，2为随机播放
    private static boolean haveMusic = false;

    public SouthView(int duration) {
        super();
        initComponent();

        this.setPreferredSize(new Dimension(1450, 80));
        setBackground(Color.white);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.setBorder(new EmptyBorder(10, 70, 10, 10));

        this.add(btn_front);
        this.add(btn_play);
        this.add(btn_next);

        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(10, 75, 10, 10));
        jPanel.setBackground(Color.white);
        jPanel.add(mSlider);

        this.add(jPanel);

        this.add(btn_playStyle);
        this.add(btn_vol);
        //setLayout(new FlowLayout());
        //this.add(new MProgressBar(duration));
        //this.add(new MSlider());
    }

    /**
     * 改变状态有/无音乐
     * @param haveMusic
     */
    public static void changeStatus(boolean haveMusic){
        SouthView.haveMusic = haveMusic;
        if (haveMusic){
            btn_next.setEnabled(true);
            btn_front.setEnabled(true);
            btn_play.setEnabled(true);
        }
        else {
            btn_play.setEnabled(false);
            btn_front.setEnabled(false);
            btn_next.setEnabled(false);
        }
    }

    /**
     * 点击音乐时执行startPlay
     */
    public static void startPlay(){

        changeStatus(true);//将状态改为有音乐
        btn_play.setIcon(new ImageIcon("src/pic/pause_normal.png"));
        btn_play.setPressedIcon(new ImageIcon("src/pic/pause_pressed.png"));
        isPlaying = true;
        mSlider.startPlaying();
    }

    /**
     * 初始化组件
     */
    private void initComponent(){

        btn_play.setIcon(new ImageIcon("src/pic/play_normal.png"));
        btn_play.setSize(new Dimension(50,500));
        btn_play.setBackground(Color.white);
        btn_play.setBorderPainted(false);
        btn_play.setPressedIcon(new ImageIcon("src/pic/play_pressed.png"));
        btn_play.setBorder(BorderFactory.createRaisedBevelBorder());
        btn_play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlaying && haveMusic){
                    btn_play.setIcon(new ImageIcon("src/pic/play_normal.png"));
                    btn_play.setPressedIcon(new ImageIcon("src/pic/play_pressed.png"));
                    isPlaying = false;
                    mSlider.timer.stop();
                    Player.getInstance().pause();
                }
                else if (haveMusic){
                    btn_play.setIcon(new ImageIcon("src/pic/pause_normal.png"));
                    btn_play.setPressedIcon(new ImageIcon("src/pic/pause_pressed.png"));
                    isPlaying = true;
                    mSlider.timer.start();
                    Player.getInstance().play();
                }
            }
        });


        btn_front.setIcon(new ImageIcon("src/pic/front_normal.png"));
        btn_front.setSize(new Dimension(50,500));
        btn_front.setBackground(Color.white);
        btn_front.setBorderPainted(false);
        btn_front.setPressedIcon(new ImageIcon("src/pic/front_pressed.png"));
        btn_front.setBorder(BorderFactory.createRaisedBevelBorder());
        btn_front.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        btn_next.setIcon(new ImageIcon("src/pic/next_normal.png"));
        btn_next.setSize(new Dimension(50,500));
        btn_next.setBackground(Color.white);
        btn_next.setBorderPainted(false);
        btn_next.setPressedIcon(new ImageIcon("src/pic/next_pressed.png"));
        btn_next.setBorder(BorderFactory.createRaisedBevelBorder());
        btn_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.player.next();
                SouthView.mSlider.setMax(Player.getInstance().getDuration());//设置音乐时长
                SouthView.mSlider.setCurrentProgressToZero();//将进度设为0
                SouthView.changeStatus(true);//将状态改为有音乐
                SouthView.startPlay();//进度条开始播放
            }
        });

        btn_playStyle.setIcon(new ImageIcon("src/pic/repeat_list.png"));
        btn_playStyle.setBorder(new EmptyBorder(0,50,0,20));
        btn_playStyle.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (playStyle == 0){
                    btn_playStyle.setIcon(new ImageIcon("src/pic/repeat_one.png"));
                    playStyle = 1;
                    Player.player.setModeSingle();
                }
                else if (playStyle == 1){
                    btn_playStyle.setIcon(new ImageIcon("src/pic/random.png"));
                    playStyle = 2;
                    Player.player.setModeRandom();
                }
                else {
                    btn_playStyle.setIcon(new ImageIcon("src/pic/repeat_list.png"));
                    playStyle = 0;
                    Player.player.setModeOrder();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        btn_vol.setBorder(new EmptyBorder(0,20,9,0));
        btn_vol.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPopupMenu(e.getComponent(),e.getX(),e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public static void showPopupMenu(Component invoker, int x, int y) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setPopupSize(new Dimension(120,30));
        popupMenu.setBackground(Color.WHITE);
        popupMenu.setBorderPainted(false);

        // 创建 一级菜单
        JMenuItem copyMenuItem = new JMenuItem("复制");
        MVolSlider jSlider = new MVolSlider();
        copyMenuItem.add(jSlider);

        popupMenu.add(copyMenuItem);

        // 添加菜单项的点击监听器
        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("复制 被点击");
            }
        });
        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, 70, - 2);
    }
}
