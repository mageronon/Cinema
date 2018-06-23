package cinema_project.ui.view;

import cinema_project.data_base.LimitedCharInput;
import cinema_project.ui.controller.AddCashierController;
import cinema_project.ui.controller.AddFilmController;
import cinema_project.ui.controller.AddSessionController;
import cinema_project.ui.controller.MainFrameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame{

    private JPanel mainPanel;
    private JButton logInButton;
    private JButton logOutButton;
    private JTabbedPane tabbedPane1;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JButton saveChangeButton;
    private JPanel films;
    private JPanel account;
    private JPanel admin;
    private JLabel image;
    private JComboBox dateChoose;
    private JComboBox TimeChoose;
    private JButton buyTicketsButton;
    private DefaultListModel listModel = new DefaultListModel();


    public JLabel getImage() {
        return image;
    }

    public JComboBox getDateChoose() {
        return dateChoose;
    }

    public JComboBox getTimeChoose() {
        return TimeChoose;
    }

    public JButton getBuyTicketsButton() {
        return buyTicketsButton;
    }

    private JTextArea descriptionText;
    private JComboBox actorsBox;
    private JComboBox directorsBox;
    private JLabel actorIcon;
    private JLabel nameOfFilm;

    public JTextArea getDescriptionText() {
        return descriptionText;
    }

    public JComboBox getActorsBox() {
        return actorsBox;
    }

    public JComboBox getDirectorsBox() {
        return directorsBox;
    }

    public JLabel getActorIcon() {
        return actorIcon;
    }


    public JButton getChangeSessionButton() {
        return changeSessionButton;
    }

    public JButton getChangeFilmButton() {
        return changeFilmButton;
    }

    public JButton getCashierButton() {
        return CashierButton;
    }


    public JLabel getNameOfFilm() {
        return nameOfFilm;

    }

    public JLabel getDirectorIcon() {
        return directorIcon;
    }

    private JLabel directorIcon;
    private JButton changeSessionButton;
    private JButton changeFilmButton;
    private JButton CashierButton;
    private JComboBox moviesByGenre;
    private JComboBox moviesByActor;
    private JComboBox moviesByDirectors;

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    private JComboBox comboBox1;

    public JButton getActorTicketsInfoButton() {
        return actorTicketsInfoButton;
    }

    public JButton getDirectorsPopularityInfoButton() {
        return directorsPopularityInfoButton;
    }

    public JButton getGenresPopularityInfoButton() {
        return genresPopularityInfoButton;
    }

    public JButton getInfoFilmButton() {
        return infoFilmButton;
    }

    private JButton actorTicketsInfoButton;
    private JButton directorsPopularityInfoButton;
    private JButton genresPopularityInfoButton;
    private JButton infoFilmButton;
    private JButton ticketsButton;
    private JButton resetOldButton;

    public JButton getResetOldButton() {
        return resetOldButton;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    public JButton getPrint() {
        return print;
    }

    private JButton print;

    public JList getTicketList() {
        return ticketList;
    }

    private JList ticketList = new JList(listModel);

    public JComboBox getMoviesByGenre() {
        return moviesByGenre;
    }

    public JComboBox getMoviesByActor() {
        return moviesByActor;
    }

    public JComboBox getMoviesByDirectors() {
        return moviesByDirectors;
    }


    public JButton getTicketsButton() {
        return ticketsButton;
    }

    public MainFrame(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setValidation();
        setResizable(false);
        setVisible(true);
        add(new JScrollPane(ticketList), BorderLayout.CENTER);


        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        pack();
        setBounds(0,0,screenSize.width, screenSize.height);

        CashierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCashierController();
            }
        });
        changeSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddSessionController();
            }
        });
        changeFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFilmController();
            }
        });
        tabbedPane1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if( e.getComponent().toString().equals("javax.swing.JTabbedPane[,0,0,1904x1041,invalid,layout=javax.swing.plaf.metal.MetalTabbedPaneUI$TabbedPaneLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=352,maximumSize=,minimumSize=,preferredSize=,haveRegistered=false,tabPlacement=TOP]")){
                    if(tabbedPane1.getSelectedIndex() == 2){
                        MainFrameController.adminMenu();
                    }
                    if(tabbedPane1.getSelectedIndex() == 0){
                        MainFrameController.setBoxes();
                    }
                }

            }
        });
;

    }

    private void setValidation(){
        firstNameField.setDocument(new LimitedCharInput(20));
        lastNameField.setDocument(new LimitedCharInput(20));
        phoneField.setDocument(new LimitedCharInput(10));
        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char vchar = e.getKeyChar();
                if(!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE){
                    e.consume();
                }
            }
        });
    }

    public JPanel getAccount() {return account;}

    public JPanel getAdmin() {return  admin;}

    public JPanel getFilms() { return films;}

    public JTabbedPane getTabbedPane1() {return tabbedPane1;}

    public JTextField getFirstNameField() {return firstNameField;}

    public JTextField getLastNameField() {return lastNameField;}

    public JTextField getPhoneField() {return phoneField;}

    public JButton getSaveChangeButton() {return saveChangeButton;}

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public JButton getLogOutButton() {return logOutButton; }

}
