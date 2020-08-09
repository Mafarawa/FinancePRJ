package com.mafarawa.controller.authreg;

import com.mafarawa.connect.DBGate;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.authreg.DropPasswordView;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

public class DropPasswordController extends DropPasswordView {
    private boolean permission;
    private int valueInput;	
    private static Logger logger;
    static { logger = Logger.getLogger(DropPasswordController.class.getName()); }

	public DropPasswordController(Stage stage, UserModel user) {
		super(stage);

        sendEmail(user.getName());

        super.shukherCodeInput.setOnKeyReleased(e -> {
            valueInput = 0;
            valueInput = Integer.parseInt(super.shukherCodeInput.getText());
            permission = checkShukherCode(valueInput, user.getName());
        });

        super.doneButton.setOnAction(e -> {
            boolean permission = this.permission;
            if(permission) dropPassword(valueInput, user);
        });		
	}

    public void sendEmail(String name) {
        DBGate dbGate = DBGate.getInstance();
        String shukherCode = "";
        String to = "";
        String from = "financeprjnoreply@gmail.com";
        String host = "smtp.gmail.com";
        String port = "465";

        logger.debug("Host: " + host + " port: " + port);

        try {
            ResultSet rs = dbGate.executeData("SELECT userfp.shukher_code, userfp.email FROM userfp WHERE userfp.name=" + "'" + name + "'" + ";");
            while(rs.next()) {
                shukherCode = rs.getString(1);
                to = rs.getString(2);
            }

            logger.debug("From: " + from + " to: " + to);
        } catch(Exception e) {
            logger.error("Exception: ", e);
        }

        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("financeprjnoreply", "_-sadjflk2421-_");
                }
            }
        );

        String msg = "Здравствуйте <b>" + name + "!</b> Вы потеряли свой пароль. Вы можете сбросить пароль " +
                     "спомощью ниже приложеного кода: " + "\n" + "<hr>" + "\n" + "<h1>" + shukherCode + "</h1>";

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Это вы забыли свой пароль?");
            message.setContent(msg, "text/html; charset=UTF-8");

            Transport.send(message);
            logger.info("Message was transported");
        } catch(Exception e) {
            logger.error("Exception: " + e);
        }
    }

    private void dropPassword(int valueInput, UserModel user) {
        String password1 = Integer.toHexString(super.passwordInput.getText().hashCode());
        String password2 = Integer.toHexString(super.confirmPasswordInput.getText().hashCode());

        if(password1.equals(password2)) {
            logger.warn("Password will be changed to: " + password1);
            DBGate dbGate = DBGate.getInstance();
            UserModel temp = new UserModel(user);

            try {
                PreparedStatement statement = dbGate.getDatabase().prepareStatement("UPDATE userfp SET password = ? WHERE shukher_code = " + valueInput + ";");
                statement.setString(1, password1);
                dbGate.insertData(statement);

                statement = dbGate.getDatabase().prepareStatement("UPDATE userfp SET shukher_code = ? WHERE name = '" + temp.getName() + "'");
                statement.setLong(1, temp.getShukherCode());
                dbGate.insertData(statement);
            } catch(Exception e) {
                logger.error("Exception: ", e);
            }

            logger.info("Password changed");

            super.childStage.close();
        }
    }

    private boolean checkShukherCode(int value, String name) {
        DBGate dbGate = DBGate.getInstance();

        try {
            ResultSet rs = dbGate.executeData("SELECT userfp.name FROM userfp WHERE shukher_code=" + value + ";");
            while(rs.next()) {
                if(rs.getString(1).equals(name)) {
                    super.shukherCodeInput.setDisable(true);
                    super.passwordInput.setDisable(false);
                    super.confirmPasswordInput.setDisable(false);

                    logger.debug("CORRECT CODE");

                    return true;
                }
            }
        } catch(Exception e) {
            logger.error("Exception: ", e);
        }

        return false;
    }	
}