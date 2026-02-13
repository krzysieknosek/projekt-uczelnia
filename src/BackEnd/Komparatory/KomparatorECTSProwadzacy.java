package BackEnd.Komparatory;

import Klasy.Kurs;

import java.util.Comparator;

public class KomparatorECTSProwadzacy implements Comparator<Kurs> {
    public int compare(Kurs kurs1, Kurs kurs2) {
        // Porównanie imion
        int ECTSComparison = Integer.compare(kurs1.getPunktyECTS(), kurs2.getPunktyECTS());

        // Jeśli nazwiska są różne, zwróć wynik porównania nazwisk
        if (ECTSComparison != 0) {
            return ECTSComparison;
        }

        // Jeśli punkty ECTS są równe, porównaj nazwisko
        return kurs1.getProwadzacy().compareTo(kurs2.getProwadzacy());
    }
}
