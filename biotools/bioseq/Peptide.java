package biotools.bioseq;

import java.awt.*;

public class Peptide extends Sequentie {

    public Peptide(String sequentie){
        super(sequentie);
    }

    public static Color getColor(char x) {
        switch (x) {
            case 'G', 'A', 'V', 'L', 'I', 'M', 'W', 'F', 'P':
                return Color.RED;
            case 'S', 'T', 'C', 'Y', 'N', 'Q':
                return Color.BLUE;
            default:
                return Color.GRAY;
        }
    }
}
