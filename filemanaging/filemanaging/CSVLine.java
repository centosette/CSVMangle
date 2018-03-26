package filemanaging;

import config.Config;
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
        this.headLine = headLine;
        this.headers = headLine.toString();
        
        if (this.fieldCount != text.split(this.separator).length)
        {
            throw (new IllegalFieldNumberInLineException("FIELDCOUNT: " + text.split(this.separator).length + " VS " + fieldCount + " - " + "TEXT: " + text));
        }
        int count = 0;
        for(String s : text.split(this.separator))
        {
            this.add(new CSVField((CSVHeader)headLine.get(count), s, count));
            count++;
        }
        
    }
   
    /**
     * Get a String representation.
     * @return The string from the file that originated the CSV Line
     */
    public String toString()
    {
        return this.text;
    }
    
    public String getSeparator()
    {
        return this.separator;
    }

    @Override
    public int getFieldCount() {
        return this.fieldCount;
    }

    @Override
    public String getHeaders() {
        return this.headers;
    }

    @Override
    public int getStringSize() {
        return this.text.length();
    }

    @Override
    public String getValue(int index) {
        return super.get(index).getValue();
    }

    @Override
    public String getName(int index) {
        return headLine.getName(index);
    }
   
}
