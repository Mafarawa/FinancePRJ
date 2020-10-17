package com.mafarawa.controller.analysis;

import com.mafarawa.view.analysis.AnalyseExpancesView;
import com.mafarawa.connect.DBGate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;

public class AnalyseExpancesController extends AnalyseExpancesView {
    private int id;
    private ObservableList<String> categories;
    private Map<Date, Integer> map;

    private static Logger logger;
    static { logger = Logger.getLogger(AnalyseExpancesController.class.getName()); }

    public AnalyseExpancesController(Stage stage, String name) {
		DBGate dbGate = DBGate.getInstance();
		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.id FROM userfp WHERE name = '" + name + "';");
			rs.next();
			id = rs.getInt(1);
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}

        map = new HashMap<>();

		categories = FXCollections.observableArrayList();
		getCategories(id);
		super.categoryList.setItems(categories);

        super.categoryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                map.clear();
                logger.debug("Selected category: " + newValue);
                try {
                    ResultSet rs = dbGate.executeData("SELECT transactions.amount, transactions.transaction_date " + 
                                                      " FROM transactions " + 
                                                      " WHERE transactions.transaction_id = " + id + 
                                                      " AND transactions.to_point = '" + newValue + "';");

                    while(rs.next()) {
                        if(map.containsKey(rs.getDate(2))) {
                            map.put(rs.getDate(2), map.get(rs.getDate(2)) + rs.getInt(1));
                        } else {
                            map.put(rs.getDate(2), rs.getInt(1));
                        }
                    }

                    displayGraphs(newValue);
                } catch(SQLException sqle) {
                    logger.error("Exception: ", sqle);
                }
            }
        });
    }

    private void displayGraphs(String category) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(category);
        for(Map.Entry<Date, Integer> entrySet : map.entrySet()) {
            series.getData().add(new XYChart.Data<Number, Number>(entrySet.getKey().getDay(), entrySet.getValue()));
        }

        super.lineChart.getData().add(series);
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