package cinema_project;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.controller.MainFrameController;
import cinema_project.ui.model.users.User;

public class Main{

    public static User user = null;

    public static void main(String[] args){
        Connect_Data_Base db = new Connect_Data_Base();
        MainFrameController mainFrameController = new MainFrameController();
        mainFrameController.showMainFrameWindow();
    }
}
