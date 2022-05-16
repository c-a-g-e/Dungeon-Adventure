import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGUI extends JFrame implements ActionListener {

    private final JFrame myMainGui;

    private JPanel myStartMenu;
    private final JLabel myTitleText = new JLabel("Dungeon Adventure");
    private final JButton myStartButton = new JButton("Start");
    private final JButton mySettingsButton = new JButton("Settings");
    private final JButton myExitButton = new JButton("Exit");

    private JPanel myCharSelectMenu;
    private final JTextField myCharSelectTitle = new JTextField("Hero Select");
    private final JRadioButton myWarriorButton = new JRadioButton("Warrior");
    private final JRadioButton mySorceressButton = new JRadioButton("Sorceress");
    private final JRadioButton myThiefButton = new JRadioButton("Thief");
    private final JButton mySelectButton = new JButton("Select Character");
    private final JButton myBackButton = new JButton("Back");
    private final JLabel myImageLabel = new JLabel();
    ImageIcon myWarriorImageIcon = null;
    ImageIcon mySorceressImageIcon = null;
    ImageIcon myThiefImageIcon = null;
    private final JTextArea myHeroDescription = new JTextArea("");
    private final String myWarriorDescription = """
                      |    Warrior    |
        Health Points |      125      |
        Attack Speed  |      4        |
        Hit Chance    |      80%      |
        Minimum Damage|      35       |
        Maximum Damage|      60       |
        Block Chance  |      20%      |
        Special Move  |   Crushing    |
                      |     Blow      |
                      |Deals 75-175   |
                      |damage with a  |
                      |40% hit chance.|
        """;
    private final String mySorceressDescription = """
                      |   Sorceress   |
        Health Points |      75       |
        Attack Speed  |      5        |
        Hit Chance    |      70%      |
        Minimum Damage|      25       |
        Maximum Damage|      45       |
        Block Chance  |      30%      |
        Special Move  |     Heal      |
                      |               |
                      |Heals for 20HP |
        """;

    private final String myThiefDescription = """
                      |     Thief     |
        Health Points |      75       |
        Attack Speed  |      6        |
        Hit Chance    |      80%      |
        Minimum Damage|      40       |
        Maximum Damage|      45       |
        Block Chance  |      40%      |
        Special Move  |   Surprise    |
                      |    Attack     |
                      |If successful, |
                      |Thief gets an  |
                      |extra attack,  |
                      |if not, thief  |
                      |loses attack   |
                      |for the current|
                      |turn.          |
        """;

    private JPanel myMapScreen;
    private JPanel myCombatScreen;

    final GridBagConstraints gbc = new GridBagConstraints();
    private Insets insets;

    public static void main(String[] args) {
        new MainGUI();
    }

    public MainGUI() {
        myMainGui = new JFrame();
        this.setTitle("Dungeon Adventure");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        initImages();
        initStartMenuPanel();
        this.setVisible(true);
    }

    private void initImages() {
        BufferedImage myBufferedWarriorImage = null;
        BufferedImage myBufferedSorceressImage = null;
        BufferedImage myBufferedThiefImage = null;
        try {
            myBufferedWarriorImage = ImageIO.read(new File("res/myWarriorImage.jpg"));
            myBufferedSorceressImage = ImageIO.read(new File("res/mySorceressImage.jpg"));
            myBufferedThiefImage = ImageIO.read(new File("res/myThiefImage.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = 200;
        int height = 350;

        assert myBufferedWarriorImage != null;
        Image myWarriorImage = myBufferedWarriorImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        assert myBufferedSorceressImage != null;
        Image mySorceressImage = myBufferedSorceressImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        assert myBufferedThiefImage != null;
        Image myThiefImage = myBufferedThiefImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        myWarriorImageIcon = new ImageIcon(myWarriorImage);
        mySorceressImageIcon = new ImageIcon(mySorceressImage);
        myThiefImageIcon = new ImageIcon(myThiefImage);

    }

    private void initStartMenuPanel() {
        myStartMenu = new JPanel();
        myStartMenu.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        myStartMenu.add(myTitleText, gbc);

        ButtonGroup myStartMenuButtonGroup = new ButtonGroup();

        myStartMenuButtonGroup.add(myStartButton);
        myStartMenuButtonGroup.add(mySettingsButton);
        myStartMenuButtonGroup.add(myExitButton);

        myStartButton.addActionListener(this);
        mySettingsButton.addActionListener(this);
        myExitButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 1;
        insets = new Insets(300,0,0,0);
        gbc.insets = insets;
        myStartMenu.add(myStartButton, gbc);
        insets = new Insets(0,0,0,0);
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 2;
        myStartMenu.add(mySettingsButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        myStartMenu.add(myExitButton, gbc);

        repaint();
        this.add(myStartMenu);
        myStartMenu.setVisible(true);
    }

    private void initCharSelectPanel() {
        myCharSelectMenu = new JPanel(new BorderLayout());

        JPanel myCharSelectHeroButtons = new JPanel(new GridLayout(3, 1));
        ButtonGroup myHeroButtonGroup = new ButtonGroup();

        myCharSelectTitle.setEditable(false);
        myCharSelectMenu.add(myCharSelectTitle, BorderLayout.NORTH);

        myHeroButtonGroup.add(myWarriorButton);
        myHeroButtonGroup.add(mySorceressButton);
        myHeroButtonGroup.add(myThiefButton);

        myCharSelectHeroButtons.add(myWarriorButton);
        myCharSelectHeroButtons.add(mySorceressButton);
        myCharSelectHeroButtons.add(myThiefButton);

        myCharSelectMenu.add(myCharSelectHeroButtons, BorderLayout.WEST);

        JPanel myButtonPanel = new JPanel();
        myButtonPanel.add(myBackButton);
        myButtonPanel.add(mySelectButton);
        myCharSelectMenu.add(myButtonPanel, BorderLayout.SOUTH);

        myHeroDescription.setPreferredSize(new Dimension(200,800));
        myCharSelectMenu.add(myHeroDescription, BorderLayout.EAST);

        myImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        myCharSelectMenu.add(myImageLabel, BorderLayout.CENTER);

        myWarriorButton.addActionListener(this);
        mySorceressButton.addActionListener(this);
        myThiefButton.addActionListener(this);
        myBackButton.addActionListener(this);
        mySelectButton.addActionListener(this);

        this.add(myCharSelectMenu);
        myCharSelectMenu.setVisible(true);
    }
    //comment

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (myStartMenu.isVisible()) {
            if (myStartButton.equals(source)) {
                System.out.println("Start button pressed.");
                //this.remove(myStartMenu);
                initCharSelectPanel();
                myStartMenu.setVisible(false);
            } else if (mySettingsButton.equals(source)) {
                System.out.println("Settings button pressed.");
            } else if (myExitButton.equals(source)) {
                System.out.println("Exit button pressed.");
            }
        } else if (myCharSelectMenu.isVisible()) {
            if (myWarriorButton.equals(source)) {
                myHeroDescription.setText(myWarriorDescription);
                myImageLabel.setIcon(myWarriorImageIcon);
            } else if (mySorceressButton.equals(source)) {
                myHeroDescription.setText(mySorceressDescription);
                myImageLabel.setIcon(mySorceressImageIcon);
            } else if (myThiefButton.equals(source)) {
                myHeroDescription.setText(myThiefDescription);
                myImageLabel.setIcon(myThiefImageIcon);
            } else if (mySelectButton.equals(source)) {
                //set the character to whatever radio button is selected and start the game
            } else if (myBackButton.equals(source)) {
                myCharSelectMenu.setVisible(false);
                myStartMenu.setVisible(true);
            }
        }
    }
}
