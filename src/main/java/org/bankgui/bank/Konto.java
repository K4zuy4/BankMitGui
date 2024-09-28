package org.bankgui.bank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public class Konto {
    private int ktoNr;
    private double kontostand;
    private static int anzahlKonten;

    static {
        try {
            anzahlKonten = Depression.loadAnzahlKonten();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Konto () {}

    public Konto(int _ktoNr) throws IOException {
        this.ktoNr = _ktoNr + 1000;
        this.kontostand = 0;
        Depression.saveAnzahlKonten(++anzahlKonten);
    }

    @JsonProperty("kontonummer")
    public int getKtoNr() {
        return this.ktoNr;
    }

    @JsonProperty("kontostand")
    public double getKontostand() {
        return this.kontostand;
    }

    @JsonIgnore
    public int getAnzahlKonten() {
        return anzahlKonten;
    }

    public void setAnzahlKonten(int _anzahlKonten) {
        anzahlKonten = _anzahlKonten;
        try {
            Depression.saveAnzahlKonten(anzahlKonten);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setKontostand(double _kontostand) {
        this.kontostand = _kontostand;
    }

    public boolean einzahlen(double betrag) {
        if(betrag < 0) {
            return false;
        }
        this.kontostand += betrag;
        return true;
    }

    public boolean auszahlen(double betrag) {
        if(betrag < 0) {
            return false;
        }
        if(this.kontostand < betrag) {
            return false;
        }
        this.kontostand -= betrag;
        return true;
    }
}
