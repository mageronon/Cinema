package cinema_project.ui.view;

import cinema_project.ui.controller.HallController;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Hall extends JFrame {

    private JPanel panelScreen = new JPanel();
    private JPanel panelSeats = new JPanel();
    private JPanel panelChosenSeats = new JPanel();

    private static int COLL;
    private static int ROW;
    private  List<JButton> list = new ArrayList<JButton>();

    private JTextField Screen = new JTextField("Screen");
    private static DefaultListModel listModel = new DefaultListModel();
    private JList jlist = new JList(listModel);
    private static Pair<Integer, Integer> pair = null;
    private JButton buy = new JButton("Buy tickets");
    private int sessionId;

    private JButton orange = new JButton("");
    private JButton green = new JButton("");
    private JButton cean = new JButton("");
    private JButton grey = new JButton("");

    private JLabel orangeLable = new JLabel("-> free seats");
    private JLabel greenLabele = new JLabel("-> your selected places");
    private JLabel cyanLable = new JLabel("-> VIP seats");
    private JLabel greyLabel = new JLabel("-> booked seats");

    public JButton getBuy(){
        return buy;
    }

    public JPanel getPanelChosenSeats() {
        return panelChosenSeats;
    }

    public void setPanelChosenSeats(JPanel panelChosenSeats) {
        this.panelChosenSeats = panelChosenSeats;
    }

    public List<JButton> getList() {
        return list;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    public void setListModel(DefaultListModel listModel) {
        this.listModel = listModel;
    }

    public JList getJlist() {
        return jlist;
    }

    public void setJlist(JList jlist) {
        this.jlist = jlist;
    }

    public Hall(int coll, int row, int sesion){
        COLL = coll;
        ROW = row;
        sessionId = sesion;
        add(createGridPanel(),  BorderLayout.CENTER);
        panelChosenSeats.add(jlist);

        listModel.removeAllElements();
        TitledBorder p2Tborder= new TitledBorder("");

        TitledBorder p3Tborder= new TitledBorder("");

        orange.setEnabled(false);
        green.setEnabled(false);
        cean.setEnabled(false);
        grey.setEnabled(false);
        orange.setBackground(Color.ORANGE);
        green.setBackground(Color.GREEN);
        cean.setBackground(Color.CYAN);
        grey.setBackground(Color.gray);

        Screen.setEditable(false);
        Screen.setBackground(Color.BLACK);
        Screen.setHorizontalAlignment(SwingConstants.CENTER);
        Screen.setPreferredSize( new Dimension( 800, 24 ) );
        Screen.setForeground(Color.WHITE);
        panelScreen.add(Screen);

        panelChosenSeats.add(orange);
        panelChosenSeats.add(orangeLable);
        panelChosenSeats.add(green);
        panelChosenSeats.add(greenLabele);
        panelChosenSeats.add(cean);
        panelChosenSeats.add(cyanLable);
        panelChosenSeats.add(grey);
        panelChosenSeats.add(greyLabel);
        panelChosenSeats.add(new JScrollPane(jlist), BorderLayout.CENTER);
        panelChosenSeats.add(buy);



        panelScreen.setBorder(new CompoundBorder(p2Tborder,BorderFactory.createEmptyBorder(10,0,20,0)));
        panelScreen.setBorder(new CompoundBorder(p3Tborder,BorderFactory.createEmptyBorder(10,0,20,0)));
        add(panelScreen, BorderLayout.NORTH);
        add(panelChosenSeats, BorderLayout.SOUTH);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }



    private JPanel createGridPanel() {
        JPanel p = new JPanel(new GridLayout(ROW, COLL));
        panelSeats = p;
        for (int i = 0; i < ROW; i++) {
            JLabel label = new JLabel((i + 1) + "");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panelSeats.add(label);
            for(int j = 0; j < COLL; j++) {
                JButton gb = HallController.createGridButton(i, j );
                gb.setBackground(Color.ORANGE);
                list.add(gb);
                panelSeats.add(gb);
            }
            JLabel label1 = new JLabel((i + 1) + "");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            panelSeats.add(label1);
        }

        return panelSeats;
    }


}
