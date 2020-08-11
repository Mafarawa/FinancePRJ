package com.mafarawa.controller.authreg;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.SelectScene;
import com.mafarawa.model.UserModel;
import com.mafarawa.model.UserImage;
import com.mafarawa.model.AccountType;
import com.mafarawa.view.authreg.RegistrationView;
import com.mafarawa.controller.authreg.SelectImageController;
import com.mafarawa.view.main.MainWindow;

import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

public class RegistrationController extends RegistrationView {
	private SelectImageController sic;
	
	private static Logger logger;
	static { logger = Logger.getLogger(RegistrationController.class.getName()); }

	public RegistrationController(Stage stage) {
		super();
		sic = new SelectImageController(stage, super.selectImageButton);

		super.selectImageButton.setOnAction(e -> sic.getStage().show());
		super.doneButton.setOnAction(e -> registerUser(stage));
		super.cancelButton.setOnAction(e -> stage.setScene(App.selectScene(SelectScene.SELECT_USER_SCENE)));
	}

	private void registerCategories(int user_id) {
		logger.debug("User id = " + user_id);

		DBGate dbGate = DBGate.getInstance();

		try {
			dbGate.insertData("INSERT INTO income(income_id, category) VALUES(" + user_id + ", 'Зарплата');");
			dbGate.insertData("INSERT INTO expance(expance_id, category) VALUES(" + user_id + ", 'Продукты');");
			dbGate.insertData("INSERT INTO expance(expance_id, category) VALUES(" + user_id + ", 'Здоровье');");
			dbGate.insertData("INSERT INTO expance(expance_id, category) VALUES(" + user_id + ", 'Продукты');");
			dbGate.insertData("INSERT INTO expance(expance_id, category) VALUES(" + user_id + ", 'Кафе');");
			dbGate.insertData("INSERT INTO expance(expance_id, category) VALUES(" + user_id + ", 'Досуг');");
			dbGate.insertData("INSERT INTO expance(expance_id, category) VALUES(" + user_id + ", 'Транспорт');");
			dbGate.insertData("INSERT INTO expance(expance_id, category) VALUES(" + user_id + ", 'Подарки');");
			dbGate.insertData("INSERT INTO expance(expance_id, category) VALUES(" + user_id + ", 'Покупки');");
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	private void registerAccounts(int user_id) {
		logger.debug("User id = " + user_id);

		DBGate dbGate = DBGate.getInstance();

		try {
			dbGate.insertData("INSERT INTO account(account_id, name, type_id, balance) VALUES(" + user_id + ", 'Карта', " + AccountType.getIdByType("Карточный") + ", 0);");
			dbGate.insertData("INSERT INTO account(account_id, name, type_id, balance) VALUES(" + user_id + ", 'Наличные', " + AccountType.getIdByType("Текущий") + ", 0);");
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	private void registerUser(Stage stage) {
		String username = super.usernameInput.getText();
		String email = super.emailInput.getText();
		String password = Integer.toHexString(super.passwordInput.getText().hashCode());
		int userImage = sic.getUserImageValue();
		long shukherCode = UserModel.createShukherCode(username, email, password);

		if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
			checkLabel.setText("Заполните все поля");
			super.inputLayout.getChildren().add(checkLabel);
		} else {
			DBGate dbGate = DBGate.getInstance();

			try {
				PreparedStatement statement = dbGate.getDatabase().prepareStatement("INSERT INTO userfp (name, email, password, image, shukher_code) VALUES(?, ?, ?, ?, ?)");
				statement.setString(1, username);
				statement.setString(2, email);
				statement.setString(3, password);
				statement.setInt(4, userImage);
				statement.setLong(5, shukherCode);
				dbGate.insertData(statement);

				logger.info("user inserted: " + username + ", " + email + ", " + 
							password + ", " + UserImage.getImageById(userImage) + ", " + shukherCode);

				ResultSet rs = dbGate.executeData("SELECT userfp.id FROM userfp;");
				rs.last();

				statement = dbGate.getDatabase().prepareStatement("UPDATE userfp SET accounts=" + rs.getInt("id") + "WHERE userfp.id=" + rs.getInt("id"));
				dbGate.insertData(statement);

				statement = dbGate.getDatabase().prepareStatement("UPDATE userfp SET incomes=" + rs.getInt("id") + "WHERE userfp.id=" + rs.getInt("id"));
				dbGate.insertData(statement);

				statement = dbGate.getDatabase().prepareStatement("UPDATE userfp SET expances=" + rs.getInt("id") + "WHERE userfp.id=" + rs.getInt("id"));			
				dbGate.insertData(statement);
				
				registerAccounts(rs.getInt("id"));
				registerCategories(rs.getInt("id"));

				MainWindow mw = new MainWindow(stage, username);
				stage.setScene(mw.getScene());
			} catch(Exception e) {
				logger.error("Exception: ", e);
			}
		}
	}
}