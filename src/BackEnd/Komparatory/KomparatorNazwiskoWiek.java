package BackEnd.Komparatory;

import java.util.Comparator;
import Klasy.*;

public class KomparatorNazwiskoWiek implements Comparator<Osoba> {
    public int compare(Osoba osoba1, Osoba osoba2) {
        // Porównanie imion
        int nameComparison = osoba1.getNazwisko().compareTo(osoba2.getNazwisko());

        // Jeśli nazwiska są różne, zwróć wynik porównania nazwisk
        if (nameComparison != 0) {
            return nameComparison;
        }

        // Jeśli nazwiska są równe, porównaj wiek
        return Integer.compare(osoba2.getWiek(), osoba1.getWiek());
    }
}

