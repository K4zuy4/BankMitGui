package org.bankgui.bank;

import java.util.ArrayList;
import java.util.List;

public class Hilfe {
    private List<Kunde> kunden;

    public Hilfe() {
        this.kunden = new ArrayList<>();
    }

    public Hilfe(List<Kunde> kunden) {
        this.kunden = kunden != null ? kunden : new ArrayList<>();
    }

    public List<Kunde> getKunde() {
        return kunden;
    }

    public void setKunden(List<Kunde> kunden) {
        this.kunden = kunden;
    }
}
