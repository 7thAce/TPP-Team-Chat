import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

import static javax.swing.text.StyleConstants.setForeground;

public class TeamChatGUI extends JFrame {
    public TeamChatGUI() {
        initComponents();
    }

    private void initComponents() {
        bluePanel = new JPanel();
        label1 = new JLabel();
        bluePctA = new JLabel();
        blueBarA = new JProgressBar();
        label2 = new JLabel();
        bluePctB = new JLabel();
        blueBarB = new JProgressBar();
        label3 = new JLabel();
        bluePctC = new JLabel();
        blueBarC = new JProgressBar();
        label4 = new JLabel();
        bluePctD = new JLabel();
        blueBarD = new JProgressBar();
        blueText = new JLabel();
        scrollPane1 = new JScrollPane();
        blueBox = new JTextPane();
        redPanel = new JPanel();
        label5 = new JLabel();
        redPctA = new JLabel();
        redBarA = new JProgressBar();
        label6 = new JLabel();
        redPctB = new JLabel();
        redBarB = new JProgressBar();
        label7 = new JLabel();
        redPctC = new JLabel();
        redBarC = new JProgressBar();
        label8 = new JLabel();
        redPctD = new JLabel();
        redBarD = new JProgressBar();
        redText = new JLabel();
        scrollPane2 = new JScrollPane();
        redBox = new JTextPane();

        //======== this ========
        setTitle("Twitch Plays Pokemon - Battle Revolution Team Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(1, 2));

        //======== bluePanel ========
        {
            bluePanel.setBackground(new Color(153, 153, 255));
            bluePanel.setLayout(new GridBagLayout());
            ((GridBagLayout)bluePanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)bluePanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)bluePanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)bluePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

            //---- label1 ----
            label1.setText("A");
            label1.setForeground(Color.red);
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            label1.setFont(new Font("Arial Black", Font.BOLD, 11));
            bluePanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- bluePctA ----
            bluePctA.setText("0%");
            bluePctA.setFont(new Font("Arial Black", Font.PLAIN, 11));
            bluePanel.add(bluePctA, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- blueBarA ----
            blueBarA.setForeground(Color.red);
            blueBarA.setBackground(new Color(204, 204, 255));
            bluePanel.add(blueBarA, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- label2 ----
            label2.setText("B");
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            label2.setForeground(Color.blue);
            label2.setFont(new Font("Arial Black", Font.BOLD, 11));
            bluePanel.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- bluePctB ----
            bluePctB.setText("0%");
            bluePctB.setFont(new Font("Arial Black", Font.PLAIN, 11));
            bluePanel.add(bluePctB, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- blueBarB ----
            blueBarB.setForeground(new Color(0, 0, 204));
            blueBarB.setBackground(new Color(204, 204, 255));
            bluePanel.add(blueBarB, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- label3 ----
            label3.setText("C");
            label3.setHorizontalAlignment(SwingConstants.CENTER);
            label3.setForeground(Color.green);
            label3.setFont(new Font("Arial Black", Font.BOLD, 11));
            bluePanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- bluePctC ----
            bluePctC.setText("0%");
            bluePctC.setFont(new Font("Arial Black", Font.PLAIN, 11));
            bluePanel.add(bluePctC, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- blueBarC ----
            blueBarC.setForeground(Color.green);
            blueBarC.setBackground(new Color(204, 204, 255));
            bluePanel.add(blueBarC, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- label4 ----
            label4.setText("D");
            label4.setHorizontalAlignment(SwingConstants.CENTER);
            label4.setForeground(Color.yellow);
            label4.setFont(new Font("Arial Black", Font.BOLD, 11));
            bluePanel.add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- bluePctD ----
            bluePctD.setText("0%");
            bluePctD.setFont(new Font("Arial Black", Font.PLAIN, 11));
            bluePanel.add(bluePctD, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- blueBarD ----
            blueBarD.setForeground(Color.yellow);
            blueBarD.setBackground(new Color(204, 204, 255));
            bluePanel.add(blueBarD, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- blueText ----
            blueText.setText("Waiting for new game...");
            bluePanel.add(blueText, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //======== scrollPane1 ========
            {

                //---- blueBox ----
                blueBox.setBackground(new Color(204, 204, 255));
                blueBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
                blueBox.setEditable(false);
                scrollPane1.setViewportView(blueBox);
            }
            bluePanel.add(scrollPane1, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(bluePanel);

        //======== redPanel ========
        {
            redPanel.setForeground(new Color(102, 102, 255));
            redPanel.setBackground(new Color(255, 153, 153));
            redPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)redPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)redPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)redPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)redPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

            //---- label5 ----
            label5.setText("A");
            label5.setForeground(Color.red);
            label5.setHorizontalAlignment(SwingConstants.CENTER);
            label5.setFont(new Font("Arial Black", Font.BOLD, 11));
            redPanel.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redPctA ----
            redPctA.setText("0%");
            redPctA.setFont(new Font("Arial Black", Font.PLAIN, 11));
            redPanel.add(redPctA, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redBarA ----
            redBarA.setForeground(Color.red);
            redBarA.setBackground(new Color(255, 204, 204));
            redPanel.add(redBarA, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- label6 ----
            label6.setText("B");
            label6.setHorizontalAlignment(SwingConstants.CENTER);
            label6.setForeground(Color.blue);
            label6.setBackground(Color.black);
            label6.setFont(new Font("Arial Black", Font.BOLD, 11));
            redPanel.add(label6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redPctB ----
            redPctB.setText("0%");
            redPctB.setFont(new Font("Arial Black", Font.PLAIN, 11));
            redPanel.add(redPctB, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redBarB ----
            redBarB.setForeground(new Color(0, 0, 204));
            redBarB.setBackground(new Color(255, 204, 204));
            redPanel.add(redBarB, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- label7 ----
            label7.setText("C");
            label7.setHorizontalAlignment(SwingConstants.CENTER);
            label7.setForeground(Color.green);
            label7.setBackground(Color.black);
            label7.setFont(new Font("Arial Black", Font.BOLD, 11));
            redPanel.add(label7, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redPctC ----
            redPctC.setText("0%");
            redPctC.setFont(new Font("Arial Black", Font.PLAIN, 11));
            redPanel.add(redPctC, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redBarC ----
            redBarC.setForeground(Color.green);
            redBarC.setBackground(new Color(255, 204, 204));
            redPanel.add(redBarC, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- label8 ----
            label8.setText("D");
            label8.setHorizontalAlignment(SwingConstants.CENTER);
            label8.setForeground(Color.yellow);
            label8.setBackground(Color.black);
            label8.setFont(new Font("Arial Black", Font.BOLD, 11));
            redPanel.add(label8, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redPctD ----
            redPctD.setText("0%");
            redPctD.setFont(new Font("Arial Black", Font.PLAIN, 11));
            redPanel.add(redPctD, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redBarD ----
            redBarD.setForeground(Color.yellow);
            redBarD.setBackground(new Color(255, 204, 204));
            redPanel.add(redBarD, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //---- redText ----
            redText.setText("Waiting for new game...");
            redPanel.add(redText, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            //======== scrollPane2 ========
            {

                //---- redBox ----
                redBox.setBackground(new Color(255, 204, 204));
                redBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
                redBox.setEditable(false);
                scrollPane2.setViewportView(redBox);
            }
            redPanel.add(scrollPane2, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(redPanel);
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    public void writeToRedBox(String message, Color messageColor, boolean isBolded)
    {
        //System.out.println(messageColor);
        StyledDocument doc = redBox.getStyledDocument();
        Style style = redBox.addStyle("style", null);
        StyleConstants.setForeground(style, messageColor);

        SimpleAttributeSet attributes = new SimpleAttributeSet();

        if (isBolded)
            attributes.addAttribute(StyleConstants.CharacterConstants.Bold, true);





        /*SimpleAttributeSet highAlert = new SimpleAttributeSet(boldBlue);
        StyleConstants.setFontSize(highAlert, 18);
        StyleConstants.setItalic(highAlert, true);
        StyleConstants.setForeground(highAlert, Color.red);  */

        redBox.setCaretPosition(doc.getLength());
        try {
            doc.insertString(redBox.getCaretPosition(), message, attributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        redBox.setCaretPosition(doc.getLength());
    }

    public void writeToBlueBox(String message, Color messageColor, boolean isBolded)
    {
        StyledDocument doc = blueBox.getStyledDocument();
        Style style = blueBox.addStyle("style", null);
        StyleConstants.setForeground(style, messageColor);

        SimpleAttributeSet attributes = new SimpleAttributeSet();

        if (isBolded)
            attributes.addAttribute(StyleConstants.CharacterConstants.Bold, true);

        blueBox.setCaretPosition(doc.getLength());
        try {
            doc.insertString(blueBox.getCaretPosition(), message, attributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        blueBox.setCaretPosition(doc.getLength());
    }

    public JPanel bluePanel;
    public JLabel label1;
    public JLabel bluePctA;
    public JProgressBar blueBarA;
    public JLabel label2;
    public JLabel bluePctB;
    public JProgressBar blueBarB;
    public JLabel label3;
    public JLabel bluePctC;
    public JProgressBar blueBarC;
    public JLabel label4;
    public JLabel bluePctD;
    public JProgressBar blueBarD;
    public JLabel blueText;
    public JScrollPane scrollPane1;
    public JTextPane blueBox;
    public JPanel redPanel;
    public JLabel label5;
    public JLabel redPctA;
    public JProgressBar redBarA;
    public JLabel label6;
    public JLabel redPctB;
    public JProgressBar redBarB;
    public JLabel label7;
    public JLabel redPctC;
    public JProgressBar redBarC;
    public JLabel label8;
    public JLabel redPctD;
    public JProgressBar redBarD;
    public JLabel redText;
    public JScrollPane scrollPane2;
    public JTextPane redBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}