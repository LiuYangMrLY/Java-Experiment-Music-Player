package view;

import awt.MProgressBar;
import awt.MSlider;
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
    private static int playStyle = 0;//0为列表循环，1为单曲循环，2为随机播放

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
        //setLayout(new FlowLayout());
        //this.add(new MProgressBar(duration));
        //this.add(new MSlider());
    }

    public static void startPlay(){
        btn_play.setIcon(new ImageIcon("src/pic/pause_normal.png"));
        btn_play.setPressedIcon(new ImageIcon("src/pic/pause_pressed.png"));
        isPlaying = true;
        mSlider.timer.start();
    }

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
                if (isPlaying){
                    btn_play.setIcon(new ImageIcon("src/pic/play_normal.png"));
                    btn_play.setPressedIcon(new ImageIcon("src/pic/play_pressed.png"));
                    isPlaying = false;
                    mSlider.timer.stop();
                    Player.getInstance().pause();
                }
                else {
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
            }
        });

        btn_playStyle.setIcon(new ImageIcon("src/pic/repeat_list.png"));
        btn_playStyle.setBorder(new EmptyBorder(0,50,0,0));
        btn_playStyle.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (playStyle == 0){
                    btn_playStyle.setIcon(new ImageIcon("src/pic/repeat_one.png"));
                    playStyle = 1;
                }
                else if (playStyle == 1){
                    btn_playStyle.setIcon(new ImageIcon("src/pic/random.png"));
                    playStyle = 2;
                }
                else {
                    btn_playStyle.setIcon(new ImageIcon("src/pic/repeat_list.png"));
                    playStyle = 0;
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
    }
}
