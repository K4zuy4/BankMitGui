package org.bankgui.bank;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SignUpController {

    @FXML
    TextField textField;
    @FXML
    Button registrierenButton;
    @FXML
    PasswordField passwordField;
    @FXML
    Button anmeldeButton;

    @FXML
    protected void onRegistrierenButtonClick() throws IOException {
        if (textField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            return;
        }

        if (saveData.getKunden().stream().anyMatch(kunde -> kunde.getName().equals(textField.getText()))) {
            registrierenButton.setText("Benutzername bereits vergeben");
            registrierenButton.setStyle("-fx-background-color: red;");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                registrierenButton.setText("Absenden");
                registrierenButton.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
        } else {
            Kunde kunde = new Kunde(saveData.loadAnzahlKonten(), textField.getText(), passwordField.getText());
            saveData.addListKunde(kunde);
            saveData.savedata();
            Main.ladeFXML("SignIn");
        }
    }

    @FXML
    protected void onAnmeldeButtonClick() throws IOException {
        Main.ladeFXML("SignIn");
    }
}
