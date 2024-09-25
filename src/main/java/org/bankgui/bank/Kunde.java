package org.bankgui.bank;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public class Kunde {
    private int kdNr;
    private String benutzername;
    private String passwort;

    Konto konto;

    public Kunde() {}

    public Kunde(int _kdNr, String _name, String _password) throws IOException {
        this.benutzername = _name;
        this.kdNr = _kdNr + 1;
        this.passwort = _password;

        konto = new Konto(this.kdNr);
    }

    public Konto getKonto() {
        return this.konto;
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
