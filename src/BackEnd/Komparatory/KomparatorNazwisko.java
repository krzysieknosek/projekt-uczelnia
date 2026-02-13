package BackEnd.Komparatory;

import Klasy.Osoba;

import java.util.Comparator;

public class KomparatorNazwisko implements Comparator<Osoba> {
    public int compare(Osoba person1, Osoba person2) {
        return person1.getNazwisko().compareTo(person2.getNazwisko());
    }
}
