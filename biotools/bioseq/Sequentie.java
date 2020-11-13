package biotools.bioseq;

abstract class Sequentie {

    private String sequentie;

    public Sequentie(String sequentie){
        this.sequentie = sequentie;
    }

    public String getSeq(){
        return sequentie;
    }

    public void setSeq(String newSeq){
        this.sequentie = newSeq;
    }

    public int getCol(){
        return 0;
    }

    public int getLength(){
        return this.sequentie.length();
    }
}
