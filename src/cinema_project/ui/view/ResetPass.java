package cinema_project.ui.view;

import cinema_project.data_base.LimitedCharInput;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ResetPass extends JFrame {

    private JPanel resetPass;
    private JTextField emeilSendTo;
    private JTextField codeConfirm;
    private JButton sendButton;
    private JButton confirmButton;
    private JPasswordField newPass;
    private JPasswordField newPassRepeat;
    private JButton resetPasswordButton;
    private JCheckBox hidePasswordCheckBox;

    public JPanel getResetPass() {
        return resetPass;
    }

    public JTextField getEmeilSendTo() {
        return emeilSendTo;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JPasswordField getNewPass() {
        return newPass;
    }

    public JPasswordField getNewPassRepeat() {
        return newPassRepeat;
    }

    public JButton getResetPasswordButton() {
        return resetPasswordButton;
    }

    public JTextField getCodeConfirm() {

        return codeConfirm;
    }

    public ResetPass() {
        setContentPane(resetPass);
        setLocationRelativeTo(null);
        emeilSendTo.setDocument(new LimitedCharInput(20));
        codeConfirm.setDocument(new LimitedCharInput(30));
        newPass.setDocument(new LimitedCharInput(20));
        newPassRepeat.setDocument(new LimitedCharInput(20));
        setSize(600, 400);
        setVisible(true);
        hidePasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    newPass.setEchoChar('•');
                    newPassRepeat.setEchoChar('•');
                } else {
                    newPass.setEchoChar((char) 0);
                    newPassRepeat.setEchoChar((char) 0);
                }
            }
        });
    }


}
