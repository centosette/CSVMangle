package filemanaging;

import algorithms.TextDelimitersMustBeEvenException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;


/**
 * ICSVFile represents a Comma Separated Values file, which is normally a 
 * plain text file containing tabular data organized in rows and columns.
 * Data belonging to the same row are separated by a special character, 
 * called a "separator". Usually the separator is a comma, but maybe a 
 * semicolon (;) a TAB or other character or sequence of characters 
 * (@, @@, etc.)
 * A CSV file may or may not be provided with headers. Headers are placed
 * in the first row and are not data, but provide instead a description
 * of the content of the columns. In a plain text file they can't be easily
 * distinguished from a data row. So the user must provide the information
 * whether the file has headers or not.
 * 
 * ICSVFile provides basic methods for dealing with CSV files.
 * 
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */

public interface ICSVFile
{
    
    

    public void close() throws IOException;
    public void delete() throws IOException;
    /**
     * Go back to the beginning of the file.
     * @throws IOException
     * @throws IllegalReadingMethodException
     * @throws IllegalFieldNumberInLineException 
     */
    public void rewind() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException;
    /**
     * Read a line from the file and return it in the form of a text string
     * @return the line in string format.
     * @throws IOException
     * @throws IllegalReadingMethodException
     * @throws IllegalFieldNumberInLineException 
     * @throws algorithms.TextDelimitersMustBeEvenException 
     */
    public String getLine() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException;
    /**
     * Read a line from the file and return it in the form of an ICSVLine object
     * @return
     * @throws IOException
     * @throws IllegalReadingMethodException
     * @throws IllegalFieldNumberInLineException 
     */
    public ICSVLine getCSVLine() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException;
    public CSVLinePool getPool(int maxSize) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException;
    public CSVLinePool getPool(int maxLines, long offset, long maxBytes) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException;
    public ArrayList<String> readSample(int maxSize) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException;
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
