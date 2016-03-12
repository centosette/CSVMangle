package filemanaging;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;


/**
 * Aggiungi qui una descrizione dell'interfaccia ICSVFile
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */

public interface ICSVFile
{
    /**
     * Un esempio di header di metodo - aggiungi i tuoi commenti
     * 
     * @param  y    un parametro d'esempio per il metodo
     * @return    il risultato prodotto dal metodo
     */
    
/*    public void openRead() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException;
    public void openWrite() throws IOException;
    public void openAppend() throws IOException;*/
    public void close() throws IOException;
    public void delete() throws IOException;
    public void rewind() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException;
    public String getLine() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException;
    public CSVLine getCSVLine() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException;
    public CSVLinePool getPool(int maxSize) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException;
    public ArrayList<String> readSample(int maxSize) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException;
    public void writeLine(String line) throws IOException, IllegalWritingMethodException;
    public void writePool(CSVLinePool pool) throws IOException, IllegalWritingMethodException;
    public void appendLine(String line) throws IOException, IllegalWritingMethodException;
    public void appendPool(CSVLinePool pool) throws IOException, IllegalWritingMethodException;
    public boolean isFileOpen();
    public boolean isForReading();
    public String getFilename();
    public String getSeparator();
    public Path getPath();
    public void setHasHeaders (boolean val);
    public boolean hasHeaders ();
    public CSVHeadLine getHeaders();
    public void setHeaders(CSVHeadLine headers);
    public long getGuessedLines();
    public long getSize();

        
}
