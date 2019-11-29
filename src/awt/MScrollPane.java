package awt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MScrollPane extends JScrollPane {
    public MScrollPane(Component component){
        super(component,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.getVerticalScrollBar().setUI(new MScrollPaneUI());
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setBorder(new EmptyBorder(0,0,0,0));
    }
}
