import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {

    private JFrame mainGui;

    private JPanel startMenu;
    private JLabel titleText;
    private ButtonGroup startMenuButtonGroup;
    private JButton startButton = new JButton("Start");
    private JButton settingsButton = new JButton("Settings");
    private JButton exitButton = new JButton("Exit");

    private JPanel characterSelectMenu;
    private JPanel mapScreen;
    private JPanel combatScreen;

    public static void main(String[] args) {
        new MainGUI();
    }

    public MainGUI() {
        mainGui = new JFrame();
        this.setTitle("Dungeon Adventure");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        setStartMenuPanel();
        this.setVisible(true);
    }

    private void setStartMenuPanel() {
        startMenu = new JPanel();

        titleText = new JLabel();
        titleText.setText("Dungeon Adventure");
        startMenu.add(titleText);

        startMenuButtonGroup = new ButtonGroup();
        startButton = new JButton();
        settingsButton = new JButton();
        exitButton = new JButton();

        startMenuButtonGroup.add(startButton);
        startMenuButtonGroup.add(settingsButton);
        startMenuButtonGroup.add(exitButton);

        startMenu.add(startButton, BoxLayout.Y_AXIS);
        startMenu.add(settingsButton, BoxLayout.Y_AXIS);
        startMenu.add(exitButton, BoxLayout.Y_AXIS);

        this.add(startMenu);
        startMenu.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
