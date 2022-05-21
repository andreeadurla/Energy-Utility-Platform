package ds.assignment.rpc.views.authentication;

import ds.assignment.rpc.views.styles.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AuthenticationView extends JFrame {

    private JTextField usernameField = new JTextField("");
    private JPasswordField passwordField = new JPasswordField("");
    private JButton submitButton = new JButton("Login");
    private JTextArea errorArea = new JTextArea();
    private JCheckBox showPassword = new JCheckBox("C++");

    public AuthenticationView() {
        initUI();

        this.pack();
        this.setTitle("Energy platform");
        this.setSize(500, 650);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setVisible(true);
    }

    private void initUI() {
        this.add(createForm());
    }

    private JPanel createForm() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Title
        c.insets.set(5, 10, 40,10);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0; c.gridy = 0;
        JLabel label = new JLabel("Login");
        Style.styleLabel(label, new Font("Arial", Font.ITALIC, 25));
        panel.add(label, c);

        //Fields and Labels
        c.insets.set(5, 10, 10,10);
        c.anchor = GridBagConstraints.LINE_START;

        //Username
        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Username"), c);

        c.gridx = 0; c.gridy = 2;
        Style.styleTextField(usernameField, 200, 30, new Font("Arial", Font.ITALIC, 15));
        panel.add(usernameField, c);

        //Password
        c.gridx = 0; c.gridy = 3;
        panel.add(new JLabel("Password"), c);

        c.gridx = 0; c.gridy = 4;
        Style.styleTextField(passwordField, 200, 30, new Font("Arial", Font.ITALIC, 15));
        panel.add(passwordField, c);

        //Error area
        c.gridx = 0; c.gridy = 5;
        Style.styleErrorArea(errorArea, 22);
        panel.add(errorArea, c);

        //Submit button
        c.insets.set(30, 10, 10, 10);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy = 6;
        Style.styleButton(submitButton, 200, 30, new Font("Arial", Font.ITALIC, 15));
        panel.add(submitButton, c);

        return panel;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public void addSubmitButtonListener(ActionListener e) {
        submitButton.addActionListener(e);
    }

    public void setVisibleErrorArea(boolean flag) {
        errorArea.setVisible(flag);
    }

    public void setErrorArea(String message) {
        errorArea.setText(message);
    }
}
