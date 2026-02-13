package FrontEnd.KomponentyInterfejsu;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(String tytul, int rozmiarX, int rozmiarY) {

        // Przypisywanie cech
        super( tytul );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(rozmiarX, rozmiarY);
        setLocationRelativeTo(null);

        // Rozmieszczenie
        setLayout(new BorderLayout());

        // Nierozszerzalne
        setResizable(false);

        // Ustawianie widoczności
        setVisible(true);
    }

}