package BackEnd.Komparatory;

import Klasy.Osoba;

import java.util.Comparator;

public class KomparatorNazwiskoImie implements Comparator<Osoba> {
    public int compare(Osoba osoba1, Osoba osoba2) {
        // Porównanie imion
        int nameComparison = osoba1.getNazwisko().compareTo(osoba2.getNazwisko());

        // Jeśli nazwiska są różne, zwróć wynik porównania nazwisk
        if (nameComparison != 0) {
            return nameComparison;
        }

        // Jeśli nazwiska są równe, porównaj imiona
        return osoba1.getImie().compareTo(osoba2.getImie());
    }
}
