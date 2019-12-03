import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setTitle("test");
        frame.setSize(1000, 1000);


        JLabel label = new JLabel("123");

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        try {
            URL url = new URL("https://www.baidu.com/img/bd_logo1.png");
            ImageIcon image = new ImageIcon(url);
            System.out.println(image.getIconHeight());

            label.setIcon(image);
            frame.add(label);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);


    }

}
