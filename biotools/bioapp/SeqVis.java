package biotools.bioapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import biotools.bioseq.*;


public class SeqVis extends JFrame implements ActionListener {
    JFileChooser fileChooser;
    JButton blader, analyse;
    JTextArea path,sequencetext;
    JPanel panel;
    BufferedReader reader;

    public static void main(String[] args) {
        SeqVis frame = new SeqVis();
        frame.setSize(400, 425);
        frame.createGUI();
        frame.setVisible(true);
    }

    /**
     * this method creates a basic gui with two text areas, 2 buttons and a graphics panel
     */
    private void createGUI() {
        //default
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());
        //textArea
        sequencetext = new JTextArea();
        sequencetext.setPreferredSize(new Dimension(275, 300));
        sequencetext.setLineWrap(true);
        path = new JTextArea();
        path.setPreferredSize(new Dimension(275, 20));
        //button
        blader = new JButton("Blader");
        blader.addActionListener(this);
        analyse = new JButton("Analyse");
        analyse.addActionListener(this);
        //panel
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(380, 40));
        //adds
        window.add(path);
        window.add(blader);
        window.add(sequencetext);
        window.add(analyse);
        window.add(panel);
    }

    /**
     * This method loops through the lines of a .txt file and adds them to an empty string
     * @return returns all the lines of a .txt file in a string
     */
    public String readFile() {
        // empty string
        String sequence = "";
        try {
            // create new reader
            reader = new BufferedReader(new FileReader(path.getText()));
            String line;
            // loop over file until no more lines
            while ((line = reader.readLine()) != null) {
                // adds line to empty string
                sequence += line;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "File was not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sequence;
    }

    /**
     * This method checks what type of sequence is provided as parameter
     * and creates the correct object
     * @param sequence sequence string
     */
    public void analyse(String sequence) throws NoValidSeq {
        // checks if sequence has other characters besides ACTG
        if (!sequence.matches(".*[^ATCG].*")){
            DNA dna_sequence = new DNA(sequence);
            drawDNA(dna_sequence);
        }
        // checks if sequence has other characters besides AUTG
        else if (!sequence.matches(".*[^AUCG].*")){
            RNA rna_sequence = new RNA(sequence);
            drawRNA(rna_sequence);
        }
        // checks if sequence has other characters besides GALMFWKQESPVICYHRNDT
        else if (!sequence.matches(".*[^GALMFWKQESPVICYHRNDT].*")){
            Peptide protein_sequence = new Peptide(sequence);
            drawPro(protein_sequence);
        }
        else {
            throw new NoValidSeq();
        }
    }

    /**
     * This method draws a bar with different colours for polar and apolar amino acids
     * @param protein_sequence Peptide object
     */
    private void drawPro(Peptide protein_sequence) {
        // position of new bar part
        int pos = 0;
        // width of bar part (dynamic with sequence length)
        int width = 380 / protein_sequence.getLength();
        Graphics paper = panel.getGraphics();
        // draws bar parts
        for(int i = 0; i < protein_sequence.getLength(); i++){
            paper.setColor(Peptide.getColor(protein_sequence.getSeq().charAt(i)));
            paper.fillRect(pos, 0, width, 20 );
            pos += width;
        }
    }

    /**
     * This method draws a bar with different colours for 'A', 'U' and 'C', 'G'
     * @param rna_sequence RNA object
     */
    private void drawRNA(RNA rna_sequence) {
        // position of new bar part
        int pos = 0;
        // width of bar part (dynamic with sequence length)
        int width = 380 / rna_sequence.getLength();
        Graphics paper = panel.getGraphics();
        // draws bar parts
        for(int i = 0; i < rna_sequence.getLength(); i++) {
            paper.setColor(RNA.getColor(rna_sequence.getSeq().charAt(i)));
            paper.fillRect(pos, 0, width, 20);
            pos += width;
        }
    }

    /**
     * This method draws a bar with different colours for 'A', 'T' and 'C', 'G'
     * @param dna_sequence DNA object
     */
    private void drawDNA(DNA dna_sequence) {
        // position of new bar part
        int pos = 0;
        // width of bar part (dynamic with sequence length)
        int width = 380 / dna_sequence.getLength();
        Graphics paper = panel.getGraphics();
        // draws bar parts
        for(int i = 0; i < dna_sequence.getLength(); i++) {
            paper.setColor(DNA.getColor(dna_sequence.getSeq().charAt(i)));
            paper.fillRect(pos, 0, width, 20);
            pos += width;
        }
    }

    /**
     * Reads a .txt file when "blader" button is pressed
     * analyses a sequence when "analyse" button is pressed
     * @param e button clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        File selectedFile;
        int reply;
        // blader
        if (e.getSource() == blader) {
            fileChooser = new JFileChooser();
            reply = fileChooser.showOpenDialog(this);
            if (reply == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                path.setText(selectedFile.getAbsolutePath());
                sequencetext.setText(readFile());
            }

        }
        // analyse
        if (e.getSource() == analyse) {
            try {
                analyse(sequencetext.getText());
            } catch (NoValidSeq noValidSeq) {
                noValidSeq.printStackTrace();
            }
        }
    }

class NoValidSeq extends Exception {

    public NoValidSeq(){
        JOptionPane.showConfirmDialog(null, "Non valid sequence provided");
        System.exit(0);
    }

}
}
