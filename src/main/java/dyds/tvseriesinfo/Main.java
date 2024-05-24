package dyds.tvseriesinfo;

import dyds.tvseriesinfo.model.dataAccess.DataBase;
import dyds.tvseriesinfo.presenter.PresenterView;

public class Main {
    public static void main(String[] args) {
        DataBase.loadDatabase();

        PresenterView.getIntance();
    }
}
