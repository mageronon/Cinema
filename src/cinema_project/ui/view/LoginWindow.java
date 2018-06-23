package cinema_project.ui.view;

import cinema_project.ui.controller.ResetWindowController;
import cinema_project.data_base.LimitedCharInput;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginWindow extends JFrame{

    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;

    private JPanel logInPanel;
    private JButton logInButton;
    private JTextField email;
    private JCheckBox showPasswordCheckBox;
    private JButton signInButton;
    private JPasswordField passwordField1;
    private JButton forgotPasswordButton;

    public LoginWindow(){
        setSize(WIDTH, HEIGHT);
        setContentPane(logInPanel);
        setLocationRelativeTo(null);
        email.setDocument(new LimitedCharInput(20));
        passwordField1.setDocument(new LimitedCharInput(20));
        showPasswordCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordField1.setEchoChar('•');
                } else {
                    passwordField1.setEchoChar((char) 0);
                }
            }
        });
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResetWindowController();
            }
        });
    }

    public JPanel getLogInPanel() {
        return logInPanel;
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public JTextField getEmail() {
        return email;
    }

    public JCheckBox getShowPasswordCheckBox() {
        return showPasswordCheckBox;
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }
}
