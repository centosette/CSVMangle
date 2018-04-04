package filemanaging;

import algorithms.CSVLineSplitter;
import algorithms.TextDelimitersMustBeEvenException;
import config.Config;
import java.util.ArrayList;
import rules.Rules;


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
    private String delimiter = Config.DEFAULT_TEXT_DELIMITER;
    private int fieldCount = 0;
    private String text;
    private String headers = "";
    private CSVHeadLine headLine = null;
    
    /**
     * Costruttore degli oggetti di classe  CSVLine
     * @param text
     * @param separator
     * @param delimiter
     * @param headLine
     * @throws filemanaging.IllegalFieldNumberInLineException
     * @throws algorithms.TextDelimitersMustBeEvenException
     */
    public CSVLine(String text, String separator, String delimiter, CSVHeadLine headLine) throws IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException
    {
        // inizializza le variabili d'istanza
        this.separator = separator;
        this.delimiter = delimiter;
        this.text = text;
        this.fieldCount = headLine.size();
        this.headLine = headLine;
        this.headers = headLine.toString();
        
        int textFieldCount = (CSVLineSplitter.split(text, separator, delimiter, Rules.TextDelimitersSplit.LEAVE_DELIMITERS)).length;
        if (this.fieldCount != textFieldCount)
        {
            throw (new IllegalFieldNumberInLineException("FIELDCOUNT: " + textFieldCount + " VS " + fieldCount + " - " + "TEXT: " + text));
        }
        int count = 0;
        for(String s : CSVLineSplitter.split(text, separator, delimiter, Rules.TextDelimitersSplit.LEAVE_DELIMITERS))
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

    @Override
    public String getDelimiter() {
        return this.delimiter;
    }
   
}
