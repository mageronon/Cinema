package cinema_project.ui.view;

import javax.swing.*;

public class FilmInfo extends JFrame{
    private JPanel panel1;
    private JTable table1;

    public JTable getTable1() {
        return table1;
    }

    public FilmInfo(){
        setContentPane(panel1);
        setSize(850,500);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
