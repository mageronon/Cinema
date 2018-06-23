package cinema_project.ui.view;

import javax.swing.*;

public class AmdinPanelIOInfo extends JFrame{
    private JPanel panel1;
    private JTable table1;

    public JPanel getPanel1() {
        return panel1;
    }

    public JTable getTable1() {
        return table1;
    }

    public AmdinPanelIOInfo(){
        setContentPane(panel1);
        setSize(800,500);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
