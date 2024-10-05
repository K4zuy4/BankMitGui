package org.bankgui.bank;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ueberweisenScreenController {
    @FXML
    TextField kundennummerText2;
    @FXML
    TextField kontonummerText2;
    @FXML
    TextField betragField2;
    @FXML
    TextField passwordField2;
    @FXML
    Button bestaetigenButton;
    @FXML
    FXMLLoader loader = new FXMLLoader();

    private MainScreenController mainScreenController;

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    protected void onBestaetigenButtonClick() throws IOException {
        Konto konto = CurrentUserHandler.getAngemeldeterKunde().getKonto(KontoScreenController.getKtoNr());
        String kundennummerText = kundennummerText2.getText();
        String kontonummerText = kontonummerText2.getText();
        String betragText = betragField2.getText();
        String passwordText = passwordField2.getText();
        double betrag;

        try {
            betrag = Double.parseDouble(betragText);
        } catch (NumberFormatException e) {
            betragField2.setStyle("-fx-background-color: red;");
            bestaetigenButton.setStyle("-fx-background-color: red;");
            bestaetigenButton.setText("Ungültiger Betrag");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                bestaetigenButton.setText("Bestätigen");
                bestaetigenButton.setStyle("");
                betragField2.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
            return;
        }

        if(konto.getKontostand() >= betrag) {
            if (CurrentUserHandler.getAngemeldeterKunde().getPasswort().equals(passwordText)) {
                try {
                    Kunde zielKunde = CurrentUserHandler.findeKundenNachNummer(saveData.getKunden(), Integer.parseInt(kundennummerText));
                    zielKunde.getKonto(Integer.parseInt(kontonummerText)).einzahlen(betrag);
                    konto.auszahlen(betrag);
                } catch (Exception e) {
                    betragField2.setStyle("-fx-background-color: red;");
                    bestaetigenButton.setStyle("-fx-background-color: red;");
                    bestaetigenButton.setText("Überweisung fehlgeschlagen");
                    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

                    scheduler.schedule(() -> Platform.runLater(() -> {
                        bestaetigenButton.setText("Bestätigen");
                        bestaetigenButton.setStyle("");
                        betragField2.setStyle("");
                    }), 3, TimeUnit.SECONDS);

                    scheduler.shutdown();
                    return;
                }
            } else {
                passwordField2.setStyle("-fx-background-color: red;");
                bestaetigenButton.setStyle("-fx-background-color: red;");
                bestaetigenButton.setText("Passwort Falsch");
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

                scheduler.schedule(() -> Platform.runLater(() -> {
                    bestaetigenButton.setText("Bestätigen");
                    bestaetigenButton.setStyle("");
                    passwordField2.setStyle("");
                }), 3, TimeUnit.SECONDS);

                scheduler.shutdown();
                return;
            }
            String text = "Kontostand: " + konto.getKontostand();
            mainScreenController.updateKontoText(text);
            saveData.savedata();
        } else {
            betragField2.setStyle("-fx-background-color: red;");
            bestaetigenButton.setStyle("-fx-background-color: red;");
            bestaetigenButton.setText("Betrag unzulässig");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                bestaetigenButton.setText("Bestätigen");
                bestaetigenButton.setStyle("");
                betragField2.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
        }
    }
}
