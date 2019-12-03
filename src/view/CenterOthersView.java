package view;

import awt.MScrollPane;
import awt.OthersListPanel;
import model.MusicSheet;
import util.MOkhttp;
import util.MusicSheetChecker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class CenterOthersView extends JPanel {
    private JPanel jPanel = new JPanel();
    private int WIDTH = 1200;
    private int HEIGHT = 820;
    private ArrayList<MusicSheet> musicSheets = new ArrayList<>();
    public CenterOthersView(){
        super();
        jPanel.setPreferredSize(new Dimension(500,HEIGHT + 1000));
        jPanel.setBorder(new EmptyBorder(40,0,0,0));
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout.setVgap(0);
        jPanel.setLayout(flowLayout);
        jPanel.setBackground(new Color(245, 245, 245));
        this.setBackground(new Color(245,245,245));

        System.out.println("okhttp");
        musicSheets = MusicSheetChecker.getMusicSheetList();
        //musicSheets = MOkhttp.getMusicSheets();
        //System.out.println(musicSheets);

        for (int i = 0;i <musicSheets.size();i ++){
            jPanel.add(new OthersListPanel(musicSheets.get(i)));
        }
        jPanel.setPreferredSize(new Dimension(500,100 * musicSheets.size() + 300));

        MScrollPane scrollPane = new MScrollPane(jPanel);
        scrollPane.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        this.setLayout(new BorderLayout());

        this.add(scrollPane,BorderLayout.CENTER);
    }
}
