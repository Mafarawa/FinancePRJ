package com.mafarawa.controller.main;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import java.sql.*;
import org.apache.log4j.Logger;

import com.mafarawa.model.AccountType;
import com.mafarawa.connect.DBGate;
import com.mafarawa.view.main.RemoveAccountView;

public class RemoveAccountController extends RemoveAccountView {
    private static Logger logger;
    static { logger = Logger.getLogger(RemoveAccountController.class.getName()); }

	public RemoveAccountController(Stage stage, String accountName) {
		super(stage, accountName);

	    super.yesButton.setOnAction(e -> {
	    	deleteAccount(accountName);
	    	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	    });

	    super.noButton.setOnAction(e -> super.childStage.close());
	}

	private void deleteAccount(String accountName) {
		DBGate dbGate = DBGate.getInstance();

		try {
			dbGate.insertData("DELETE FROM account WHERE name='" + accountName + "';");
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}