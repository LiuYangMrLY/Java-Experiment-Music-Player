package awt;

import model.Music;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DownloadPanel extends JPanel {
    private boolean isDownloading = true;
    public Music music;
    private JLabel lb_songName = new JLabel();
    public JLabel lb_status = new JLabel();

    public DownloadPanel(Music music){
        this.setPreferredSize(new Dimension(1200,50));
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(flowLayout);
        this.music = music;

        setBorder(new EmptyBorder(0,20,0,20));
        lb_songName.setText(music.getName());
        lb_songName.setPreferredSize(new Dimension(1000,45));
        //System.out.println("歌曲名：" + lb_songName.getText());

        initView();
    }

    private void initView() {
        lb_songName.setBorder(new EmptyBorder(0,0,0,0));
        lb_songName.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        //lb_songName.setPreferredSize(new Dimension(WIDTH,100));
        this.add(lb_songName);

    }
}
