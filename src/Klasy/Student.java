package Klasy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Student extends Osoba implements Serializable, subskrybent {
    int numerIndeksu;
    int rokStudiow;
    ArrayList<String> kursy;
    boolean uczestnikErasmus;
    boolean pierwszyStopien;
    boolean drugiStopien;
    boolean studiujeStacjonarnie;
    
    Object[] mapaWartosci;

    public Student(Object[] dane) {
        super((String) dane[1], (String) dane[2], (String) dane[3], (Integer) dane[4], (String) dane[5]);
        this.numerIndeksu =  (Integer) dane[6];
        this.rokStudiow = (Integer) dane[7];
        this.kursy = (ArrayList<String>) dane[8];
        this.uczestnikErasmus = (boolean) dane[9];
        this.pierwszyStopien = (boolean) dane[10];
        this.drugiStopien = (boolean) dane[11];
        this.studiujeStacjonarnie = (boolean) dane[12];
        
        mapaWartosci = new Object[] {"Student", imie, nazwisko, PESEL, wiek, plec, numerIndeksu, rokStudiow, kursy,
                uczestnikErasmus, pierwszyStopien, drugiStopien, studiujeStacjonarnie};
    /*
    (String) dane[1], (String) dane[2], (String) dane[3], (Integer) dane[4], (String) dane[5],
                        (Integer) dane[6], (Integer) dane[7], (Klasy.Kurs[]) dane[8], (boolean) dane[9], (boolean)
                        dane[10], (boolean) dane[11], (boolean) dane[12]
     */


    }

    public Object[] getMapaWartosci() {
        return mapaWartosci;
    }

    public int getNumerIndeksu() {
        return numerIndeksu;
    }

    public void setNumerIndeksu(int numerIndeksu) {
        this.numerIndeksu = numerIndeksu;
    }

    public int getRokStudiow() {
        return rokStudiow;
    }

    public void setRokStudiow(int rokStudiow) {
        this.rokStudiow = rokStudiow;
    }

    public ArrayList<String> getKursy() {
        return kursy;
    }

    public void setKursy(ArrayList<String> kursy) {
        this.kursy = kursy;
    }

    public boolean isUczestnikErasmus() {
        return uczestnikErasmus;
    }

    public void setUczestnikErasmus(boolean uczestnikErasmus) {
        this.uczestnikErasmus = uczestnikErasmus;
    }

    public boolean isPierwszyStopien() {
        return pierwszyStopien;
    }

    public void setPierwszyStopien(boolean pierwszyStopien) {
        this.pierwszyStopien = pierwszyStopien;
    }

    public boolean isDrugiStopien() {
        return drugiStopien;
    }

    public void setDrugiStopien(boolean drugiStopien) {
        this.drugiStopien = drugiStopien;
    }

    public boolean isStudiujeStacjonarnie() {
        return studiujeStacjonarnie;
    }

    public void setStudiujeStacjonarnie(boolean studiujeStacjonarnie) {
        this.studiujeStacjonarnie = studiujeStacjonarnie;
    }

    public String wypiszKursy() {
        if (!this.kursy.isEmpty()) {
            String s = this.kursy.get(0);
            for (int i = 1; i<this.kursy.size(); i++) {
                s += ", " + this.kursy.get(i);
            }
            return s;
        }
        return null;
    }
    public ArrayList<String> podajDane() {

        ArrayList<String> daneSuper = super.podajDane();

        ArrayList<String> dane = new ArrayList<>(Arrays.asList(

                "Numer indeksu: " + numerIndeksu,
                "Rok studiów: " + rokStudiow,
                "Kursy: " + wypiszKursy(),
                "Uczestnik ERASMUS: " + uczestnikErasmus,
                "Licencjat: " + pierwszyStopien,
                "Magister: " +  !pierwszyStopien,
                "Studia stacjonarne: " + studiujeStacjonarnie

        ));

        daneSuper.addAll(dane);

        return daneSuper;
    }

    @Override
    public void aktualizujKursy(String kurs) {
        this.kursy.remove(kurs);
    }
}
