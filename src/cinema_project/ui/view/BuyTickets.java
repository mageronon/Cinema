package cinema_project.ui.view;

import cinema_project.data_base.LimitedCharInput;
import cinema_project.ui.controller.HallController;

import javax.swing.*;
import java.awt.event.*;

public class BuyTickets extends JFrame{
    private JPanel buyTickets;
    private JTextField phone;
    private JButton buyButton;

    public BuyTickets() {
        setSize(400, 150);
        setContentPane(buyTickets);
        setLocationRelativeTo(null);
        phone.setDocument(new LimitedCharInput(9));
        phone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char vchar = e.getKeyChar();
                if(!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE){
                    e.consume();
                }
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(phone.getText().length() == 9){
                    HallController.phoneBuyer = phone.getText();
                    setVisible(false);
                }
            }
        });
        addComponentListener ( new ComponentAdapter ()
        {
            public void componentShown ( ComponentEvent e )
            {

            }

            public void componentHidden ( ComponentEvent e )
            {
                if(phone.getText().length() == 9 ){
                    HallController.setTicket();
                    return;
                }

            }
        } );

        setVisible(true);

    }
}
