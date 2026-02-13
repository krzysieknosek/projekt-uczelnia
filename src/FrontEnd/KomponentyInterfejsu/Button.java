package FrontEnd.KomponentyInterfejsu;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    String text;
    public Button(String text, int rozmiar) {
        super(text);

        this.text = text;

        setFont(new Font("SansSefir", Font.PLAIN, rozmiar));

        // Usuwanie dziwnej obramówki
        setFocusPainted(false);

    }
}
