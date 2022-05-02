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

    final GridBagConstraints gbc = new GridBagConstraints();
    private Insets insets;

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
        startMenu.setLayout(new GridBagLayout());

        titleText = new JLabel();
        titleText.setText("Dungeon Adventure");

        gbc.gridx = 0;
        gbc.gridy = 0;
        startMenu.add(titleText, gbc);

        startMenuButtonGroup = new ButtonGroup();

        startMenuButtonGroup.add(startButton);
        startMenuButtonGroup.add(settingsButton);
        startMenuButtonGroup.add(exitButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        insets = new Insets(300,0,0,0);
        gbc.insets = insets;
        startMenu.add(startButton, gbc);
        insets = new Insets(0,0,0,0);
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 2;
        startMenu.add(settingsButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        startMenu.add(exitButton, gbc);

        repaint();
        this.add(startMenu);
        startMenu.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
