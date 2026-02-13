package FrontEnd.KomponentyInterfejsu;

import javax.swing.*;
import java.awt.*;

public class KonstruktorSwobodny {
    public KonstruktorSwobodny(JComponent komponent, int rozmiar, Object... cechy) {
        String nazwaCzcionki = "Serif";
        int typCzcionki = Font.PLAIN, rozmiarCzcionki = rozmiar;

        // Dodatkowe cechy
        for (Object cecha: cechy) {

            if (cecha instanceof Color) {

                Color kolor = (Color) cecha;
                komponent.setForeground(kolor);

            } else if (cecha instanceof String) {

                switch ((String) cecha) {

                    case "B":
                        typCzcionki = Font.BOLD;
                        break;

                    case "Serif":
                    case "SansSerif":
                    case "Dialog":
                    case "DialogInput":
                    case "Monospace":
                        nazwaCzcionki = (String) cecha;
                        break;
                }

            } else if (cecha instanceof int[] && ((int[]) cecha).length == 4) {
                int[] margines = (int[]) cecha;
                komponent.setBorder(BorderFactory.createEmptyBorder(
                        margines[0], margines[1], margines[2], margines[3]
                ));
            }
        }

        // Czcionka
        komponent.setFont(new Font(nazwaCzcionki, typCzcionki, rozmiarCzcionki));
    }
}
