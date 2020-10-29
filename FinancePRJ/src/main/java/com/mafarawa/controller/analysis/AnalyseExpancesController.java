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

public class AnalyseExpancesController extends AnalyseExpancesView {
    private int id;
    private Map<Date, Integer> map;

    private static Logger logger;
    static { logger = Logger.getLogger(AnalyseExpancesController.class.getName()); }

    public AnalyseExpancesController(Stage stage, String name) {
        map = new HashMap<>();
		DBGate dbGate = DBGate.getInstance();
		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.id FROM userfp WHERE name = '" + name + "';");
			rs.next();
			id = rs.getInt(1);

            rs = dbGate.executeData("SELECT DISTINCT to_point FROM transactions WHERE transaction_id = " + id + " AND EXISTS(SELECT 1 FROM expance WHERE category = to_point);");
            while(rs.next()) {
                ResultSet graphs = dbGate.executeData("SELECT transactions.amount, transactions.transaction_date " + 
                                                      "FROM transactions " +
                                                      "WHERE transactions.transaction_id = " + id +
                                                      " AND transactions.to_point = '" + rs.getString("to_point") + "'");
                while(graphs.next()) {
                    if(map.containsKey(graphs.getDate(2))) {
                        map.put(graphs.getDate(2), map.get(graphs.getDate(2)) + graphs.getInt(1));
                    } else {
                        map.put(graphs.getDate(2), graphs.getInt(1));
                    }
                }
                
                displayGraphs(rs.getString("to_point"));
            }
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
    }

    private void displayGraphs(String category) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(category);
        for(Map.Entry<Date, Integer> entrySet : map.entrySet()) {
            series.getData().add(new XYChart.Data<Number, Number>(entrySet.getKey().getDay(), entrySet.getValue()));
        }

        super.lineChart.getData().add(series);
    }
}