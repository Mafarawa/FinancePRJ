package com.mafarawa.controller.authreg;

import com.mafarawa.connect.DBGate;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.authreg.DropPasswordView;

import javafx.stage.Stage;
import org.apache.log4j.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DropPasswordController extends DropPasswordView {
    private boolean permission;
    private int valueInput;

    private static Logger logger;
    static { logger = Logger.getLogger(DropPasswordController.class.getName()); }

	public DropPasswordController(Stage stage, UserModel user) {
		super(stage);

        // Message sending through second thread
        Runnable sendEmailThread = () -> sendEmail(user.getName());
        new Thread(sendEmailThread).start();

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

    // This method used to send a message to users email which contains a shukher code
    private void sendEmail(String name) {
        DBGate dbGate = DBGate.getInstance();

        String shukherCode = "";
        String to = "";
        String from = "financeprjnoreply@gmail.com";
        String host = "smtp.gmail.com";
        String port = "465";

        logger.debug("Host: " + host + " port: " + port);

        try {
            ResultSet rs = dbGate.executeData("SELECT userfp.shukher_code, userfp.email FROM userfp WHERE userfp.name=" + "'" + name + "'" + ";");
            rs.next();
            shukherCode = rs.getString(1);
            to = rs.getString(2);

            logger.debug("From: " + from + " to: " + to);

            // Loading properties... Maybe i should load props from props file?
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.ssl.enable", "true");

            // Authorizing on gmail
            Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("financeprjnoreply", "_-sadjflk2421-_");
                    }
                }
            );

            String msg = "Здравствуйте <b>" + name + "!</b> Вы потеряли свой пароль. Вы можете сбросить пароль " +
                     "спомощью ниже приложеного кода: " + "\n" + "<hr>" + "\n" + "<h1>" + shukherCode + "</h1>";

            // Preparing message for sending
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Это вы забыли свой пароль?");
            message.setContent(msg, "text/html; charset=UTF-8");

            // LIFT OFF!
            Transport.send(message);
            logger.info("Message was transported");
        } catch(MessagingException | SQLException ae) {
            logger.error("Exception: ", ae);
        }
    }

    // This method used to drop password 
    private void dropPassword(int valueInput, UserModel user) {
        DBGate dbGate = DBGate.getInstance();

        String password1 = Integer.toHexString(super.passwordInput.getText().hashCode());
        String password2 = Integer.toHexString(super.confirmPasswordInput.getText().hashCode());

        // Confirming password...
        if(password1.equals(password2)) {
            logger.warn("Password will be changed to: " + password1);
            UserModel temp = new UserModel(user);

            try {
                // Setting up new password
                PreparedStatement statement = dbGate.getDatabase().prepareStatement("UPDATE userfp SET password = ? WHERE shukher_code = " + valueInput + ";");
                statement.setString(1, password1);
                dbGate.insertData(statement);

                // Setting up new shukher code
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

    // This method used to check shukher code that user typed with shukher code which contains in the database
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