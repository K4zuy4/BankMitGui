package org.bankgui.bank;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KontoScreenController {
    @FXML
    private ListView<String> kontoListView;
    @FXML
    private Button newKontoButton;

    private static int ktoNr;

    private FXMLLoader loader = new FXMLLoader();

    @FXML
    public void initialize() {
        Kunde kunde = CurrentUserHandler.getAngemeldeterKunde();
        for (Konto konto : kunde.getKonten()) {
            kontoListView.getItems().add("Kontonummer: " + konto.getKtoNr());
        }

        kontoListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                try {
                    handleDoubleClick(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        CurrentUserHandler.setKontoScreenController(this);
        updateKontoListView();
    }

    @FXML
    public void updateKontoListView() {
        kontoListView.getItems().clear();
        for (Konto konto : CurrentUserHandler.getAngemeldeterKunde().getKonten()) {
            kontoListView.getItems().add("Kontonummer: " + konto.getKtoNr());
        }
    }

    @FXML
    protected void onNewKontoButtonClick() {
        if(kontoListView.getItems().size() >= 5) {
            newKontoButton.setText("Maximal 5 Konten erlaubt");
            newKontoButton.setStyle("-fx-background-color: red;");
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            scheduler.schedule(() -> Platform.runLater(() -> {
                newKontoButton.setText("Absenden");
                newKontoButton.setStyle("");
            }), 3, TimeUnit.SECONDS);

            scheduler.shutdown();
            return;
        }

        try {
            CurrentUserHandler.getAngemeldeterKunde().newKonto();
            saveData.savedata();
            kontoListView.getItems().clear();
            for (Konto konto : CurrentUserHandler.getAngemeldeterKunde().getKonten()) {
                kontoListView.getItems().add("Kontonummer: " + konto.getKtoNr());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDoubleClick(MouseEvent event) throws IOException {
        String selectedItem = kontoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String kontonummer = selectedItem.split(": ")[1];

            ktoNr = Integer.parseInt(kontonummer);
            Konto konto = CurrentUserHandler.getAngemeldeterKunde().getKonten().stream()
                    .filter(k -> String.valueOf(k.getKtoNr()).equals(kontonummer))
                    .findFirst()
                    .orElse(null);
            if (konto != null) {
                Main.ladeFXML("MainScreen");
            }
        }
    }

    public static int getKtoNr() {
        return ktoNr;
    }
}