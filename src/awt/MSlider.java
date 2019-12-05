package awt;

import model.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.System.exit;

public class MSlider extends JSlider implements Runnable{
    private static int currentProgress = 0;
    private int MAX_PROGRESS;
    public Timer timer;

    public MSlider(){
        super(0,500,0);
        setUI(new MSliderUI(this));
        this.setPreferredSize(new Dimension(700, 27));
        this.MAX_PROGRESS = this.getMaximum();
        this.setBackground(Color.white);
        //this.setAutoscrolls(true);
        //animate(0);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                currentProgress = getValue();
//                System.out.println("鼠标位置" + getValue());
//                Player.player.jumpTo((int)(currentProgress / 500 * Player.getInstance().getDuration()));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    /**
     * 设置当前播放时长
     * @param duration
     */
    public void setMax(double duration){
        animate(duration);
    }

    /**
     * 将播放进度设置为0
     */
    public void setCurrentProgressToZero(){
        currentProgress = 0;
    }

    /**
     * 初始化计时器
     * @param duration
     * @return
     */
    public void animate(double duration){
//        MSlider mSlider = this;
//        currentProgress = this.getValue();
//        timer = new Timer((int)duration * 2, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                currentProgress ++;
//                if (currentProgress > MAX_PROGRESS) {
//                    currentProgress = 0;
//                }
//                mSlider.setValue(currentProgress);
//            }
//        });

        MSlider mSlider = this;
        currentProgress = 0;
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentProgress = (int) ((Player.getInstance().getCurrentTime() / Player.getInstance().getDuration()) * 500);
                if (currentProgress > MAX_PROGRESS) {
                    currentProgress = 0;
                }
                mSlider.setValue(currentProgress);
            }
        });

        //timer.start();
    }

    /**
     * timer开始计时，滑动条开始滑动
     */
    public void startPlaying(){
//        setMax(Player.getInstance().getDuration());//设置音乐时长
//        setCurrentProgressToZero();//将进度设为0
//        timer.start();
        animate(Player.getInstance().getDuration());
        System.out.println(Player.getInstance().getDuration());
        timer.start();
    }

    @Override
    public void run() {
//        Timer timer;
//        //timer = animate();
//        synchronized (this) {
//            timer.start();
//        }
//        while (timer.isRunning()) {
//            if (Thread.currentThread().isInterrupted()) {
//                //处理中断逻辑
//                timer.stop();
//                break;
//                //Thread.currentThread().interrupted();
//            }
//        }
    }
}
