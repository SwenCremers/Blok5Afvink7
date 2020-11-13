package biotools.bioseq;

import java.awt.*;

public class DNA extends Sequentie{

    public DNA(String sequentie){
        super(sequentie);
    }

    public int getGCperc(String sequence){ return sequence.replaceAll("[AT]", "").length()
            / sequence.length() * 100; }

    public static Color getColor(char x){
        switch(x) {
            case 'A', 'T':
                return Color.YELLOW;
            default:
                return Color.RED;
        }
    }

}
