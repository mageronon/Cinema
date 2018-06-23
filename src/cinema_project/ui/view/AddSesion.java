package cinema_project.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.Calendar;
import java.util.Date;

public class AddSesion extends JFrame {

    private JPanel sessionPanel;
    private JTable table1;
    private JComboBox chooseFilm;

    public JPanel getSessionPanel() {
        return sessionPanel;
    }

    public JTable getTable1() {
        return table1;
    }

    public JComboBox getChooseFilm() {
        return chooseFilm;
    }


    public JComboBox getChooseHall() {
        return chooseHall;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getAddButton() {
        return addButton;
    }


    public JScrollPane getjScrollPane() {
        return jSrollpane;
    }
    private JComboBox chooseHall;
    private JButton deleteButton;
    private JButton addButton;
    private JScrollPane jSrollpane;
    private JSpinner timeSpinerH;
    public JScrollPane getjSrollpane() {
        return jSrollpane;
    }

    public JSpinner getTimeSpinerH() {
        return timeSpinerH;
    }

    public JSpinner getEndTimeSpinerH() {
        return endTimeSpinerH;
    }

    public JSpinner getDateSpinerY() {
        return dateSpinerY;
    }

    private JSpinner endTimeSpinerH;
    private JSpinner dateSpinerY;
    private DefaultTableModel model;

    public DefaultTableModel getModel() {
        return model;
    }

    private SpinnerDateModel model1;
    private SpinnerDateModel modelTime2;
    private SpinnerDateModel modelTime3;

    public SpinnerDateModel getModel1() {
        return model1;
    }

    public SpinnerDateModel getModelTime2() {
        return modelTime2;
    }

    public SpinnerDateModel getModelTime3() {
        return modelTime3;
    }

    public AddSesion(){

        String[] columnNames = {"Id", "Film", "Hall", "Date", "Session begin", "Session end"};
        Object[][] objects = {};
        model = new DefaultTableModel(objects, columnNames);
        table1.setModel(model);
        table1.setFillsViewportHeight(true);
        TableColumnModel colmodel = table1.getColumnModel();
        colmodel.getColumn(0).setPreferredWidth(100);
        colmodel.getColumn(1).setPreferredWidth(300);
        colmodel.getColumn(2).setPreferredWidth(100);
        colmodel.getColumn(3).setPreferredWidth(200);
        colmodel.getColumn(4).setPreferredWidth(200);
        colmodel.getColumn(5).setPreferredWidth(200);


        model1 = new SpinnerDateModel(new Date(), null, null,
                Calendar.DAY_OF_MONTH);

        dateSpinerY.setModel(model1);
        String format = "yyyy-MM-dd";
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinerY, format);
        dateSpinerY.setEditor(editor);


        modelTime2 = new SpinnerDateModel(new Date(), null, null,
                Calendar.DAY_OF_MONTH);
        timeSpinerH.setModel(modelTime2);
        String format1 = "HH:mm";
        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(timeSpinerH, format1);
        timeSpinerH.setEditor(editor1);

        modelTime3 = new SpinnerDateModel(new Date(), null, null,
                Calendar.DAY_OF_MONTH);
        endTimeSpinerH.setModel(modelTime3);
        String format2 = "HH:mm";
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(endTimeSpinerH, format2);
        endTimeSpinerH.setEditor(editor2);



        setContentPane(sessionPanel);
        setSize(1200,500);
        setTitle("Admin menu about sessions");
        setVisible(true);
        setLocationRelativeTo(null);


    }
}
