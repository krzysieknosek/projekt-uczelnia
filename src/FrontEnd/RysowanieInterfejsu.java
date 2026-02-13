package FrontEnd;

import BackEnd.OperatorDanych;
import Klasy.*;
import FrontEnd.KomponentyInterfejsu.*;
import FrontEnd.KomponentyInterfejsu.Button;
import FrontEnd.KomponentyInterfejsu.Frame;
import FrontEnd.KomponentyInterfejsu.Label;
import FrontEnd.KomponentyInterfejsu.Panel;
import FrontEnd.KomponentyInterfejsu.TextField;
import BackEnd.OperatorSerializacji;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/*

 Klasa RysowanieInterfejsu zajmuje się wyświetlaniem interfejsu graficznego na ekranie użytkownika
        Współdziała z klasą OperatorInterfejsu, która pracuje na danych podawanych w interfejsie przez użytkownika

 Metody główne:
    stworzGornyPanel, stworzPrzyciskMenu, stworzPanelMenu       -- metody odpowiedzialne za stworzenie panelu na
                                                                                    górze interfejsu graficznego

    stworzPanelWyszukiwarki, stworzPanelDanych, stworzBazeDanych

    inicjalizuj                                                 -- metoda tworząca ekran główny interfejsu

 Metody wtórne:
    wyczyscEkran, daneToHtml

 */

public class RysowanieInterfejsu {

    static Frame frame;
    static Panel[] panele = new Panel[4];
    static OperatorTerminalu operatorTerminalu = new OperatorTerminalu();

    //--------------------------------------------- KONSTRUKTOR ----------------------------------------------------\\

    //-------------------------------------------- METODY GŁÓWNE ---------------------------------------------------\\

    static private ArrayList<Panel> stworzGornyPanel() {
        Panel gornyPasek = new Panel(new Color(50, 168, 160));

        Panel lewyGornyPanel = new Panel(new Color(50, 168, 160));
        lewyGornyPanel.setLayout(new GridBagLayout());

        Panel prawyGornyPanel = new Panel(new Color(50, 168, 160));

        gornyPasek.add(lewyGornyPanel, BorderLayout.WEST);
        gornyPasek.add(prawyGornyPanel, BorderLayout.EAST);

        // Kompresowanie panelu do ArrayListy
        return new ArrayList<>(Arrays.asList(
                gornyPasek, lewyGornyPanel, prawyGornyPanel
        ));
    }

    static private JButton stworzPrzyciskMenu(Object tekstLubObraz, int rozmiar) {
        JButton przycisk = null;
        if (tekstLubObraz instanceof String) {

            String tekst = (String) tekstLubObraz;
            przycisk = new Button(tekst, rozmiar);

            przycisk.setForeground(Color.white);

        } else if (tekstLubObraz instanceof BufferedImage) {

            BufferedImage obraz = (BufferedImage) tekstLubObraz;
            przycisk = new JButton(new ImageIcon(obraz.getScaledInstance(rozmiar, rozmiar, Image.SCALE_FAST)));

        }

        assert przycisk != null;
        przycisk.setBackground(new Color(40, 158, 150));
        przycisk.setFocusPainted(false);
        przycisk.setBorderPainted(false);

        return przycisk;
    }

    static private ArrayList<Panel> stworzPanelMenu() {

        ArrayList<Panel> gornyPanel = stworzGornyPanel();
        Panel lewyGornyPanel = gornyPanel.get(1), prawyGornyPanel = gornyPanel.get(2);

        JButton przyciskOsoby = stworzPrzyciskMenu("Osoby", 21);
        przyciskOsoby.addActionListener(e ->
                stworzBazeDanych("Osoba")
        );

        JButton przyciskKursy = stworzPrzyciskMenu("Kursy", 21);
        przyciskKursy.addActionListener(e ->
                stworzBazeDanych("Kurs")
        );

        BufferedImage plus = null, minus = null, drzwi = null, sortowanie = null, zapis = null, duplikat = null;
        try {
            minus = ImageIO.read(new File("src/FrontEnd/Obrazy/minus.png"));
            drzwi = ImageIO.read(new File("src/FrontEnd/Obrazy/drzwi.png"));
            plus = ImageIO.read(new File("src/FrontEnd/Obrazy/plus.png"));
            sortowanie = ImageIO.read(new File("src/FrontEnd/Obrazy/sortowanie.png"));
            zapis = ImageIO.read(new File("src/FrontEnd/Obrazy/zapis.png"));
            duplikat = ImageIO.read(new File("src/FrontEnd/Obrazy/duplikat.png"));

        } catch (IOException e) {
            System.out.println("BŁĘDY");
        }

        // Tworzenie przycisków górnego panelu i przypisywanie im ich funkcji
        JButton przyciskPlus = stworzPrzyciskMenu(plus, 20);
        przyciskPlus.addActionListener(e ->
                OperatorInterfejsu.dodajObiekt()
        );

        JButton przyciskMinus = stworzPrzyciskMenu(minus, 20);
        przyciskMinus.addActionListener(e ->
                OperatorInterfejsu.usuwanie()
        );

        JButton przyciskDrzwi = stworzPrzyciskMenu(drzwi, 20);
        przyciskDrzwi.addActionListener(e ->
                System.exit(0)
        );
        JButton przyciskSortowanie = stworzPrzyciskMenu(sortowanie, 20);
        przyciskSortowanie.addActionListener(e ->
                OperatorInterfejsu.sortowanie()
        );

        JButton przyciskZapis = stworzPrzyciskMenu(zapis, 20);
        przyciskZapis.addActionListener(e ->
                OperatorSerializacji.serializacja()
        );

        JButton przyciskDuplikat = stworzPrzyciskMenu(duplikat, 20);
        przyciskDuplikat.addActionListener(e ->
                OperatorInterfejsu.usunDuplikaty()
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1, 5, 1, 0);

        lewyGornyPanel.add(przyciskOsoby, gbc);
        lewyGornyPanel.add(przyciskKursy, gbc);

        lewyGornyPanel.add(przyciskPlus, gbc);
        lewyGornyPanel.add(przyciskMinus, gbc);
        lewyGornyPanel.add(przyciskSortowanie, gbc);
        lewyGornyPanel.add(przyciskZapis, gbc);
        lewyGornyPanel.add(przyciskDuplikat, gbc);

        prawyGornyPanel.add(przyciskDrzwi);


        return gornyPanel;
    }

    static private JPanel stworzPanelWyszukiwarki(String klasa) {

        TextField wyszukiwarka = new TextField(25);
        Button przyciskWyszukaj = new Button("Wyszukaj", 15);


        ArrayList<String> istniejaceKategorieArrayList = OperatorTerminalu.skomponujListeWyboru(klasa);
        String[] istniejaceKategorie = new String[istniejaceKategorieArrayList.size()];
        istniejaceKategorieArrayList.toArray(istniejaceKategorie);
        ComboBox wyborKategorii = new ComboBox(istniejaceKategorie);

        ComboBox wyborKlasy;

        // Tworzenie paska wyboru klasy. Jeśli zostanie on edytowany, ta zmiana wpływa również na pasek kategorii
        if (klasa.equals("Osoba") || klasa.equals("Pracownik") || klasa.equals("Student")) {
            switch (klasa) {
                case "Osoba":
                    wyborKlasy = new ComboBox(new String[]{"Wszyscy", "Student", "Pracownik"});
                    break;

                case "Pracownik":
                    wyborKlasy = new ComboBox(new String[]{"Pracownik", "Student", "Wszyscy"});
                    break;

                case "Student":
                    wyborKlasy = new ComboBox(new String[]{"Student", "Wszyscy", "Pracownik"});
                    break;

                default:
                    wyborKlasy = null;
            }

            ComboBox finalWyborKlasy = wyborKlasy;
            wyborKlasy.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     String wybranaKlasa = (String) finalWyborKlasy.getSelectedItem();

                     ArrayList<String> kategorie = null;
                     switch (wybranaKlasa) {
                         case "Pracownik":
                             kategorie = OperatorTerminalu.skomponujListeWyboru("Pracownik");
                             stworzBazeDanych("Pracownik");
                             break;
                         case "Student":
                             kategorie = OperatorTerminalu.skomponujListeWyboru("Student");
                             stworzBazeDanych("Student");
                             break;
                         case "Wszyscy":
                             kategorie = OperatorTerminalu.skomponujListeWyboru("Osoba");
                             stworzBazeDanych("Osoba");
                             break;
                     }

                     assert kategorie != null;
                     String[] istniejaceKategorie = new String[kategorie.size()];
                     wyborKategorii.setModel(new DefaultComboBoxModel<>(kategorie.toArray(istniejaceKategorie)));
                 }
             }

            );
        } else {
            wyborKlasy = null;
        }

        if (wyborKlasy != null) {
            przyciskWyszukaj.addActionListener(e ->
                    OperatorInterfejsu.wyszukiwanie(
                            (String) wyborKlasy.getSelectedItem(),
                            (String) wyborKategorii.getSelectedItem(),
                            wyszukiwarka.getText())
            );
        } else {
            przyciskWyszukaj.addActionListener(e ->
                    OperatorInterfejsu.wyszukiwanie(
                            "Kurs",
                            (String) wyborKategorii.getSelectedItem(),
                            wyszukiwarka.getText())
            );
        }


        // Komponowanie panelu
        Panel panelWyszukiwarki = new Panel(Color.white);


        panelWyszukiwarki.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridwidth = 2;
        panelWyszukiwarki.add(wyszukiwarka, gbc);
        gbc.gridwidth = 1;
        panelWyszukiwarki.add(przyciskWyszukaj, gbc);
        gbc.gridy = 1;


        if (!klasa.equals("Kurs")) {
            panelWyszukiwarki.add(wyborKlasy, gbc);
        }
        panelWyszukiwarki.add(wyborKategorii, gbc);

        return panelWyszukiwarki;
    }

    static JPanel stworzPanelDanych(String klasa, ArrayList<Object>... wlasnePozycje) {

        String dane = "<html>";

        switch (klasa) {
            case "Osoba":
                for (Osoba osoba: OperatorDanych.osoby) {
                    dane += daneToHtml(osoba.podajDane()) + "<br/>";
                }
                break;
            case "Kurs":
                for (Kurs kurs: OperatorDanych.kursy) {
                    dane += daneToHtml(kurs.podajDane()) + "<br/>";
                }
                break;

            case "Student":
                for (Osoba osoba: OperatorDanych.osoby) {
                    if (osoba instanceof Student) {
                        dane += daneToHtml(osoba.podajDane()) + "<br/>";
                    }
                }
                break;

            case "Pracownik":
                for (Osoba osoba: OperatorDanych.osoby) {
                    if (osoba instanceof PracownikUczelni) {
                        dane += daneToHtml(osoba.podajDane()) + "<br/>";
                    }
                }
                break;

            case "Wyszukane":
                for (Object pozycja: wlasnePozycje[0]) {
                    if (pozycja instanceof Osoba) {
                        dane += daneToHtml(((Osoba) pozycja).podajDane()) + "<br/>";
                    } else if (pozycja instanceof Kurs) {
                        dane += daneToHtml(((Kurs) pozycja).podajDane()) + "<br/>";
                    }
                }
                break;
            default:
                System.out.println("Podano niepoprawny typ klasy. Dane nie zostaną wypisane");
                return null;
        }
        dane += "</html>";

        Panel danePanel = new Panel(Color.white);
        Label daneLabel = new Label(dane, 17);
        daneLabel.setLayout(new BorderLayout());
        danePanel.add(daneLabel, BorderLayout.CENTER);


        JScrollPane suwak= new JScrollPane(danePanel);
        suwak.setViewportView(daneLabel);
        suwak.getViewport().setBackground(Color.white);
        suwak.getVerticalScrollBar().setUnitIncrement(20);

        suwak.setBorder(null);

        suwak.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        danePanel.add(suwak,BorderLayout.CENTER);

        return danePanel;

    }

    static public void inicjalizuj() {

        wyczyscEkran();

        int dlugoscInterfejsu = 575, wysokoscInterfejsu = 575;
        frame = new Frame("Uczelnia", dlugoscInterfejsu, wysokoscInterfejsu);
        OperatorInterfejsu.frame = frame;

        // GÓRNY PANEL
        ArrayList<Panel> gornyPanel = stworzGornyPanel();
        Panel gornyPasek = gornyPanel.get(0), lewyGornyPanel = gornyPanel.get(1);

        Label gornyPanelTekst = new Label("Uczelnia wita!",23, Color.white, "SansSerif");
        lewyGornyPanel.add(gornyPanelTekst);

        // PRZYCISK NA ŚRODKU EKRANU
        Panel panelMenu = new Panel(Color.white);

        panelMenu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Button uzytkownik = new Button("Przejdź do Uczelni", 20);
        uzytkownik.addActionListener(e ->
                stworzBazeDanych("Osoba")
        );

        panelMenu.add(uzytkownik, gbc);

        // Grupowanie całości do wspólnego panelu
        Panel panelInicjalizacja = new Panel(Color.white);
        panelInicjalizacja.add(panelMenu);
        panelInicjalizacja.add(gornyPasek, BorderLayout.PAGE_START);

        panele[0] = panelInicjalizacja;
        frame.add(panelInicjalizacja);
    }

    static void stworzBazeDanych(String klasa, ArrayList<Object>... wlasnePozycje) {

        wyczyscEkran();

        ArrayList<Panel> gornyPanel = stworzPanelMenu();
        Panel gornyPasek = gornyPanel.get(0);

        Panel glownyPanel = new Panel(Color.white);
        glownyPanel.setLayout(new BorderLayout());

        JPanel panelDanych;
        if (!Objects.equals(klasa, "Wyszukane")) {
            JPanel panelWyszukiwarki = stworzPanelWyszukiwarki(klasa);
            glownyPanel.add(panelWyszukiwarki, BorderLayout.NORTH);

            panelDanych = stworzPanelDanych(klasa);
        } else {
            panelDanych = stworzPanelDanych("Wyszukane", wlasnePozycje[0]);
        }


        // Komponowanie głównego panelu z panelów podrzędnych
        glownyPanel.add(panelDanych,BorderLayout.CENTER);

        // Grupowanie całości do wspólnego panelu (panelBazaDanych)
        Panel panelBazaDanych = new Panel(Color.white);

        panelBazaDanych.add(gornyPasek, BorderLayout.PAGE_START);
        panelBazaDanych.add(glownyPanel, BorderLayout.CENTER);


        switch (klasa) {
            case "Osoba":
            case "Pracownik":
            case "Student":
                panele[1] = panelBazaDanych;
                break;
            case "Kurs":
                panele[2] = panelBazaDanych;
                break;


            case "Wyszukane":
                panele[3] = panelBazaDanych;
                break;

        }
        frame.add(panelBazaDanych);
    }

    //-------------------------------------------- METODY WTÓRNE ---------------------------------------------------\\


    static void wyczyscEkran() {
        for (Panel panel : panele) {
            if (panel != null) {
                panel.setVisible(false);
            }
        }
    }

    static String daneToHtml(ArrayList<String> dane) {
        String HTML = "";
        for (String dana: dane) {
            String[] danaIWartosc = dana.split(": ");
            HTML += "<b>" + danaIWartosc[0] + "</b>: " + danaIWartosc[1] + "<br/>";
        }
        return HTML;
    }

}
