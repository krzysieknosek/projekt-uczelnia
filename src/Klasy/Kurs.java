package Klasy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Kurs implements Serializable {
    String nazwaKursu;
    String prowadzacy;
    int punktyECTS;
    ArrayList<Integer> zapisaniStudenci = new ArrayList<>();

    Object[] mapaWartosci;

    public Kurs(Object... dane) {
        this.nazwaKursu = (String) dane[1];
        this.prowadzacy = (String) dane[2];
        this.punktyECTS = (Integer) dane[3];

        mapaWartosci = new Object[] {"Kurs", nazwaKursu, prowadzacy, punktyECTS};
    }

    public Object[] getMapaWartosci() {
        return mapaWartosci;
    }

    public String getNazwaKursu() {
        return nazwaKursu;
    }

    public void setNazwaKursu(String nazwaKursu) {
        this.nazwaKursu = nazwaKursu;
    }

    public String getProwadzacy() {
        return prowadzacy;
    }

    public void setProwadzacy(String prowadzacy) {
        this.prowadzacy = prowadzacy;
    }

    public int getPunktyECTS() {
        return punktyECTS;
    }

    public void setPunktyECTS(byte punktyECTS) {
        this.punktyECTS = punktyECTS;
    }

    public ArrayList<String> podajDane() {

        return new ArrayList<>(Arrays.asList(

                "Nazwa kursu: " + nazwaKursu,
                "Prowadzący: " + prowadzacy,
                "Punkty ECTS: " + punktyECTS

        ));

    }

    public void dodajStudenta(Student student) {
        zapisaniStudenci.add(student.getNumerIndeksu());
    }

    public void usunStudenta(Student student) {
        zapisaniStudenci.remove(student);
    }

    public ArrayList<Integer> getZapisaniStudenci() {
        return zapisaniStudenci;
    }
}
