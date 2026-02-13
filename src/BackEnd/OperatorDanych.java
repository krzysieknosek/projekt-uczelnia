package BackEnd;
import Klasy.*;
import BackEnd.Komparatory.KomparatorECTSProwadzacy;
import BackEnd.Komparatory.KomparatorNazwisko;
import BackEnd.Komparatory.KomparatorNazwiskoImie;
import BackEnd.Komparatory.KomparatorNazwiskoWiek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/*
 Klasa OperatorDanych to główna klasa "back-endu" - zawiera bazy danych "osoby" i "kursy" oraz wszystkie
          metody związane z modyfikacją tych baz.

  Klasa zawiera również tablicę mapaPol - rozkład wszystkich składowych i ich typów danych wszystkich klas

 Metody główne:
    dodajObiekt, usunObiekt, wyszukajObiekt, sortujObiekt, wyeliminujPowtorzenia

 Metody wtórne:
    znajdzStudentaPoIndeksie, znajdzKursPoNazwie

 */

public class OperatorDanych {

    //---------------------------------------- BAZY DANYCH I MAPA PÓL -----------------------------------------------\\
    public static ArrayList<Osoba> osoby = new ArrayList<>();                 // ArrayListy przechowujace dane
    public static ArrayList<Kurs> kursy = new ArrayList<>();

    public static String[][][] mapaPol = new String[][][] {        // [X][0][0] - Nazwa klasy; [X][Y][0] - Nazwa pola

            {
                {"PracownikAdministracyjny"},               // [0][0][0]
                    {"imie", "java.lang.String"},           // [0][1][0] i [0][1][1]
                    {"nazwisko", "java.lang.String"},       // [0][2][0] i [0][2][1]
                    {"PESEL", "java.lang.String"},          // itd...
                    {"wiek", "java.lang.Integer"},
                    {"plec", "java.lang.String",
                            "Mężczyzna, Kobieta"},
                    {"stanowiskoPracy", "java.lang.String",
                            "Referent, Specjalista, Starszy Specjalista"},
                    {"stazPracy", "java.lang.Integer"},
                    {"pensja", "java.lang.Integer"},
                    {"liczbaNadgodzin", "java.lang.Integer"}
            },

            {
                {"PracownikBadawczoDydaktyczny"},
                    {"imie", "java.lang.String"},
                    {"nazwisko", "java.lang.String"},
                    {"PESEL", "java.lang.String"},
                    {"wiek", "java.lang.Integer"},
                    {"plec", "java.lang.String",
                            "Mężczyzna, Kobieta"},
                    {"stanowiskoPracy", "java.lang.String",
                            "Asystent, Adiunkt, Profesor Nadzwyczajny, Profesor Zwyczajny, Wykładowca"},
                    {"stazPracy", "java.lang.Integer"},
                    {"pensja", "java.lang.Integer"},
                    {"liczbaPublikacji", "java.lang.Integer"}
            },

            {
                {"Student"},
                    {"imie", "java.lang.String"},
                    {"nazwisko", "java.lang.String"},
                    {"PESEL", "java.lang.String"},
                    {"wiek", "java.lang.Integer"},
                    {"plec", "java.lang.String",
                            "Mężczyzna, Kobieta"},
                    {"numerIndeksu", "java.lang.Integer"},
                    {"rokStudiow", "java.lang.Integer"},
                    {"kursy", "ArrayList<Kurs>"},
                    {"uczestnikErasmus", "java.lang.Boolean"},
                    {"pierwszyStopien", "java.lang.Boolean"},
                    {"drugiStopien", "java.lang.Boolean"},
                    {"studiujeStacjonarnie", "java.lang.Boolean"}
            },

            {
                {"Kurs"},
                    {"nazwaKursu", "java.lang.String"},
                    {"prowadzacy", "java.lang.String"},
                    {"punktyECTS", "java.lang.Integer"}
            }
    };
    // Pierwszy indeks - klasa; Drugi indeks - pole [0 - nazwa klasy], trzeci indeks - nazwa pola/typ/kryteria

    //-------------------------------------------- METODY GŁÓWNE ---------------------------------------------------\\

    public static Object[] sprawdzPoprawnoscDanych(Object... dane) {

        boolean klasaIstnieje = false;
        for (String[][] klasa: mapaPol) {
            String nazwaKlasy = klasa[0][0], zleconaKlasa = (String) dane[0];

            // Warunek 1 : klasa znajduje się w mapie pól i ilość danych jest równa ilości pól klasy
            if (nazwaKlasy.equals(zleconaKlasa) && (klasa.length == dane.length)) {
                klasaIstnieje = true;


                for (int i=1; i < klasa.length; i++) {
                    String oczekiwanyTypDanej = klasa[i][1], typDanej = dane[i].getClass().getName();

                    // Jeśli oczekiwany typ danej to Int, spróbuj dokonać konwersji
                    if (oczekiwanyTypDanej.equals("java.lang.Integer") && typDanej.equals("java.lang.String")) {
                        try {
                            assert dane[i] instanceof String;
                            dane[i] = Integer.parseInt((String) dane[i]);

                        } catch(NumberFormatException | AssertionError e) {
                            return new Object[] {("\nNiepoprawny format danej " + klasa[i][0] + " - oczekiwana liczba")};
                        }

                    // Jeśli oczekiwany typ danej to Boolean, spróbuj dokonać konwersji
                    } else if (oczekiwanyTypDanej.equals("java.lang.Boolean") && typDanej.equals("java.lang.String")) {

                        ArrayList<String> falsze = new ArrayList<>(
                                Arrays.asList( "nie",   "Nie", "fałsz",   "Fałsz",   "0",   "false",   "False"  ));
                        ArrayList<String> prawdy = new ArrayList<>(
                                Arrays.asList( "tak",   "Tak", "prawda",  "Prawda",  "1",   "true",    "True"   ));

                        assert dane[i] instanceof String;
                        if (falsze.contains((String) dane[i])) { dane[i] = false; }
                        else if (prawdy.contains((String) dane[i])) { dane[i] = true; }
                        else {
                            return new Object[] {"\nNiepoprwna wartość zmiennej boolowskiej " + klasa[i][0]};
                        }

                    // Jeśli oczekiwany typ danej to Klasy.Kurs[], wyszukaj kursy po nazwie
                    } else if (oczekiwanyTypDanej.equals("ArrayList<Kurs>") && typDanej.equals("java.lang.String")) {
                        String[] listaKursowString = dane[i].toString().split(", ");
                        ArrayList<String> kursy = new ArrayList<>();
                        for (String s : listaKursowString) {
                            Kurs kurs = znajdzKursPoNazwie(s);
                            if (kurs != null) {
                                kursy.add(kurs.getNazwaKursu());
                            } else {
                                return new Object[] {"\nKurs " + s + " nie istnieje. " +
                                        "Zostanie on pominięty przy tworzeniu instancji obiektu"};
                            }
                        }

                        dane[i] = kursy;
                    }

                    // Warunek 2 : każda dana ma poprawny typ zmiennej
                    else if (!oczekiwanyTypDanej.equals(typDanej)) {
                    return new Object[] {"\nNiepoprawny typ zmiennej : " + klasa[i][0] + " - " + dane[i]};


                }
                // Warunek 3 (opcj.) : Jeżeli pole ma ścisłą grupę dopuszczalnych wartości, dana pasuje
                    if (klasa[i].length > 2) {
                        ArrayList<String> wartosci = new ArrayList<>(Arrays.asList(klasa[i][2].split(", ")));

                        assert dane[i] instanceof String;
                        if (!wartosci.contains((String) dane[i])) {
                            return new Object[] {"\nWartość danej " + klasa[i][0] + " nie jest dopuszczona." };
                        }
                    }
                }
            }
        }
        if (!klasaIstnieje) {
            return new Object[] {"\nKlasa " + dane[0] + " nie istnieje lub podano niepoprawną ilość danych"};

        } else { return dane; }
    }

    public static void dodajObiekt(Object... dane) {

        if (dane == null) {
            return;
        }
        if (dane.length == 0) {
            System.out.println("\nOperacja dodania do bazy danych zakończona niepowodzeniem - przekazana tablica" +
                    "danych jest pusta");
            return;
        }
        switch ((String) dane[0]) {         // dane[0] przechowuje nazwę klasy, którą chcemy dodać do ArrayListy

            case "Kurs" :
                Kurs kurs = new Kurs(dane);
                kursy.add(kurs);
                break;

            case "Student" :
                Student student = new Student(dane);
                for (String nazwaKursuStudenta: student.getKursy()) {
                    Kurs kursStudenta = znajdzKursPoNazwie(nazwaKursuStudenta);
                    if (kursStudenta != null) {
                        kursStudenta.dodajStudenta(student);
                    }
                }
                osoby.add(student);
                break;

            case "PracownikAdministracyjny" :
                ArrayList<String> stanowiskaAdministracyjne = new ArrayList<>(Arrays.asList
                        ("Referent", "Specjalista", "Starszy Specjalista"));
                if (stanowiskaAdministracyjne.contains((String) dane[6])) {
                    PracownikAdministracyjny pracownikAdministracyjny = new PracownikAdministracyjny(dane);
                    osoby.add(pracownikAdministracyjny);

                } else {
                    System.out.println("\nNiepoprawne stanowisko pracy: " + dane[6] + ". Obiekt nie zostanie dodany");
                }
                break;

            case "PracownikBadawczoDydaktyczny" :
                ArrayList<String> stanowiskaBadDyd = new ArrayList<>(Arrays.asList
                        ("Asystent", "Adiunkt", "Profesor Nadzwyczajny", "Profesor Zwyczajny", "Wykładowca"));
                if (stanowiskaBadDyd.contains((String) dane[6])) {
                    PracownikBadawczoDydaktyczny pracownikBadawczoDydaktyczny = new PracownikBadawczoDydaktyczny(dane);
                    osoby.add(pracownikBadawczoDydaktyczny);
                } else {
                    System.out.println("\nNiepoprawne stanowisko pracy: " + dane[6] + ". Obiekt nie zostanie dodany");
                }
                break;

            default:
                System.out.println("\nWprowadzono niepoprawny typ danej (Pracownik/Student/Kurs): " + dane[0] + ". " +
                        "Obiekt nie zostanie dodany");
                break;
        }
    }
    public static void usunObiekt(ArrayList<Object> obiekty) {
        int iloscObiektow = obiekty.size();
        for (Object obiekt: obiekty) {
            if (osoby.contains(obiekt)) {
                if (obiekt instanceof Student) {
                    for (String nazwaKursuStudenta: ((Student) obiekt).getKursy()) {
                        Kurs kursStudenta = znajdzKursPoNazwie(nazwaKursuStudenta);
                        if (kursStudenta != null) {
                            kursStudenta.usunStudenta((Student) obiekt);
                        }
                    }
                }
                osoby.remove(obiekt);

            } else if (kursy.contains(obiekt) && obiekt instanceof Kurs) {
                ArrayList<Integer> indeksyZapisanychStudentow = ((Kurs) obiekt).getZapisaniStudenci();
                for (Integer indeksStudenta: indeksyZapisanychStudentow) {
                    Student student = znajdzStudentaPoIndeksie(indeksStudenta);
                    if (student != null) {
                        student.aktualizujKursy(((Kurs) obiekt).getNazwaKursu());
                    }
                    else {
                        System.out.println("Student o indeksie " + indeksStudenta + "nie istnieje - nieaktualne" +
                                "dane w bazie danych?");
                    }

                }
                if (!indeksyZapisanychStudentow.isEmpty()) {
                    // System.out.println("\n" + indeksyZapisanychStudentow.size() + " studentów było zapisanych na kurs "
                    //        + ((Kurs) obiekt).getNazwaKursu() + ". Zostali oni z niego wypisani");
                }
                kursy.remove(obiekt);

            } else {
                System.out.println("\nObiekt " + obiekt + " nie istnieje w żadnej z list");
                iloscObiektow -= 1;
            }
        }
        System.out.println("\nUsuwanie zakończone. Liczba usuniętych elementów: " + iloscObiektow);
    }

    public static ArrayList<Object> wyszukajObiekt(String klasa, String kategoriaSzukania, String klucz) {

        // Poszukiwanie kategorii na mapie pól
        int Y = -1;
        Object kluczSzukania = klucz;
        for (String[][] strings : mapaPol) {
            for (int y = 1; y < strings.length; y++) {
                if (strings[y][0].equals(kategoriaSzukania)) {                 // [X][1][Y] - Nazwa pola
                    Y = y;
                    if (strings[y][1].equals("java.lang.Integer")) {
                        try {
                            kluczSzukania = Integer.parseInt(klucz);
                        } catch (NumberFormatException e) {
                            System.out.println("\nWpisano niepoprawny klucz wyszukiwania. Oczekiwana liczba");
                            return null;
                        }
                    }

                    else if (strings[y][1].equals("java.lang.Boolean")) {
                        ArrayList<String> falsze = new ArrayList<>(
                                Arrays.asList( "nie",   "Nie", "fałsz",   "Fałsz",   "0",   "false",   "False"  ));
                        ArrayList<String> prawdy = new ArrayList<>(
                                Arrays.asList( "tak",   "Tak", "prawda",  "Prawda",  "1",   "true",    "True"   ));

                        assert kluczSzukania instanceof String;
                        if (falsze.contains((String) kluczSzukania)) { kluczSzukania = false; }
                        else if (prawdy.contains((String) kluczSzukania)) { kluczSzukania = true; }
                        else {
                            System.out.println("\nNiepoprwna wartość zmiennej boolowskiej " + klucz);
                            return null;
                        }
                    }
                    break;
                }
            }
        }


        ArrayList<Object> pasujaceObiekty = new ArrayList<>();
        if (Y != -1) {
            switch (klasa) {
                case "Pracownik":
                case "Student":
                case "Osoba":
                    for (Osoba osoba : osoby) {
                        Object[] mapaWartosci = osoba.getMapaWartosci();
                        String typOsoby = (String) mapaWartosci[0];
                        boolean jestPracownikiem =
                                (typOsoby.substring(0, Math.min(9, typOsoby.length()))).equals("Pracownik");
                        if ((typOsoby.equals("Student") || jestPracownikiem) && mapaWartosci.length > Y) {
                            if (mapaWartosci[Y].equals(kluczSzukania)) {
                                pasujaceObiekty.add(osoba);
                            }
                        }
                    }
                    break;

                case "Kurs":
                    for (Kurs kurs : kursy) {
                        Object[] mapaWartosci = kurs.getMapaWartosci();
                        if (mapaWartosci[Y].equals(kluczSzukania)) {
                            pasujaceObiekty.add(kurs);
                        }
                    }
                    break;

                default:
                    System.out.println("\nWprowadzono niepoprawny typ danej (Pracownik/Student/Kurs): " + klasa);
                    return null;
            }
        }   else {
            System.out.println("\nKategoria, według której chcesz wyszukiwać, nie istnieje: " + kategoriaSzukania);
            return null;
        }
        return pasujaceObiekty;
    }

    public static void sortujDane(String tryb) {

        System.out.println(tryb);
        ArrayList<?> sortowanaKlasa = osoby;
        Comparator<?> komparator = null;

        switch (tryb) {
            case "1":
                komparator = new KomparatorNazwisko();
                break;
            case "2":
                komparator = new KomparatorNazwiskoImie();
                break;
            case "3":
                komparator = new KomparatorNazwiskoWiek();
                break;
            case "4":
                sortowanaKlasa = kursy;
                komparator = new KomparatorECTSProwadzacy();
                break;
            default:
                System.out.println("\nPodano niepoprawną cyfrę. Sortowanie nie zaszło");
        }

        if (sortowanaKlasa != null && komparator != null) {
            ((ArrayList<Object>) sortowanaKlasa).sort((Comparator<Object>) komparator);
        }

    }

    public static String wyeliminujPowtorzenia() {

        ArrayList<Osoba> osobyNowe = new ArrayList<>();
        HashSet<String> indeksy = new HashSet<>();
        int usunieteElementy = 0;

        for (Osoba osoba: OperatorDanych.osoby) {

            boolean pierwszy = false;
            if (osoba instanceof PracownikUczelni) {

                pierwszy = indeksy.add(osoba.getPESEL());

            } else if (osoba instanceof Student) {

                String numerIndeksu =  Integer.toString ( ((Student) osoba).getNumerIndeksu() );
                pierwszy = indeksy.add(numerIndeksu);

            }

            if (pierwszy) {
                osobyNowe.add(osoba);
            } else {
                usunieteElementy++;
            }
        }

        OperatorDanych.osoby = osobyNowe;
        return "Wyeliminowane duplikaty: " + usunieteElementy;
    }

    //-------------------------------------------- METODY WTÓRNE ---------------------------------------------------\\

    static Student znajdzStudentaPoIndeksie(Integer docelowyIndeks) {
        for (Osoba osoba: osoby) {
            if (osoba instanceof Student) {
                Integer indeksStudenta = ((Student) osoba).getNumerIndeksu();
                if (indeksStudenta.equals(docelowyIndeks)) {
                    return (Student) osoba;
                }

            }
        }
        return null;
    }
    static Kurs znajdzKursPoNazwie(String docelowaNazwaKursu) {
        for (Kurs kurs: kursy) {
            String nazwaKursu = kurs.getNazwaKursu();
            if (nazwaKursu.equals(docelowaNazwaKursu)) {
                return kurs;
            }
        }
        return null;
    }
}
