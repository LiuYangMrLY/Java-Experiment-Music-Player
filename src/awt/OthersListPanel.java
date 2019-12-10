package awt;

import model.MusicSheet;
import sun.plugin2.os.windows.FLASHWINFO;
import view.CenterListView;
import view.CenterOthersView;
import view.MainView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OthersListPanel extends JPanel implements MouseListener {
    private String picPath = "src/pic/others_list.png";
    private String listName = "那些年的格莱美";
    private String userName = "粉色龙类物种";
    private JLabel lb_pic = new JLabel();
    private JLabel lb_listName = new JLabel();
    private JLabel lb_userName = new JLabel();

    private MusicSheet musicSheet;

    public OthersListPanel(String picPath, String listName, String userName) {
        super();
        this.picPath = picPath;
        this.listName = listName;
        this.userName = userName;
        setFocusable(true);
        requestFocus();
        init();

    }

    public OthersListPanel(MusicSheet musicSheet){
        super();
        this.musicSheet = musicSheet;
        lb_pic.setPreferredSize(new Dimension(300,200));
        lb_pic.setBorder(new EmptyBorder(0,60,0,0));

        if(musicSheet.getPicture() != null){
            picPath = musicSheet.getPicture();
        }
        ImageIcon image = new ImageIcon(picPath);//实例化ImageIcon 对象
        image.setImage(image.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT ));
        lb_pic.setIcon(image);
        setFocusable(true);
        requestFocus();
        init();
    }

    private void init(){
        this.setPreferredSize(new Dimension(300,300));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setVgap(0);
        this.setLayout(flowLayout);
        this.setBackground(new Color(245,245,245));

        //lb_listName.setText(this.listName);
        //lb_pic.setIcon(new ImageIcon(this.picPath));
        //lb_userName.setText("by " + this.userName);

        lb_listName.setText(musicSheet.getName());

        lb_userName.setText(musicSheet.getCreator());

//        lb_userName.setOpaque(true);
//        lb_pic.setOpaque(true);
//        lb_listName.setOpaque(true);

        lb_listName.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
        lb_userName.setFont(new Font (Font.DIALOG, Font.PLAIN, 18));
        lb_userName.setForeground(new Color(0, 0, 0, 172));

        lb_userName.setPreferredSize(new Dimension(300,30));
        lb_listName.setPreferredSize(new Dimension(300,30));

        lb_listName.setBorder(new EmptyBorder(0,60,0,0));
        lb_userName.setBorder(new EmptyBorder(0,60,0,0));


        this.add(lb_pic);
        this.add(lb_listName);
        this.add(lb_userName);
        this.addMouseListener(this);
//        lb_pic.setOpaque(true);
//        lb_pic.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                setBackground(new Color(224,224,224));
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MainView.mJpanel.remove(MainView.center);
        System.out.println("歌曲数：" + musicSheet.getMusicArray().size());
        MainView.center = new CenterListView(musicSheet,false);
        MainView.mJpanel.add(MainView.center,BorderLayout.CENTER);

        MainView.mJpanel.updateUI();
        System.out.println("点击了歌单");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setBackground(new Color(224,224,224));

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setBackground(new Color(245,245,245));

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(new Color(245,245,245));
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
