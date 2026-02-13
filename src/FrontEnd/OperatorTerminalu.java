package FrontEnd;

import BackEnd.OperatorDanych;
import Klasy.*;
import BackEnd.OperatorSerializacji;

import java.util.*;

public class OperatorTerminalu {

    static String[][][] mapaPol;
    Scanner skaner = new Scanner(System.in);
    boolean interfejsGraficzny;

    public OperatorTerminalu() {
        this.mapaPol = OperatorDanych.mapaPol;
    }

    public static ArrayList<String> skomponujListeWyboru(String klasa) {

        // Komponowanie listy opcji w zależności od wybranej klasy
        ArrayList<String> opcje = new ArrayList<>();
        switch (klasa) {

            case "Pracownik":
                for (int x = 1; x < mapaPol[0].length; x++) {
                    String dana = mapaPol[0][x][0];
                    opcje.add(dana);
                }
                for (int x = 1; x < mapaPol[1].length; x++) {
                    String dana = mapaPol[1][x][0];
                    if (!opcje.contains(dana)) {
                        opcje.add(dana);
                    }
                }
                break;

            case "Student":
                for (int x = 1; x < mapaPol[2].length; x++) {
                    opcje.add(mapaPol[2][x][0]);
                }
                break;

            case "Kurs":
                for (int x = 1; x < mapaPol[3].length; x++) {
                    opcje.add(mapaPol[3][x][0]);
                }
                break;
                
            case "Osoba":
                for (int x = 1; x < mapaPol.length; x++) {
                    if (!mapaPol[x][0][0].equals("Kurs")) {
                        for (int y = 1; y < mapaPol[x].length; y++) {
                            if (!opcje.contains(mapaPol[x][y][0])) {
                                opcje.add(mapaPol[x][y][0]);
                            }

                        }
                    }
                }
        }
        return opcje;
    }

    String zapytajOWybor(ArrayList<String> opcje) {
        int n = 1;
        for (String opcja: opcje) {

            String wiersz = String.format("[%d] %s", n, opcja);
            System.out.println(wiersz);

            n++;
        }

        while (true) {
            String wyborObiektu = skaner.nextLine();

            // Upewniamy się, że wartość którą wpisał użytkownik jest cyfrą z przedziału podanego przez program.
            int indeksWyboru;
            try {

                indeksWyboru = Integer.parseInt(wyborObiektu) - 1;

            } catch (NumberFormatException e) {
                System.out.println("""

                        Podana opcja jest niepoprawna - nie podano liczby
                        Spróbuj jeszcze raz""");
                continue;
            }

            if (0 <= indeksWyboru && indeksWyboru < opcje.size()) {
                return opcje.get(indeksWyboru);
            }
            System.out.println("""

                    Podana opcja jest niepoprawna - niepoprawny indeks
                    Spróbuj jeszcze raz""");
        }
    }

    void dodajObiekt() {
        // Tworzymy tablicę obiektów, która będzie kontrolowała, czy użytkownik wpisał poprawne dane
        ArrayList<String> tablicaObiektow = new ArrayList<>(Collections.singletonList(mapaPol[0][0][0]));
        for (int x = 1; x < mapaPol.length; x++) {
            tablicaObiektow.add(mapaPol[x][0][0]);
        }

        // boolean niepoprawnaWartosc zapewnia pytanie użytkownika o dane, dopóki wpisze poprawne informacje
        boolean niepoprawnaWartosc = true;
        while (niepoprawnaWartosc) {

            System.out.println("\nJakiego rodzaju obiekt chcesz dodać do bazy danych?");
            String wybor = zapytajOWybor(tablicaObiektow);

            // Prosimy użytkownika o podanie wymaganych danych

            if (wybor != null) {
                niepoprawnaWartosc = false;

                String[][] tablicaDanych = mapaPol[tablicaObiektow.indexOf(wybor)];
                ArrayList<String> daneUzytkownika = new ArrayList<>();
                daneUzytkownika.add(wybor);

                System.out.println("\nProszę o podanie następujących danych:");
                for (int i = 1; i < tablicaDanych.length; i++) {

                    System.out.print(tablicaDanych[i][0] + ": ");

                    if (tablicaDanych[i].length == 3) {
                        System.out.println();
                        ArrayList<String> opcje = new ArrayList<>
                                (Arrays.asList(tablicaDanych[i][2].split(", ")));
                        String wybranaOpcja = zapytajOWybor(opcje);
                        daneUzytkownika.add(wybranaOpcja);

                    } else if (tablicaDanych[i][1].equals("java.lang.Boolean")) {
                        System.out.println();
                        ArrayList<String> opcje = new ArrayList<> (Arrays.asList("Tak", "Nie"));
                        String wybranaOpcja = zapytajOWybor(opcje);
                        daneUzytkownika.add(wybranaOpcja);

                    } else {
                        daneUzytkownika.add(skaner.nextLine());
                    }

                }

                Object[] poprawioneDane = OperatorDanych.sprawdzPoprawnoscDanych(daneUzytkownika.toArray());

                if (poprawioneDane.length == 1) {
                    System.out.println(poprawioneDane[0]);
                }
                else {
                    OperatorDanych.dodajObiekt(poprawioneDane);
                    System.out.println("\nOperacja zakończona pomyślnie");

                }

            } else {
                System.out.println("\nPodana opcja jest niepoprawna - podano niepoprawne dane");
            }
        }
    }

    void wyszukajObiekt() {
        System.out.println("\nPodaj klasę, w której chcesz dokonać wyszukiwania");

        ArrayList<String> opcje = new ArrayList<> (Arrays.asList("Pracownik", "Student", "Kurs"));
        String klasa = zapytajOWybor(opcje);

        // Zapytanie o wyszukiwaną danę
        System.out.println("\nWyszukiwanie wg danej: ");

        opcje = skomponujListeWyboru(klasa);

        String kategoriaSzukania = zapytajOWybor(opcje);

        System.out.println("\nWyszukiwana wartość: ");
        String kluczSzukania = skaner.nextLine();

        ArrayList<Object> pasujaceElementy = OperatorDanych.wyszukajObiekt(klasa, kategoriaSzukania, kluczSzukania);
        if (pasujaceElementy != null && !pasujaceElementy.isEmpty()) {
            for (Object wyszukanaKlasa: pasujaceElementy) {
                if (wyszukanaKlasa instanceof Kurs) {
                    ((Kurs) wyszukanaKlasa).podajDane();
                } else {
                    ((Osoba) wyszukanaKlasa).podajDane();
                }
            }
        } else {
            System.out.println("\nŻadna pozycja nie pasuje do twoich kryteriów");
        }
    }

    void wypiszWszystkieDane() {
        System.out.println("\nPodaj klasę, której dane chcesz pozyskać");
        ArrayList<String> opcje = new ArrayList<> (Arrays.asList("Pracownik", "Student", "Kurs", "Osoba"));
        String klasa = zapytajOWybor(opcje);
        System.out.println(klasa);

        switch (klasa) {
            case "Pracownik":
                for (Osoba osoba: OperatorDanych.osoby) {
                    if (osoba instanceof PracownikAdministracyjny || osoba instanceof PracownikBadawczoDydaktyczny) {
                        System.out.println();
                        ArrayList<String> dane = osoba.podajDane();
                        for (String dana: dane) {
                            System.out.println(dana);
                        }
                    }
                }
                break;
            case "Student":
                for (Osoba osoba: OperatorDanych.osoby) {
                    if (osoba instanceof Student) {
                        System.out.println();
                        ArrayList<String> dane = osoba.podajDane();
                        for (String dana: dane) {
                            System.out.println(dana);
                        }
                    }
                }
                break;
            case "Kurs":
                for (Kurs kurs: OperatorDanych.kursy) {
                    System.out.println();
                    ArrayList<String> dane = kurs.podajDane();
                    for (String dana: dane) {
                        System.out.println(dana);
                    }
                }
                break;

            case "Osoba":
                for (Osoba osoba: OperatorDanych.osoby) {
                    System.out.println();
                    ArrayList<String> dane = osoba.podajDane();
                    for (String dana: dane) {
                        System.out.println(dana);
                    }
                }
                break;

            default:
                System.out.println("\nPodano niepoprawną klasę");
        }
    }
    
    void sortujDane() {
        System.out.println("""

                Podając odpowiednią cyfrę, wybierz tryb sortowania:
                [1] Osoby - Nazwisko
                [2] Osoby - Nazwisko i imię
                [3] Osoby - Nazwisko i wiek
                [4] Kursy - punkty ECTS i prowadzący""");
        String tryb = skaner.nextLine();
        OperatorDanych.sortujDane(tryb);
    }

    void usunDane() {
        System.out.println("\nPodaj klasę, której dane chcesz usunąć");
        ArrayList<String> opcje = new ArrayList<> (Arrays.asList("Pracownik", "Student", "Kurs"));
        String klasa = zapytajOWybor(opcje);

        System.out.println("\nUsuń według kategorii danych:");

        opcje = skomponujListeWyboru(klasa);
        String kategoria = zapytajOWybor(opcje);

        System.out.println("\nPoszukiwana wartość:");
        String wartosc = skaner.nextLine();

        ArrayList<Object> pasujaceElementy = OperatorDanych.wyszukajObiekt(klasa, kategoria, wartosc);

        if (pasujaceElementy != null) {
            if (!pasujaceElementy.isEmpty()) {
                OperatorDanych.usunObiekt(pasujaceElementy);
            } else {
                System.out.println("\nNie istnieją elementy spełniające podane kryteria. Nie ma co usunąć");
            }
        }
    }


    String[] operacje = new String[] {
            "Wyjście",                                  // 0
            "Dodaj",                                    // 1
            "Wyszukaj",                                 // 2
            "Wyświetl wszystkie dane",                  // 3
            "Sortuj",                                   // 4
            "Usuń",                                     // 5
            "Wyeliminuj powtórzenia [Osoby]"            // 6
    };

    public void inicjalizuj(boolean interfejsGraficzny) {

        this.interfejsGraficzny = interfejsGraficzny;
        if (interfejsGraficzny) {
            return;
        }


        System.out.println("\nDostępne operacje:");
        for (int i = 0; i < operacje.length; i++) {
            System.out.println("[" + i + "] " + operacje[i]);
        }
        
        String wybor = skaner.nextLine();

        try {
            switch (Integer.parseInt(wybor)) {
                case 0:
                    OperatorSerializacji.serializacja();
                    break;

                case 1:
                    dodajObiekt();
                    break;

                case 2:
                    wyszukajObiekt();
                    break;

                case 3:
                    wypiszWszystkieDane();
                    break;
                    
                case 4:
                    sortujDane();
                    break;

                case 5:
                    usunDane();
                    break;

                case 6:
                    OperatorDanych.wyeliminujPowtorzenia();
                    break;
            }
        } catch(NumberFormatException e){
            System.out.println("\nWpisano niepoprawną wartość liczbową");
            inicjalizuj(false);
        }

        if (Integer.parseInt(wybor) != 0) {
            inicjalizuj(false);
        }
    }
}
