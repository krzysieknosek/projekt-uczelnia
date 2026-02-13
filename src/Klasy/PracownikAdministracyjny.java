package Klasy;

import Klasy.PracownikUczelni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class PracownikAdministracyjny extends PracownikUczelni implements Serializable {
    int liczbaNadgodzin;
    Object[] mapaWartosci;

    public PracownikAdministracyjny(Object[] dane) {
        super((String) dane[1], (String) dane[2], (String) dane[3], (Integer) dane[4], (String) dane[5],
                (String) dane[6], (Integer) dane[7], (Integer) dane[8]);
        this.liczbaNadgodzin = (Integer) dane[9];

        mapaWartosci = new Object[] {"PracownikAdministracyjny", imie, nazwisko, PESEL, wiek, plec, stanowiskoPracy,
                stazPracy, pensja, liczbaNadgodzin};

        /*
        (String) dane[1], (String) dane[2], (String) dane[3], (Integer) dane[4], (String) dane[5],
                        (String) dane[6], (Integer) dane[7], (Integer) dane[8], (Integer) dane[9]
         */

    }

    public Object[] getMapaWartosci() {
        return mapaWartosci;
    }

    public int getLiczbaNadgodzin() {
        return liczbaNadgodzin;
    }

    public void setLiczbaNadgodzin(int liczbaNadgodzin) {
        this.liczbaNadgodzin = liczbaNadgodzin;
    }

    public ArrayList<String> podajDane() {

        ArrayList<String> daneSuper = super.podajDane();

        ArrayList<String> dane = new ArrayList<>(Arrays.asList(

                "Liczba nadgodzin: " + liczbaNadgodzin
        ));

        daneSuper.addAll(dane);

        return daneSuper;
    }
}
