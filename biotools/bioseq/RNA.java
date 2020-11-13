package biotools.bioseq;

import java.awt.*;

public class RNA extends Sequentie {

    public RNA(String sequentie) {
        super(sequentie);
    }

    public static Color getColor(char x) {
        switch (x) {
            case 'A':
                return Color.YELLOW;
            case 'U':
                return Color.BLUE;
            default:
                return Color.RED;
        }
    }
}
