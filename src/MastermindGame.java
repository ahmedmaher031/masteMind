import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MastermindGame {
    private JFrame frame;
    private JComboBox<String>[] subjectSelectors;
    private JTextArea resultArea;
    private JButton submitButton;
    private String[] subjects = {"OS", "Software", "OOP", "CO", "DSP"};
    private String[] secretCode;

    public MastermindGame() {
        generateSecretCode();
        initializeGUI();
    }
    private void generateSecretCode() {
        Random rand = new Random();
        secretCode = new String[4];
        for (int i = 0; i < 4; i++) {
            secretCode[i] = subjects[rand.nextInt(subjects.length)];
        }
        System.out.println("Secret Code (for debugging): " + String.join(", ", secretCode));
    }

    private void initializeGUI() {
        frame = new JFrame("Mastermind Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("اختار ياحبيبي اللي انت عاوزه"));
        inputPanel.setBackground(new Color(49, 97, 51, 221));

        subjectSelectors = new JComboBox[4];
        for (int i = 0; i < 4; i++) {
            subjectSelectors[i] = new JComboBox<>(subjects);
            subjectSelectors[i].setPreferredSize(new Dimension(120, 30));
            inputPanel.add(subjectSelectors[i]);
        }

        submitButton = new JButton("يلا بينا");
        submitButton.setBackground(new Color(109, 33, 239));
        submitButton.setForeground(Color.WHITE);
        inputPanel.add(submitButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(163, 183, 255));
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(450, 200));

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        frame.setVisible(true);
    }

    private void checkGuess() {
        String[] guess = new String[4];
        for (int i = 0; i < 4; i++) {
            guess[i] = (String) subjectSelectors[i].getSelectedItem();
        }

        int correctPosition = 0;
        int correctColor = 0;
        boolean[] checkedSecret = new boolean[4];
        boolean[] checkedGuess = new boolean[4];

        for (int i = 0; i < 4; i++) {
            if (guess[i].equals(secretCode[i])) {
                correctPosition++;
                checkedSecret[i] = true;
                checkedGuess[i] = true;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (!checkedGuess[i]) {
                for (int j = 0; j < 4; j++) {
                    if (!checkedSecret[j] && guess[i].equals(secretCode[j])) {
                        correctColor++;
                        checkedSecret[j] = true;
                        break;
                    }
                }
            }
        }

        resultArea.append("انت اللي بتقول كدا " + String.join(", ", guess) +
                " | Correct Position: " + correctPosition + ", Correct Subject: " + correctColor + "\n");

        if (correctPosition == 4) {
            JOptionPane.showMessageDialog(frame, "Congratulations! You've guessed the code!", "عااش", JOptionPane.INFORMATION_MESSAGE);
            submitButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MastermindGame());
    }
}
