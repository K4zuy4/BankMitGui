package org.bankgui.bank;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SignInController {
    @FXML
    Button registrierenButton;
    @FXML
    Button anmeldeButton;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField textField;

    Kunde kunde;

    @FXML
    protected void onAnmeldeButtonClick() throws IOException {
        if (textField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            return;
        }

        List<Kunde> kunden = saveData.getKunden();

        for (Kunde _kunde : kunden) {
            if (_kunde.getName().equals(textField.getText()) && _kunde.getPasswort().equals(passwordField.getText())) {
                kunde = _kunde;
                break;
            }
        }

        if (kunde != null) {
            CurrentUserHandler.setAngemeldeterKunde(kunde);
            Main.ladeFXML("MainScreen");
        } else {
            anmeldeButton.setText("Falscher Benutzername oder Passwort");
            anmeldeButton.setStyle("-fx-background-color: red;");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                anmeldeButton.setText("Absenden");
                anmeldeButton.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
        }
    }

    @FXML
    protected void onRegistrierenButtonClick() throws IOException {
        Main.ladeFXML("SignUp");
    }
}
