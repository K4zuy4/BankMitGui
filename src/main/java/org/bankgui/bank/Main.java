package org.bankgui.bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        ladeFXML("SignIn");
        primaryStage.show();

        try {
            saveData.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveData.createBankDirectory();
        CurrentUserHandler.startTerminal();
    }

    public static void ladeFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Scene scene;
        switch (fxml) {
            case "MainScreen":
                loader.setLocation(Main.class.getResource("MainScreen.fxml"));
                scene = new Scene(loader.load());
                MainScreenController controller = loader.getController();
                controller.setWelcomeText();
                primaryStage.setTitle("Banking App - " + CurrentUserHandler.getAngemeldeterKunde().getName());
                break;
            case "SignUp":
                loader.setLocation(Main.class.getResource("SignUp.fxml"));
                scene = new Scene(loader.load());
                primaryStage.setTitle("Banking App - Registrieren");
                break;
            case "SignIn":
                loader.setLocation(Main.class.getResource("SignIn.fxml"));
                scene = new Scene(loader.load());
                primaryStage.setTitle("Banking App - Anmelden");
                break;
            default:
                throw new IllegalArgumentException("Unbekannte FXML-Datei: " + fxml);
        }

        /*String css = String.valueOf(Main.class.getResource("styles.css"));
        scene.getStylesheets().add(css);*/

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}