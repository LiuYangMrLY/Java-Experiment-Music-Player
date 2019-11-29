package awt;


import javax.swing.*;
import java.awt.*;

public class MProgressBar extends JProgressBar {
    public MProgressBar(int duration){
        super(0,duration);
        setBackground(Color.white);
    }
}
