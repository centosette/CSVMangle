package filemanaging;

import java.util.ArrayList;


/**
 * Aggiungi qui una descrizione della classe CSVLine
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */
public class CSVLine extends ArrayList<ICSVField> implements ICSVLine
{
    // variabili d'istanza - sostituisci l'esempio che segue con il tuo
    private String separator = Config.DEFAULT_SEPARATOR;
    private int fieldCount = 0;
    private String text;
    private String headers = "";
    private CSVHeadLine headLine = null;
    
    /**
     * Costruttore degli oggetti di classe  CSVLine
     */
    public CSVLine(String text, String separator, CSVHeadLine headLine) throws IllegalFieldNumberInLineException
    {
        // inizializza le variabili d'istanza
        this.separator = separator;
        this.text = text;
        this.fieldCount = headLine.size();
        
        if (this.fieldCount != text.split(this.separator).length)
        {
            throw (new IllegalFieldNumberInLineException());
        }
        int count = 0;
        for(String s : text.split(this.separator))
        {
            this.add(new CSVField(headLine.get(count), s));
            count++;
        }
        
    }
    public CSVLine(String text)
    {
        // inizializza le variabili d'istanza
     
    }

    
   
}
