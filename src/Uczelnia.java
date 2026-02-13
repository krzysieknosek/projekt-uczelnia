import BackEnd.OperatorDanych;
import FrontEnd.RysowanieInterfejsu;
import FrontEnd.OperatorTerminalu;
import BackEnd.OperatorSerializacji;

class Uczelnia {
    public static void main(String[] args) {
        OperatorSerializacji.deserializacja();

        boolean interfejsGraficzny = true;

        if (interfejsGraficzny) {

            RysowanieInterfejsu interfejs = new RysowanieInterfejsu();
            interfejs.inicjalizuj();

        } else {

            OperatorTerminalu menu = new OperatorTerminalu();
            menu.inicjalizuj(false);
        }
    }
}

