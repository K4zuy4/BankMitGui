package org.bankgui.bank;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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
    Button kontoWechselnButton;
    @FXML
    Button ueberweisenButton;

    private AtomicBoolean ueberweisenWindowOpen = new AtomicBoolean(false);

    @FXML
    protected void onAuszahlButtonClick() throws IOException {
        Konto konto = CurrentUserHandler.getAngemeldeterKunde().getKonto(KontoScreenController.getKtoNr());
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
            saveData.savedata();
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
        Konto konto = CurrentUserHandler.getAngemeldeterKunde().getKonto(KontoScreenController.getKtoNr());
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
            saveData.savedata();
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
        welcomeText.setText("Willkommen zurück " + CurrentUserHandler.getAngemeldeterKunde().getName() + "!");
        Konto konto = CurrentUserHandler.getAngemeldeterKunde().getKonto(KontoScreenController.getKtoNr());
        kontoText.setText("Kontostand: " + konto.getKontostand());
        kundennummerText.setText("KdNr: " + CurrentUserHandler.getAngemeldeterKunde().getKdNr());
        kontonummerText.setText("KtoNr: " + konto.getKtoNr());
    }

    @FXML
    protected void onKontoWechselnButtonClick() throws IOException {
        Main.ladeFXML("KontoScreen");
    }

    @FXML
    protected void onUeberweisenButtonClick() throws IOException {
        if (!ueberweisenWindowOpen.get()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ueberweisenScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);

            ueberweisenScreenController controller = loader.getController();
            controller.setMainScreenController(this);

            secondaryStage.show();
            ueberweisenWindowOpen.set(true);

            secondaryStage.setOnCloseRequest(event -> ueberweisenWindowOpen.set(false));
        }
    }

    @FXML
    public void updateKontoText(String newText) {
        kontoText.setText(newText);
    }
}