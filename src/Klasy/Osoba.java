package Klasy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Osoba implements Serializable {
    String imie;
    String nazwisko;
    String PESEL;
    int wiek;
    String plec;
    Object[] mapaWartosci;

    public Osoba(String imie, String nazwisko, String PESEL, int wiek, String plec) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.PESEL = PESEL;
        this.wiek = wiek;
        this.plec = plec;

        this.mapaWartosci = new Object[] {imie, nazwisko, PESEL, wiek, plec};
    }

    public Object[] getMapaWartosci() {
        return mapaWartosci;
    }

    public void setMapaWartosci(Object[] mapaWartosci) {
        this.mapaWartosci = mapaWartosci;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public int getWiek() {
        return wiek;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public ArrayList<String> podajDane() {

        return new ArrayList<>(Arrays.asList(

                "Imię: " + imie,
                "Nazwisko: " + nazwisko,
                "PESEL: " + PESEL,
                "Wiek: " + wiek,
                "Płeć: " + plec

        ));
    }

    public boolean contains(String keyword) {
        return imie.contains(keyword) || nazwisko.contains(keyword) || PESEL.contains(keyword);
    }
}
