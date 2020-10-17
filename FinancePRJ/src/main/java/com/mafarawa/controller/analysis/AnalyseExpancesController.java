package com.mafarawa.controller.analysis;

import com.mafarawa.view.analysis.AnalyseExpancesView;
import com.mafarawa.connect.DBGate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalyseExpancesController extends AnalyseExpancesView {
    private int id;
    private ObservableList<String> categories;

    private static Logger logger;
    static { logger = Logger.getLogger(AnalyseExpancesController.class.getName()); }

    public AnalyseExpancesController(Stage stage, String name) {
		DBGate dbGate = DBGate.getInstance();
		try {
			ResultSet rs = dbGate.executeData("select userfp.id from userfp where name = '" + name + "';");
			rs.next();
			id = rs.getInt(1);
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}

		categories = FXCollections.observableArrayList();
		getCategories(id);
		super.categoryList.setItems(categories);
    }

    private void getCategories(int id) {
        DBGate dbGate = DBGate.getInstance();

        try {
            ResultSet rs = dbGate.executeData("SELECT expance.category FROM expance WHERE expance.expance_id = " + id);
            while(rs.next()) {
                categories.add(rs.getString(1));
            }
        } catch(SQLException sqle) {
            logger.error("Exception: ", sqle);
        }
    }
}