package Klasy;

import Klasy.PracownikUczelni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class PracownikBadawczoDydaktyczny extends PracownikUczelni implements Serializable {
    int liczbaPublikacji;
    Object[] mapaWartosci;

    public PracownikBadawczoDydaktyczny(Object[] dane) {
        super((String) dane[1], (String) dane[2], (String) dane[3], (Integer) dane[4], (String) dane[5],
                (String) dane[6], (Integer) dane[7], (Integer) dane[8]);
        this.liczbaPublikacji = (Integer) dane[9];
        
        mapaWartosci = new Object[] {"PracownikBadawczoDydaktyczny", imie, nazwisko, PESEL, wiek, plec, stanowiskoPracy,
                stazPracy, pensja, liczbaPublikacji};
    }

    /*
    (String) dane[1], (String) dane[2], (String) dane[3], (Integer) dane[4], (String) dane[5],
                        (String) dane[6], (Integer) dane[7], (Integer) dane[8], (Integer) dane[9]
     */

    public Object[] getMapaWartosci() {
        return mapaWartosci;
    }

    public int getLiczbaPublikacji() {
        return liczbaPublikacji;
    }

    public void setLiczbaPublikacji(int liczbaPublikacji) {
        this.liczbaPublikacji = liczbaPublikacji;
    }

    public ArrayList<String> podajDane() {

        ArrayList<String> daneSuper = super.podajDane();

        ArrayList<String> dane = new ArrayList<>(Arrays.asList(

                "Liczba publikacji: " + liczbaPublikacji
        ));

        daneSuper.addAll(dane);

        return daneSuper;
    }
}
