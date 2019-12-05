package awt;

import model.Player;
import view.SouthView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.System.exit;

public class MSlider extends JSlider implements MouseListener{
    private static int currentProgress = 0;
    private int MAX_PROGRESS;
    public static Timer timer;

    public MSlider(){
        super(0,500,0);
        //setUI(new MSliderUI(this));
        setUI(new  javax.swing.plaf.metal.MetalSliderUI(){
            @Override
            public void paintThumb(Graphics g) {
                //绘制指示物
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(211,47,47));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width,
                        thumbRect.height);//修改为圆形

            }
            public void paintTrack(Graphics g) {
                //绘制刻度的轨迹
                int cy,cw;
                Rectangle trackBounds = trackRect;
                if (slider.getOrientation() == JSlider.HORIZONTAL) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setPaint(new Color(0, 0, 0, 20));//将背景设为黑色
                    cy = (trackBounds.height/2) - 2;
                    cw = trackBounds.width;

                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.translate(trackBounds.x, trackBounds.y + cy);
                    g2.fillRect(0, -cy + 5, cw, cy);

                    int trackLeft = 0;
                    int trackRight = 0;
                    trackRight = trackRect.width - 1;

                    int middleOfThumb = 0;
                    int fillLeft = 0;
                    int fillRight = 0;
                    //换算坐标
                    middleOfThumb = thumbRect.x + (thumbRect.width / 2);
                    middleOfThumb -= trackRect.x;

                    if (!drawInverted()) {
                        fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
                        fillRight = middleOfThumb;
                    } else {
                        fillLeft = middleOfThumb;
                        fillRight = !slider.isEnabled() ? trackRight - 1
                                : trackRight - 2;
                    }
                    //设定渐变,在这里从红色变为红色,则没有渐变,滑块划过的地方自动变成红色
                    g2.setPaint(new GradientPaint(0, 0,new Color(211,47,47), cw, 0,
                            new Color(211,47,47), true));
                    g2.fillRect(0, -cy + 5, fillRight - fillLeft, cy);

                    g2.setPaint(slider.getBackground());
                    g2.fillRect(10, 10, cw, 5);

                    g2.setPaint(Color.WHITE);
                    g2.drawLine(0, cy, cw - 1, cy);

                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_OFF);
                    g2.translate(-trackBounds.x, -(trackBounds.y + cy));
                }
                else {
                    super.paintTrack(g);
                }
            }

        });
        this.setPreferredSize(new Dimension(700, 27));
        this.MAX_PROGRESS = this.getMaximum();
        this.setBackground(Color.white);
        //this.setAutoscrolls(true);
        //animate(0);
        this.grabFocus();
        this.addMouseListener(this);
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("值" + getValue());

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
                    timer.stop();
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
        if (SouthView.haveMusic){
            currentProgress = 0;
        }
        else {
            animate(Player.getInstance().getDuration());
        }
        System.out.println(Player.getInstance().getDuration());
        timer.start();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SouthView.haveMusic)
        MSlider.timer.stop();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SouthView.haveMusic){
            Player.getInstance().jumpTo((int)(Double.valueOf(getValue()) / 500.0 * Player.getInstance().getDuration()));
            System.out.println("jump to:" + Double.valueOf(getValue()) / 500.0 * Player.getInstance().getDuration());
            currentProgress = getValue();
            timer.start();
            System.out.println("点击完成");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
