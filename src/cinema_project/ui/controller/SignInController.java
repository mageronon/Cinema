package cinema_project.ui.controller;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.view.SignIn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static cinema_project.data_base.Password_Avtorisation.md5Custom;
import static javax.swing.JOptionPane.showMessageDialog;

public class SignInController extends Connect_Data_Base {

    Statement statement = null;

    private SignIn signInFrame;
    private JTextField email;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JLabel passwordCheck1;
    private JLabel emailCheck;
    private JLabel passwordCheck2;
    private JLabel phoneCheck;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField phoneNumber;
    private JButton signInButton;
    private JCheckBox showPasswordCheckBox;

    private  int ID;
    private String first_name;
    private String last_name;
    private String emailstr;
    private String pass;
    private String telephone;

    public SignInController(){
        initComponentsSignIn();
        initListenersSignIn();
    }

    private void initListenersSignIn() {
        signInButton.addActionListener(new SignInButton());
    }

    private void initComponentsSignIn() {
        this.signInFrame = new SignIn();
        email = signInFrame.getEmail();
        passwordField1 = signInFrame.getPasswordField1();
        passwordField2 = signInFrame.getPasswordField2();
        emailCheck = signInFrame.getEmailCheck();
        passwordCheck1 = signInFrame.getPasswordCheck1();
        passwordCheck2 = signInFrame.getPasswordCheck2();
        phoneCheck = signInFrame.getPhoneChek();
        firstName = signInFrame.getFirstName();
        lastName = signInFrame.getLastName();
        phoneNumber = signInFrame.getPhoneNumber();
        signInButton = signInFrame.getSignInButton();
        showPasswordCheckBox = signInFrame.getShowPasswordCheckBox();
    }

    public void showSignInWiundow(){
        signInFrame.setVisible(true);
        signInFrame.setTitle("Sign In");
    }

        public boolean checkEmail(String em){
            try {
                statement = connection.createStatement();

                String str = null;

                ResultSet resultSet = statement.executeQuery("select email from account");
                while(resultSet.next()){
                    str = resultSet.getString(1);
                    if(str.equals(em)) {
                        return false;
                    }
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

    public boolean checkPhone(String phone){
        try {
            statement = connection.createStatement();
            String str = null;

            ResultSet resultSet1 = statement.executeQuery("select telephone_num from client");
            while (resultSet1.next()){
                str = resultSet1.getString(1);
                if(str.equals(phone)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean ispresentAccount(){
        try {
            statement = connection.createStatement();

            ResultSet resultSet1 = statement.executeQuery("select * from client where telephone_num = " + phoneNumber.getText());

            if(resultSet1.next()) {
                return true;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class SignInButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                statement = connection.createStatement();
                emailstr = email.getText();
                first_name = firstName.getText();
                last_name = lastName.getText();
                telephone = phoneNumber.getText();
                pass = md5Custom(String.valueOf(passwordField2.getPassword()));
                ResultSet resultSet = statement.executeQuery("select MAX(accountid) + 1 from account");
                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }
                if(!checkEmail(emailstr)) {
                    emailCheck.setText("This email is used!");
                }else if(!checkPhone(telephone)){
                    emailCheck.setText("");
                    if(ispresentAccount()){
                        phoneCheck.setText("This phone number is used!");
                    }else {
                        statement.execute("insert Into account (accountid, email, password, promo, bought_tickets_num, admin, isachier) VALUES(" + ID + ", '"
                                + emailstr + "', '" + pass + "', false , 0, false ,false )");
                        statement.executeUpdate("UPDATE client SET accountid = " + ID +" where telephone_num = " + Integer.parseInt(phoneNumber.getText()));
                        signInFrame.setVisible(false);
                    }
                } else{
                    phoneCheck.setText("");
                    if((first_name.equals("")) || (last_name.equals(""))
                            || (String.valueOf(passwordField2.getPassword()).equals(""))
                            ||(String.valueOf(passwordField1.getPassword()).equals(""))
                            ||(emailstr.equals("")) || (telephone.equals(""))){
                        showMessageDialog(null, "Enter all textField, please.");
                    }else {
                        if(telephone.length() < 9){
                            showMessageDialog(null, "Incorrect telephone munber.");
                        }else if(String.valueOf(passwordField2.getPassword()).equals(String.valueOf(passwordField1.getPassword()))) {
                            statement.execute("insert Into account (accountid, email, password, promo, bought_tickets_num, admin, isachier) VALUES(" + ID + ", '"
                                    + emailstr + "', '" + pass + "', false , 0, false ,false )");
                            statement.execute("insert into client (telephone_num, first_name, last_name, accountid) VALUES ('" + telephone + "', '"
                                    + first_name + "', ' " + last_name + "', " + ID + ")");
                            signInFrame.setVisible(false);
                        }else{
                            //showMessageDialog(null, "Passwords do not match.");
                            passwordCheck2.setText("Passwords do not match!");
                        }
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }
}
