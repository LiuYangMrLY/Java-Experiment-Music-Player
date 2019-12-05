package awt;

import javafx.scene.image.Image;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class MSliderUI extends BasicSliderUI {
    private static final Color BACKGROUND01=new Color(211,47,47);
    private static final Color BACKGROUND02=new Color(211,47,47);
    private static final Color BACKGROUND03=new Color(187, 183, 183);
    public MSliderUI(JSlider arg0) {
        super(arg0);
    }
    @Override
    public void paintThumb(Graphics g){
        Graphics2D g2d=(Graphics2D) g;
        BasicStroke stroke=new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        g2d.setStroke(stroke);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp=new GradientPaint(0,0,BACKGROUND02,0,thumbRect.height,BACKGROUND02);
        g2d.setPaint(gp);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width,
                thumbRect.height);//修改为圆形
        //g2d.fillRoundRect(thumbRect.x, thumbRect.y, thumbRect.width + 4, thumbRect.height - 4,100,100);
        BasicStroke stroke1=new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        g2d.setStroke(stroke1);
        g2d.drawLine(8, thumbRect.height/2,thumbRect.x+8 , thumbRect.height/2);


    }
    @Override
    public void paintTrack(Graphics g){
        Graphics2D g2d=(Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        g2d.setPaint(new GradientPaint(0, 0,BACKGROUND03 , 0,trackRect.height,  BACKGROUND03, true));
        g2d.setStroke(new BasicStroke(4,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2d.drawLine(8, trackRect.height/2+1, trackRect.width+8, trackRect.height/2+1);
    }
}
