package awt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.exit;

public class MSlider extends JSlider implements Runnable{
    private int currentProgress = 0;
    private int MAX_PROGRESS;
    public Timer timer;

    public MSlider(){
        super(0,500,0);
        setUI(new MSliderUI(this));
        this.setPreferredSize(new Dimension(700, 27));
        this.MAX_PROGRESS = this.getMaximum();
        this.setBackground(Color.white);
        //this.setAutoscrolls(true);
        animate();
    }
    public Timer animate(){
        MSlider mSlider = this;
        currentProgress = this.getValue();
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProgress ++;
                if (currentProgress > MAX_PROGRESS) {
                    currentProgress = 0;
                }
                mSlider.setValue(currentProgress);
            }
        });
        //timer.start();
        return timer;
    }

    @Override
    public void run() {
        Timer timer;
        timer = animate();
        synchronized (this) {
            timer.start();
        }
        while (timer.isRunning()) {
            if (Thread.currentThread().isInterrupted()) {
                //¥¶¿Ì÷–∂œ¬ﬂº≠
                timer.stop();
                break;
                //Thread.currentThread().interrupted();
            }
        }
    }
}
