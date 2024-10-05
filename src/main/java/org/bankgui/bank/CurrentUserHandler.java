package org.bankgui.bank;

import javafx.application.Platform;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CurrentUserHandler {

    static Kunde angemeldeterKunde;
    static KontoScreenController kontoScreenController;

    public static void setKontoScreenController(KontoScreenController controller) {
        kontoScreenController = controller;
    }

    public static Kunde getAngemeldeterKunde() {
        return angemeldeterKunde;
    }

    public static void setAngemeldeterKunde(Kunde _angemeldeterKunde) {
        angemeldeterKunde = _angemeldeterKunde;
    }

    public static Kunde findeKundenNachNummer(List<Kunde> kundenListe, int kundennummer) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getKdNr() == kundennummer) {
                return kunde;
            }
        }
        return null;
    }

    public static void startTerminal() {
        new Thread(() -> {
        System.out.println("Willkommen im Bankterminal");

        while(true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Was möchten Sie tun?");
            System.out.println("1. Bestimmtes Kundenkonto anzeigen");
            System.out.println("2. Alle Kundenkonten anzeigen");
            System.out.println("3. Kunden löschen");
            System.out.println("4. Konto löschen");


            String eingabe = scanner.nextLine();

            switch (eingabe) {
                case "1":
                    System.out.println("Kundennummer eingeben:");
                    int kundennummer = scanner.nextInt();

                    Kunde kunde = findeKundenNachNummer(saveData.getKunden(), kundennummer);
                    if (kunde == null) {
                        System.out.println("Kunde nicht gefunden");
                        break;
                    }
                    System.out.println("Benutzername: " + kunde.getName());
                    System.out.println("Kundennummer: " + kunde.getKdNr());
                    System.out.println("Passwort: " + kunde.getPasswort());
                    for(Konto konto : kunde.getKonten()) {
                        System.out.println("Kontonummer: " + konto.getKtoNr());
                        System.out.println("Kontostand: " + konto.getKontostand());
                    }
                    break;
                case "2":
                    for(Kunde kunden : saveData.getKunden()) {
                        System.out.println("Benutzername: " + kunden.getName());
                        System.out.println("Kundennummer: " + kunden.getKdNr());
                        System.out.println("Passwort: " + kunden.getPasswort());
                        for(Konto konto : kunden.getKonten()) {
                            System.out.println("Kontonummer: " + konto.getKtoNr());
                            System.out.println("Kontostand: " + konto.getKontostand());
                        }
                        System.out.println();
                    }
                    break;
                case "3":
                    System.out.println("Kundennummer eingeben:");
                    int kundennummerLoeschen = scanner.nextInt();

                    Kunde kundeLoeschen = findeKundenNachNummer(saveData.getKunden(), kundennummerLoeschen);
                    if (kundeLoeschen == null) {
                        System.out.println("Kunde nicht gefunden");
                        break;
                    }
                    kundeLoeschen.getKonto(KontoScreenController.getKtoNr()).setAnzahlKonten(kundeLoeschen.getKonto(KontoScreenController.getKtoNr()).getAnzahlKonten() - 1);
                    saveData.getKunden().remove(kundeLoeschen);
                    try {
                        saveData.savedata();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Platform.runLater(() -> {
                        if (kontoScreenController != null) {
                            if(Main.isInMainScreen()) {
                                try {
                                    Main.ladeFXML("SignIn");
                                    Main.setInMainScreen(false);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            kontoScreenController.updateKontoListView();
                        }
                    });

                    System.out.println("Kunde gelöscht");
                    break;
                case "4":
                    System.out.println("Kundennummer eingeben:");
                    int kundennummerKontoLoeschen = scanner.nextInt();

                    System.out.println("Kontonummer eingeben:");
                    int kontonummerKontoLoeschen = scanner.nextInt();

                    Kunde kundeKontoLoeschen = findeKundenNachNummer(saveData.getKunden(), kundennummerKontoLoeschen);

                    if (kundeKontoLoeschen == null) {
                        System.out.println("Kunde nicht gefunden");
                        break;
                    }

                    kundeKontoLoeschen.getKonten().remove(kundeKontoLoeschen.getKonto(kontonummerKontoLoeschen));

                    try {
                        saveData.savedata();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Platform.runLater(() -> {
                        if (kontoScreenController != null) {
                            if(Main.isInMainScreen()) {
                                try {
                                    Main.ladeFXML("KontoScreen");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            kontoScreenController.updateKontoListView();
                        }
                    });

                    System.out.println("Konto gelöscht");
                    break;
                default:
                    System.out.println("Ungültige Eingabe");
                    break;
            }
        }
        }).start();
    }
}
