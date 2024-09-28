package org.bankgui.bank;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScreenController {
    @FXML
    TextField betragField;
    @FXML
    TextField passwordField;
    @FXML
    Button einzahlButton;
    @FXML
    Button auszahlButton;
    @FXML
    Text welcomeText;
    @FXML
    Text kontoText;
    @FXML
    Text kontonummerText;
    @FXML
    Text kundennummerText;

    @FXML
    protected void onAuszahlButtonClick() throws IOException {
        Konto konto = Schmerzen.getAngemeldeterKunde().getKonto();
        String betragText = betragField.getText();
        double betrag;

        try {
            betrag = Double.parseDouble(betragText);
        } catch (NumberFormatException e) {
            betragField.setStyle("-fx-background-color: red;");
            auszahlButton.setStyle("-fx-background-color: red;");
            auszahlButton.setText("Ungültiger Betrag");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                auszahlButton.setText("Auszahlen");
                auszahlButton.setStyle("");
                betragField.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
            return;
        }

        if (konto.auszahlen(betrag)) {
            kontoText.setText("Kontostand: " + konto.getKontostand());
            Depression.saveData();
        } else {
            betragField.setStyle("-fx-background-color: red;");
            auszahlButton.setStyle("-fx-background-color: red;");
            auszahlButton.setText("Betrag unzulässig");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                auszahlButton.setText("Auszahlen");
                auszahlButton.setStyle("");
                betragField.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
        }
    }

    @FXML
    protected void onEinzahlButtonClick() throws IOException {
        Konto konto = Schmerzen.getAngemeldeterKunde().getKonto();
        String betragText = betragField.getText();
        double betrag;

        try {
            betrag = Double.parseDouble(betragText);
        } catch (NumberFormatException e) {
            betragField.setStyle("-fx-background-color: red;");
            einzahlButton.setStyle("-fx-background-color: red;");
            einzahlButton.setText("Ungültiger Betrag");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                einzahlButton.setText("Einzahlen");
                einzahlButton.setStyle("");
                betragField.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
            return;
        }

        if (konto.einzahlen(betrag)) {
            kontoText.setText("Kontostand: " + konto.getKontostand());
            Depression.saveData();
        } else {
            betragField.setStyle("-fx-background-color: red;");
            einzahlButton.setStyle("-fx-background-color: red;");
            einzahlButton.setText("Betrag unzulässig");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                einzahlButton.setText("Einzahlen");
                einzahlButton.setStyle("");
                betragField.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
        }
    }

    @FXML
    public void setWelcomeText() {
        welcomeText.setText("Willkommen zurück " + Schmerzen.getAngemeldeterKunde().getName() + "!");
        Konto konto = Schmerzen.getAngemeldeterKunde().getKonto();
        kontoText.setText("Kontostand: " + konto.getKontostand());
        kundennummerText.setText("KdNr: " + Schmerzen.getAngemeldeterKunde().getKdNr());
        kontonummerText.setText("KtoNr: " + konto.getKtoNr());
    }
}