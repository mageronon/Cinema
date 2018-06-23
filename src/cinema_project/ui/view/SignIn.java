package cinema_project.ui.view;

import cinema_project.data_base.LimitedCharInput;

import javax.swing.*;
import java.awt.event.*;

public class SignIn extends JFrame{

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private JPanel SignInPanel;
    private JTextField email;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField phoneNumber;
    private JButton signInButton;
    private JLabel passwordCheck1;
    private JLabel emailCheck;
    private JLabel passwordCheck2;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JLabel phoneCheck;
    private JCheckBox showPasswordCheckBox;

    public SignIn(){
        setSize(WIDTH, HEIGHT);
        setContentPane(SignInPanel);
        setLocationRelativeTo(null);
        email.setDocument(new LimitedCharInput(20));
        firstName.setDocument(new LimitedCharInput(20));
        lastName.setDocument(new LimitedCharInput(20));
        phoneNumber.setDocument(new LimitedCharInput(10));
        phoneNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char vchar = e.getKeyChar();
                if(!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE){
                    e.consume();
                }
            }
        });
        showPasswordCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordField1.setEchoChar('•');
                    passwordField2.setEchoChar('•');
                } else {
                    passwordField1.setEchoChar((char) 0);
                    passwordField2.setEchoChar((char) 0);
                }
            }
        });
    }

    public JCheckBox getShowPasswordCheckBox() {return showPasswordCheckBox;}

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public JTextField getFirstName() {
        return firstName;
    }

    public JLabel getPhoneChek() {
        return phoneCheck;
    }

    public JLabel getPasswordCheck1() {
        return passwordCheck1;
    }

    public JLabel getPasswordCheck2() {
        return passwordCheck2;
    }

    public JLabel getEmailCheck() {
        return emailCheck;
    }

    public JPanel getSignInPanel() {
        return SignInPanel;
    }

    public JPasswordField getPasswordField2() {
        return passwordField2;
    }

    public JTextField getEmail() {
        return email;
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public JTextField getLastName() {
        return lastName;
    }

    public JTextField getPhoneNumber() {
        return phoneNumber;
    }

}
