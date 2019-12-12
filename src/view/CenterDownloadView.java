package view;

import awt.DownloadPanel;
import model.Music;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class CenterDownloadView extends JPanel {

    private int WIDTH = 1200;
    private int HEIGHT = 820;
    public static boolean isDownloading = false;
    public static ArrayList<Music> music = new ArrayList<>();
    private static ArrayList<DownloadPanel> downloadPanels = new ArrayList<>();

    public CenterDownloadView(){
        setBackground(new Color(245,245,245));
        setBorder(new EmptyBorder(10,0,0,0));
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        JLabel lb_download = new JLabel("下载管理");
        lb_download.setFont(new Font (Font.DIALOG, Font.BOLD, 23));
        lb_download.setBorder(new EmptyBorder(0,20,0,20));
        lb_download.setPreferredSize(new Dimension(WIDTH,100));
        this.add(lb_download);

        if (isDownloading){
            initDownloadView();
        }
        else initBlankView();
    }

    private void initBlankView() {
        JLabel jLabel = new JLabel(new ImageIcon("src/pic/blank_view.png"));
        jLabel.setBorder(new EmptyBorder(160,0,0,0));
        this.add(jLabel);
    }

    private void initDownloadView() {
        downloadPanels.clear();
        for (int i = 0;i < music.size();i ++){
            downloadPanels.add(new DownloadPanel(music.get(i)));
        }
        for (int i = 0;i < downloadPanels.size();i ++){
            this.add(downloadPanels.get(i));
        }
    }

    public static void setProgressBar(Music music,int progress){
        for (int i = 0;i < downloadPanels.size(); i ++){
            if (downloadPanels.get(i).music == music){

            }
        }
    }
    public static void setStatus(Music music){
        for (int i = 0;i < downloadPanels.size(); i ++){
            if (downloadPanels.get(i).music == music){
                downloadPanels.get(i).lb_status.setText("已完成");
                downloadPanels.get(i).isDownloading = false;
            }
        }
    }
}
