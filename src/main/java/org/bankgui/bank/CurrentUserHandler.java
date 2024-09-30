package org.bankgui.bank;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CurrentUserHandler {

    static Kunde angemeldeterKunde;

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
            System.out.println("3. Kunde löschen");


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
                    System.out.println("Kontonummer: " + kunde.getKonto().getKtoNr());
                    System.out.println("Kontostand: " + kunde.getKonto().getKontostand());
                    break;
                case "2":
                    for(Kunde kunden : saveData.getKunden()) {
                        System.out.println("Benutzername: " + kunden.getName());
                        System.out.println("Kundennummer: " + kunden.getKdNr());
                        System.out.println("Passwort: " + kunden.getPasswort());
                        System.out.println("Kontonummer: " + kunden.getKonto().getKtoNr());
                        System.out.println("Kontostand: " + kunden.getKonto().getKontostand());
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
                    kundeLoeschen.getKonto().setAnzahlKonten(kundeLoeschen.getKonto().getAnzahlKonten() - 1);
                    saveData.getKunden().remove(kundeLoeschen);
                    try {
                        saveData.saveData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Kunde gelöscht");
                    break;
                default:
                    System.out.println("Ungültige Eingabe");
                    break;
            }
        }
        }).start();
    }
}
