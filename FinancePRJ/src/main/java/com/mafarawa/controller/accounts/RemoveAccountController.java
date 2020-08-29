package com.mafarawa.controller.accounts;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

import com.mafarawa.connect.DBGate;
import com.mafarawa.view.accounts.RemoveAccountView;

public class RemoveAccountController extends RemoveAccountView {
	private static DBGate dbGate;
    private static Logger logger;

    static {
    	dbGate = DBGate.getInstance();
    	logger = Logger.getLogger(RemoveAccountController.class.getName());
    }

	public RemoveAccountController(Stage stage, String accountName) {
		super(stage, accountName);

	    super.yesButton.setOnAction(e -> {
	    	deleteAccount(accountName);
	    	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	    });

	    super.noButton.setOnAction(e -> super.childStage.close());
	}

	// This method used to delete selected account
	private void deleteAccount(String accountName) {
		DBGate dbGate = DBGate.getInstance();

		try {
			dbGate.insertData("DELETE FROM account WHERE name='" + accountName + "';");
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}