package cinema_project.ui.view;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class AddActorsOrDirectors extends JFrame{
    private JPanel panel;
    private JButton addButton;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField citizenShip;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton addToFilmButton;
    private JButton deleteFromFilmButton;

    public JButton getFinallyAddToFilmsButton() {
        return finallyAddToFilmsButton;
    }

    private JButton finallyAddToFilmsButton;
    private JSpinner dateSpiner;
    private JButton selectButton;

    public JSpinner getDateSpiner() {
        return dateSpiner;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public JComboBox getComboBox2() {
        return comboBox2;
    }

    public JButton getAddToFilmButton() {
        return addToFilmButton;
    }

    public JButton getDeleteFromFilmButton() {
        return deleteFromFilmButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JTextField getFirstName() {
        return firstName;
    }

    public JTextField getLastName() {
        return lastName;
    }

    public JTextField getCitizenShip() {
        return citizenShip;
    }
    private SpinnerDateModel model1;

    public SpinnerDateModel getModel1() {
        return model1;
    }

    public AddActorsOrDirectors(){
        setContentPane(panel);
        setResizable(false);
        setSize(1200,500);
        model1 = new SpinnerDateModel(new Date(), null, null,
                Calendar.DAY_OF_MONTH);

        dateSpiner.setModel(model1);
        String format = "yyyy-MM-dd";
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpiner, format);
        dateSpiner.setEditor(editor);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public JButton getSelectButton() {
        return selectButton;
    }
}
