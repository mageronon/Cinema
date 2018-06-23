package cinema_project.ui.controller;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.data_base.SendEmail;
import cinema_project.ui.view.ResetPass;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cinema_project.data_base.Password_Avtorisation.md5Custom;

public class ResetWindowController extends Connect_Data_Base {
    Statement statement = null;
    private ResetPass resetPass;
    private JTextField emeilSendTo;
    private JTextField codeConfirm;
    private JButton sendButton;
    private JButton confirmButton;
    private JPasswordField newPass;
    private JPasswordField newPassRepeat;
    private JButton resetPasswordButton;
    public DateFormat dateFormat;
    public Date date;

    public ResetWindowController(){
        this.resetPass  = new ResetPass();
        emeilSendTo = resetPass.getEmeilSendTo();
        codeConfirm = resetPass.getCodeConfirm();
        sendButton = resetPass.getSendButton();
        confirmButton = resetPass.getConfirmButton();
        newPass = resetPass.getNewPass();
        newPassRepeat = resetPass.getNewPassRepeat();
        resetPasswordButton = resetPass.getResetPasswordButton();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(emeilSendTo.isEditable()) {
                    try {
                        new SendEmail(emeilSendTo.getText());
                        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        date = new Date();
                        try {
                            statement = connection.createStatement();
                            // now();
                            statement.execute("insert Into password_email (recovery_pass, validated_to) VALUES('" + SendEmail.passwordReset + "' ,  NOW() )");
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(resetPass, "Sorry! Cant send message to this e-mail now");
                    }
                    JOptionPane.showMessageDialog(resetPass, "Check your e-mail\n We send there a code");
                    emeilSendTo.setEditable(false);
                    codeConfirm.setEditable(true);
                }
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (codeConfirm.isEditable()){
                    if(SendEmail.passwordReset.equals(codeConfirm.getText())){
                        codeConfirm.setEditable(false);
                        newPass.setEditable(true);
                        newPassRepeat.setEditable(true);
                        JOptionPane.showMessageDialog(resetPass, "Now you can change your password.");
                    }else {
                        JOptionPane.showMessageDialog(resetPass, "Incorrect reset code.");
                    }
                }
            }
        });
        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newPass.isEditable()){
                    if(String.valueOf(newPass.getPassword()).equals(String.valueOf(newPassRepeat.getPassword()))){
                        if(String.valueOf(newPassRepeat.getPassword()).equals(checkPass(emeilSendTo.getText()))){
                            JOptionPane.showMessageDialog(resetPass, "We cant change your password to the same password!!!");
                        }else {
                            try {
                                statement = connection.createStatement();
                               // statement.execute("insert Into old_passwords (oldpassid, oldpassword) VALUES(" + getIdPass() + ", '" + checkPass(emeilSendTo.getText()) + "')");
                                updateStr("account","password" , md5Custom(String.valueOf(newPass.getPassword()))," email = '" + emeilSendTo.getText() + "'");
                                resetPass.setVisible(false);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

    }

    private void updateStr(String table, String what, String uprate_to, String where) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE "  + table + " SET " + what + " = '" + uprate_to + "' WHERE " + where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getIdPass(){
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select count(*) from old_passwords ");
            if(resultSet.next()) {
                return  resultSet.getInt(1) + 1;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return 1;
    }

    private String checkPass(String em){
        try {
            statement = connection.createStatement();

            String str = null;
            ResultSet resultSet = statement.executeQuery("select password from account where email = '" + em + "'");
            if(resultSet.next()) {
                str = resultSet.getString(1);

                return str;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
