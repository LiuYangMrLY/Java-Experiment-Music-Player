package awt;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class NSliderUI extends BasicSliderUI{
    private Image Image;
    {
        try {
            Image = ImageIO.read(new File("src/pic/thumb2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NSliderUI(JSlider b) {
        super(b);
    }

    @Override
    public void paintThumb(Graphics g) {
        //绘制指示物
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(211,47,47));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);//修改为圆形
        g2d.drawImage(Image, thumbRect.x, thumbRect.y,null);

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
}
