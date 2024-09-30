package org.bankgui.bank;

import java.util.ArrayList;
import java.util.List;

public class WrapperClass {
    private List<Kunde> kunden;

    public WrapperClass() {
        this.kunden = new ArrayList<>();
    }

    public WrapperClass(List<Kunde> kunden) {
        this.kunden = kunden != null ? kunden : new ArrayList<>();
    }

    public List<Kunde> getKunde() {
        return kunden;
    }

    public void setKunden(List<Kunde> kunden) {
        this.kunden = kunden;
    }
}
