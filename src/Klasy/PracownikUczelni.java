package Klasy;

import Klasy.Osoba;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class PracownikUczelni extends Osoba implements Serializable {
    String stanowiskoPracy;
    int stazPracy;
    int pensja;

    public PracownikUczelni(String imie, String nazwisko, String PESEL, int wiek, String plec, String stanowiskoPracy, int stazPracy, int pensja) {
        super(imie, nazwisko, PESEL, wiek, plec);
        this.stanowiskoPracy = stanowiskoPracy;
        this.stazPracy = stazPracy;
        this.pensja = pensja;

    }

    public String getStanowiskoPracy() {
        return stanowiskoPracy;
    }

    public void setStanowiskoPracy(String stanowiskoPracy) {
        this.stanowiskoPracy = stanowiskoPracy;
    }

    public int getPensja() {
        return pensja;
    }

    public void setPensja(int pensja) {
        this.pensja = pensja;
    }

    public int getStazPracy() {
        return stazPracy;
    }

    public void setStazPracy(int stazPracy) {
        this.stazPracy = stazPracy;
    }

    public ArrayList<String> podajDane() {

        ArrayList<String> daneSuper = super.podajDane();

        ArrayList<String> dane = new ArrayList<>(Arrays.asList(
                "Stanowisko pracy: " + stanowiskoPracy,
                "Pensja: " + pensja,
                "Staż pracy: " + stazPracy
        ));

        daneSuper.addAll(dane);

        return daneSuper;
    }
}
