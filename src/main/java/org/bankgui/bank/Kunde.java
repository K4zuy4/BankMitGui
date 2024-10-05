package org.bankgui.bank;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Kunde {
    private int kdNr;
    private String benutzername;
    private String passwort;
    private List<Konto> konten = new ArrayList<>();

    Konto konto;

    public Kunde() {}

    public Kunde(int _kdNr, String _name, String _password) throws IOException {
        this.benutzername = _name;
        this.kdNr = _kdNr;
        this.passwort = _password;

        konten.add(new Konto(true,kdNr + 1000));
    }

    public Konto getKonto(int _ktoNr) {
        for(Konto konto : konten) {
            if(konto.getKtoNr() == _ktoNr) {
                this.konto = konto;
            }
        }
        return this.konto;
    }

    public void newKonto() throws IOException {
        int max = 0;
        for(Konto konto : konten) {
            if(konto.getKtoNr() > max) {
                max = konto.getKtoNr();
            }
        }

        int ktoNr = max + 1;
        konten.add(new Konto(ktoNr));

    }

    @JsonProperty("konten")
    public List<Konto> getKonten() {
        return this.konten;
    }

    @JsonProperty("benutzername")
    public String getName() {
        return this.benutzername;
    }

    @JsonProperty("kundennummer")
    public int getKdNr() {
        return this.kdNr;
    }

    @JsonProperty("passwort")
    public String getPasswort() {
        return this.passwort;
    }

    public void setName(String _name) {
        this.benutzername = _name;
    }
}
