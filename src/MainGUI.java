import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {

    private JFrame mainGui;

    private JPanel startMenu;
    private JButton startButton;
    private JButton settingsButton;
    private JButton exitButton;

    private JPanel characterSelectMenu;
    private JPanel mapScreen;
    private JPanel combatScreen;




    public MainGUI() {
        mainGui = new JFrame();
        startMenu = new JPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
