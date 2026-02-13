package FrontEnd;

import BackEnd.OperatorDanych;

import javax.swing.*;

/*

 Klasa OperatorInterfejsu zajmuje się pobieraniem dodatkowych danych od użytkownika i
        wysyłaniem ich do OperatoraDanych w celu przeprowadzenia oczekiwanych przez użytkownika  operacji

 Metody główne:
    wyszukiwanie, dodawanie, usuwanie, sortowanie

 */

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class OperatorInterfejsu {
    static JFrame frame;

    //-------------------------------------------- METODY GŁÓWNE ---------------------------------------------------\\

    static void dodajObiekt() {

        String[][][] mapaPol = OperatorDanych.mapaPol;

        // Tworzymy tablicę obiektów, która będzie kontrolowała, czy użytkownik wpisał poprawne dane
        ArrayList<String> listaObiektow = new ArrayList<>(Collections.singletonList(mapaPol[0][0][0]));
        for (int x = 1; x < mapaPol.length; x++) {
            listaObiektow.add(mapaPol[x][0][0]);
        }

        ArrayList<Component> zapytania = new ArrayList<>();
        JPanel panelZapytan = new JPanel();

        String[] tablicaOpcji = listaObiektow.toArray(new String[0]);
        String wybor = stworzZapytanie(tablicaOpcji, "Wybierz klasę, którą chcesz dodać");
        if (wybor == null) { return; }

        String[][] tablicaDanych = mapaPol[listaObiektow.indexOf(wybor)];

        Object[] daneUzytkownika = new Object[tablicaDanych.length];
        daneUzytkownika[0] = wybor;

        panelZapytan.setLayout(new GridLayout(tablicaDanych.length, 1));

        for (int i = 1; i < tablicaDanych.length; i++) {

            Component zapytanie = null;

            if (tablicaDanych[i][1].equals("java.lang.Boolean")) {
                String[] opcje = new String[] {"Tak", "Nie"};

                zapytanie = dodajOknoZapytania(
                        panelZapytan, "JComboBox", tablicaDanych[i][0], opcje
                );

            } else {

                if (tablicaDanych[i].length == 2) {
                    zapytanie = dodajOknoZapytania(panelZapytan, "JTextField", tablicaDanych[i][0]);
                }

                else if (tablicaDanych[i].length == 3) {
                    String[] opcje = tablicaDanych[i][2].split(", ");

                    zapytanie = dodajOknoZapytania(
                            panelZapytan, "JComboBox", tablicaDanych[i][0], opcje
                    );

                }  else {
                    System.out.println("Niepoprawne zapytanie o dane");
                }
            }

            zapytania.add(zapytanie);
        }


        int result = JOptionPane.showConfirmDialog(frame, panelZapytan,
                "Proszę podać dane", JOptionPane.OK_CANCEL_OPTION);


        if (result == JOptionPane.OK_OPTION) {
            for (int i = 1; i < zapytania.size()+1; i++) {

                Component komponent = zapytania.get(i-1);
                String dana = null;
                if ( komponent instanceof JTextField ) {
                    dana = ((JTextField) komponent).getText();
                } else if ( komponent instanceof JComboBox<?>) {
                    dana = ((JComboBox<?>) komponent).getSelectedItem().toString();
                }

                daneUzytkownika[i] = dana;
            }
        }

        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            return;
        }

        for (Object dana: daneUzytkownika) {
            if (dana == null) {
                JOptionPane.showMessageDialog(frame, "Nie uzupełniono wszystkich pól. Proszę spróbować ponownie");
                return;
            }
        }
        Object[] poprawioneDane = OperatorDanych.sprawdzPoprawnoscDanych(daneUzytkownika);

        if (poprawioneDane.length == daneUzytkownika.length) {
            OperatorDanych.dodajObiekt(poprawioneDane);

        } else {
            JOptionPane.showMessageDialog(frame, poprawioneDane[0]);
        }

        if (wybor.equals("Kurs")) {
            RysowanieInterfejsu.stworzBazeDanych("Kurs");
        } else {
            RysowanieInterfejsu.stworzBazeDanych("Osoba");
        }

    }

    static void usuwanie() {
        String klasa = stworzZapytanie(new String[] {"Pracownik", "Student", "Kurs"},
                "Wybierz klasę, którą chcesz usunąć");

        if (klasa != null) {
            ArrayList<String> opcje = OperatorTerminalu.skomponujListeWyboru(klasa);
            String kategoriaSzukania = stworzZapytanie(opcje.toArray(new String[0]),
                    "Wybierz daną, według której chcesz usunać");

            if (kategoriaSzukania != null) {
                String klucz = JOptionPane.showInputDialog("Podaj klucz usuwania");
                if (klucz == null) { return; }
                ArrayList<Object> wyszukaneObiekty = OperatorDanych.wyszukajObiekt(klasa, kategoriaSzukania, klucz);
                if (wyszukaneObiekty == null) {
                    JOptionPane.showMessageDialog(null,
                            "Nie znaleziono elementów spełniających dane kryteria");

                } else {
                    JOptionPane.showMessageDialog(null,
                            "Usunięte elementy: " + wyszukaneObiekty.size());
                    OperatorDanych.usunObiekt(wyszukaneObiekty);
                }


                if (klasa.equals("Kurs")) {
                    RysowanieInterfejsu.stworzBazeDanych("Kurs");
                } else {
                    RysowanieInterfejsu.stworzBazeDanych("Osoba");
                }
            }
        }
    }

    static void wyszukiwanie(String klasa, String kategoriaSzukania, String klucz) {

        if (Objects.equals(klasa, "Wszyscy")) {
            klasa = "Osoba";
        }
        ArrayList<Object> wyszukaneObiekty = OperatorDanych.wyszukajObiekt(klasa, kategoriaSzukania, klucz);
        if (wyszukaneObiekty.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Nie znaleziono elementów spełniających dane kryteria");
        } else {
            RysowanieInterfejsu.stworzBazeDanych("Wyszukane", wyszukaneObiekty);
        }

    }

    static void sortowanie() {
        String[] mozliweTryby = new String[] {"Osoby - Nazwisko", "Osoby - Nazwisko i imię",
                "Osoby - Nazwisko i wiek", "Kursy - punkty ECTS i prowadzący"};
        String trybSortowania = stworzZapytanie(mozliweTryby, "Wybierz tryb sortowania");

        if (trybSortowania != null) {
            OperatorDanych.sortujDane(
                    Integer.toString(java.util.Arrays.asList(mozliweTryby).indexOf(trybSortowania) + 1));

            if (trybSortowania.equals("Kursy - punkty ECTS i prowadzący")) {
                RysowanieInterfejsu.stworzBazeDanych("Kurs");
            } else {
                RysowanieInterfejsu.stworzBazeDanych("Osoba");
            }
        }
    }

    static void usunDuplikaty() {
        String komunikat = OperatorDanych.wyeliminujPowtorzenia();
        JOptionPane.showMessageDialog(null, komunikat);

        RysowanieInterfejsu.stworzBazeDanych("Osoba");
    }

    //-------------------------------------------- METODY WTÓRNE ---------------------------------------------------\\

    static Component dodajOknoZapytania(JPanel panel, String typZapytania, String danaZapytania, String[]... dodatkoweDane) {

        Component zapytanie = null;
        switch (typZapytania) {

            case "JTextField":
                zapytanie = new JTextField(5);
                break;

            case "JComboBox":
                if (dodatkoweDane.length > 0) {
                    zapytanie = new JComboBox<>(dodatkoweDane[0]);
                }
                break;


            default:
                System.out.println("Podano niepoprawny typ zapytania: " + typZapytania);
                return null;

        }

        panel.add(new JLabel(danaZapytania + ": "));
        panel.add(zapytanie);
        panel.add(Box.createHorizontalStrut(15)); // a spacer

        return zapytanie;
    }

    static String stworzZapytanie(String[] tablicaOpcji, String wiadomosc) {

        int dodawanaKlasaInt = JOptionPane.showOptionDialog(
                frame,
                wiadomosc,
                "Wybór klasy",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tablicaOpcji,
                ""
        );
        if (dodawanaKlasaInt != -1) {
            return tablicaOpcji[dodawanaKlasaInt];
        } else {
            return null;
        }

    }
}


