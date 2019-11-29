package view;

import awt.MProgressBar;
import awt.MSlider;
import org.omg.CORBA.portable.ValueOutputStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SouthView extends JPanel{
    JButton btn_play = new JButton();
    JButton btn_next = new JButton();
    JButton btn_front = new JButton();
    MSlider mSlider = new MSlider();
    boolean isPlaying = false;

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
        //setLayout(new FlowLayout());
        //this.add(new MProgressBar(duration));
        //this.add(new MSlider());
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
                }
                else {
                    btn_play.setIcon(new ImageIcon("src/pic/pause_normal.png"));
                    btn_play.setPressedIcon(new ImageIcon("src/pic/pause_pressed.png"));
                    isPlaying = true;
                    mSlider.timer.start();
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
    }
}
