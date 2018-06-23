package cinema_project.ui.controller;

import cinema_project.Main;
import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.model.users.User;
import cinema_project.ui.view.LoginWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static cinema_project.data_base.Password_Avtorisation.md5Custom;
import static cinema_project.ui.controller.MainFrameController.admin;
import static javax.swing.JOptionPane.showMessageDialog;

public class LoginWindowController extends Connect_Data_Base {

    private LoginWindow logInFrame;
    private JButton logInButton;
    private JTextField email;
    private JCheckBox showPasswordCheckBox;
    private JButton signInButton;
    private JPasswordField passwordField1;

    private String emailstr;
    private String pass;

    private Statement statement = null;

    public LoginWindowController() {
        initComponentsLogin();
        initListenersLogin();
    }

    public void showLogInWiundow(){
        logInFrame.setVisible(true);
        logInFrame.setTitle("Log In");
    }

    private void initComponentsLogin(){
        this.logInFrame  = new LoginWindow();
        logInButton = logInFrame.getLogInButton();
        email = logInFrame.getEmail();
        showPasswordCheckBox = logInFrame.getShowPasswordCheckBox();
        signInButton = logInFrame.getSignInButton();
        passwordField1 = logInFrame.getPasswordField1();
    }

    private void initListenersLogin(){
        logInButton.addActionListener(new LogInButton());
        signInButton.addActionListener(new SignInButton());
    }

    private boolean checkEmail(String em){
        try {
            statement = connection.createStatement();

            String str = null;

            ResultSet resultSet = statement.executeQuery("select email from account");
            while(resultSet.next()){
                str = resultSet.getString(1);
                if(str.equals(em)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkPass(String password, String em){
        try {
            statement = connection.createStatement();

            String str = null;

            ResultSet resultSet = statement.executeQuery("select password from account where email = '" + em + "'");
            if(resultSet.next()) {
                str = resultSet.getString(1);
                if (str.equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int bought_tuckets_num(String em){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select bought_tickets_num from account where email = '" + em + "'");
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isAdmin(String em){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select admin from account where email = '" + em + "'");
            if(resultSet.next())
                 return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isCashier(String em){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select isachier from account where email = '" + em + "'");
            if(resultSet.next())
                return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getFirst(String em){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select first_name " +
                    "from client inner join account a on client.accountid = a.accountid " +
                    "where email = '" + em + "'");
            if (resultSet.next())
                    return resultSet.getString(1);
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getLast(String em){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select last_name " +
                    "from client inner join account a on client.accountid = a.accountid " +
                    "where email = '" + em + "'");
            if (resultSet.next())
                return resultSet.getString(1);
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getPhone(String em){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select telephone_num " +
                    "from client inner join account a on client.accountid = a.accountid " +
                    "where email = '" + em + "'");
            if (resultSet.next()){
                return resultSet.getString(1);
            }
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getBougth(String em){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select bought_tickets_num " +
                    "from client inner join account a on client.accountid = a.accountid " +
                    "where email = '" + em + "'");
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getPass(String em){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select password " +
                    "from client inner join account a on client.accountid = a.accountid " +
                    "where email = '" + em + "'");
            if (resultSet.next()){
                return resultSet.getString(1);
            }
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private class LogInButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            emailstr = email.getText();
            pass = md5Custom(String.valueOf(passwordField1.getPassword()));
            if(checkEmail(emailstr) && checkPass(pass, emailstr)){
                logInFrame.setVisible(false);
                bought_tuckets_num(emailstr);
                isAdmin(emailstr);
                Main.user = new User(emailstr, getFirst(emailstr), getLast(emailstr), getPhone(emailstr), getBougth(emailstr), getPass(emailstr), isAdmin(emailstr), isCashier(emailstr));
                MainFrameController.logInButton.setVisible(false);
                MainFrameController.logOutButton.setVisible(true);
                MainFrameController.setValueToAccount();

                if(isAdmin(emailstr)){
                    MainFrameController.tabbedPane1.add(admin, 2);
                    MainFrameController.tabbedPane1.setTitleAt(2, "Admin menu");
                }

                MainFrameController.saveChangeButton.setEnabled(true);
                MainFrameController.ticketsButton.setEnabled(true);
                MainFrameController.resetOldButton.setEnabled(true);
            }else{
                showMessageDialog(null, "Incorrect input.");
            }
        }
    }



    private class SignInButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SignInController signInController = new SignInController();
            signInController.showSignInWiundow();
        }
    }
}
