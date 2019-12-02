package awt;

import view.*;

import javax.naming.Context;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.html.parser.ContentModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Date;

public class MLabel extends JLabel implements MouseListener {
    private String name;
    private boolean isSelected = false;
    private Context context;

    public MLabel(String name,String text,String path){
        super();
        this.name = name;
        this.setOpaque(true);
        this.setIcon(new ImageIcon(path));
        this.setText("   " + text);
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(235,34));
        this.setBorder(new EmptyBorder(0,0,0,0));
        this.addMouseListener(this);
        this.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        System.out.println("rferfe");
        switch (name){
            case "list":
                //MainView.mJpanel.removeAll();
//                MainView.mJpanel.add(new WestView(),BorderLayout.WEST);
//                MainView.mJpanel.add(new SouthView(4),BorderLayout.SOUTH);
                //MainView.mJpanel.remove(CENTER);
                //MainView.mJpanel.remove();

                //MainView.mJpanel.add(new CenterListView(),BorderLayout.CENTER);
                if (e.getButton() == MouseEvent.BUTTON3){
                    showPopupMenu(e.getComponent(), e.getX(), e.getY());
                }
                else {
                    MainView.mJpanel.remove(MainView.center);
                    MainView.center = new CenterListView();
                    MainView.mJpanel.add(MainView.center,BorderLayout.CENTER);

                    MainView.mJpanel.updateUI();
                    System.out.println("点击了歌单");
                }
                break;
            case "others":
                //MainView.mJpanel.removeAll();
//                MainView.mJpanel.add(new WestView(),BorderLayout.WEST);
//                MainView.mJpanel.add(new SouthView(4),BorderLayout.SOUTH);
                //MainView.mJpanel.add(new CenterOthersView(),BorderLayout.CENTER);
                MainView.mJpanel.remove(MainView.center);
                MainView.center = new CenterOthersView();
                MainView.mJpanel.add(MainView.center,BorderLayout.CENTER);
                MainView.mJpanel.updateUI();
                System.out.println("点击了推荐");
                break;
            case "nList":
                //showFileAddDialog(MainView.mJpanel);
                String inputContent = JOptionPane.showInputDialog(
                        MainView.mJpanel,
                        "请输入歌单名字:",
                        "我的歌单"
                );
                System.out.println("输入的内容: " + inputContent);

                break;
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        this.setBackground(new Color(224,224,224));
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        this.setBackground(new Color(245,245,245));
//        this.setForeground(new Color(216,30,6));
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        this.setBackground(new Color(245,245,245));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        this.setBackground(Color.white);
    }

    private static void showFileAddDialog(Component parent) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();

            System.out.println("打开文件: " + file.getAbsolutePath() + "\n\n");
        }
    }

    private static void showPopupMenu(Component invoker, int x, int y) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();
        // 创建 一级菜单
        JMenuItem addMenuItem = new JMenuItem("添加歌曲");
        JMenuItem deleteMenuItem = new JMenuItem("删除歌单");

        popupMenu.add(addMenuItem);
        popupMenu.add(deleteMenuItem);

        // 添加菜单项的点击监听器

        addMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileAddDialog(MainView.mJpanel);
            }
        });


        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("删除歌单");
            }
        });
        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }
}
