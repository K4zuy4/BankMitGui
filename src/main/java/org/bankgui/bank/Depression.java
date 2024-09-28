package org.bankgui.bank;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Depression {
    private static final String ANZAHL_KONTEN_PATH = getAppDataPath("Bank/anzahlKonten.txt");
    private static final String DATEN_PFAD = getAppDataPath("Bank/daten.json");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Kunde> kunden = new ArrayList<>();

    public static void addListKunde(Kunde kunde) {
        kunden.add(kunde);
    }

    public static List<Kunde> getKunden() {
        return kunden;
    }

    private static String getAppDataPath(String fileName) {
        String appData = System.getenv("APPDATA");
        return appData + File.separator + fileName;
    }

    public static void saveData() throws IOException {
        Hilfe wrapper = new Hilfe(kunden);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATEN_PFAD), wrapper);
    }

    public static void createBankDirectory() {
        File file = new File(getAppDataPath("Bank"));
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void loadData() throws IOException {
        File file = new File(DATEN_PFAD);
        if (!file.exists()) {
            kunden.clear();
            return;
        }

        Hilfe wrapper = objectMapper.readValue(file, Hilfe.class);

        kunden.clear();
        kunden.addAll(wrapper.getKunde());
    }

    public static void saveAnzahlKonten(int anzahl) throws IOException {
        try (FileWriter writer = new FileWriter(ANZAHL_KONTEN_PATH)) {
            writer.write(String.valueOf(anzahl));
        }
    }

    public static int loadAnzahlKonten() throws IOException {
        File file = new File(ANZAHL_KONTEN_PATH);
        if (!file.exists()) {
            return 0;
        }

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        }
        return 0;
    }
}
