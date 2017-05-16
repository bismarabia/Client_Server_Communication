import javax.swing.*;
import java.awt.*;

public class GUInterface extends JFrame{
    static JFrame mainWindow;

    private GUInterface(){

        setLayout(new BorderLayout(5, 10));
        Panel panel1 = new Panel();
        JTextPane textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(300, 500));
        panel1.add(textPane);
        add(panel1, BorderLayout.EAST);
        add(new Panel(), BorderLayout.SOUTH);
        add(new Panel(), BorderLayout.WEST);
        add(new Panel(), BorderLayout.NORTH);
        add(new Panel(), BorderLayout.CENTER);
    }

    public static void main(String[] arg){
        GUInterface mainWindow = new GUInterface();
        mainWindow.setTitle("Client-Server chat");
        mainWindow.setSize(700, 500);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
