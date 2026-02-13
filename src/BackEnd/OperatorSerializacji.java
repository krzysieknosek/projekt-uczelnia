package BackEnd;
import BackEnd.OperatorDanych;
import Klasy.*;

import java.io.*;
import java.util.ArrayList;

public class OperatorSerializacji {

    public static boolean deserializacja() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("kursy.txt"));
            ObjectInputStream inputStream1 = new ObjectInputStream(new FileInputStream("osoby.txt"));

            // Deserializacja listy osób
            ArrayList<Osoba> osoby = (ArrayList<Osoba>) inputStream1.readObject();
            OperatorDanych.osoby = osoby;

            // Deserializacja listy kursów
            ArrayList<Kurs> kursy = (ArrayList<Kurs>) inputStream.readObject();
            OperatorDanych.kursy = kursy;



        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Baza danych jest pusta - deserializacja nie zaszła");
            return false;
        }

        return true;
    }
    public static boolean serializacja() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("kursy.txt"));
            ObjectOutputStream outputStream1 = new ObjectOutputStream(new FileOutputStream("osoby.txt"));

            outputStream.writeObject(OperatorDanych.kursy);
            outputStream1.writeObject(OperatorDanych.osoby);

        } catch (IOException e) {
            System.err.println("\nPodczas serializacji zaszedł błąd [" + e + "]. Dane nie zostały zapisane");
            return false;
        }
        return true;
    }
}
