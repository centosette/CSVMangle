package filemanaging; 

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
 * Aggiungi qui una descrizione della classe CSVFile
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */
public class CSVFile implements ICSVFile
{
    // variabili d'istanza - sostituisci l'esempio che segue con il tuo
    private String filename;
    private String separator = ";";
    private int recordlength = 0;
    private boolean readLine = true;
    private Path filePath;
    private BufferedReader filein;
    private BufferedWriter fileout;
    private boolean forReading = true;
    private boolean fileIsOpen = false;
    private boolean hasHeaders = true;
    private Charset cs = Charset.defaultCharset();

    /**
     * Costruttore degli oggetti di classe  CSVFile
     */
    public CSVFile(Path filePath
            , String separator
            , int recordlength
            , Charset cs) 
    {
        // inizializza le variabili d'istanza
        this.filePath = filePath;
        this.filename = filePath.toString();
        this.separator = separator;
        if (recordlength > 0) 
        {
            this.recordlength = recordlength;
            this.readLine = false;
        }
        this.cs = cs;
    }
    
    public CSVFile(String filename
            , int recordlength) throws IOException
    {
        this(Paths.get(filename, ""),";",recordlength, Charset.defaultCharset());
    }

    public CSVFile(String filename) throws IOException
    {
        this(Paths.get(filename, ""),";",0, Charset.defaultCharset());
    }
 
    
    public void open(boolean forReading) throws IOException
    {
        if (this.fileIsOpen)
        {
            this.close();
        }
        this.forReading = forReading;
        try
        {
            if (forReading)
            {
                this.filein = Files.newBufferedReader(filePath,cs);
            }
            else
            {
                this.fileout = Files.newBufferedWriter(filePath,cs);
            }
            this.fileIsOpen = true;
        }
        catch (IOException ex)
        {
            this.fileIsOpen = false;
            throw(ex);
        }
    }
    
    public void close() throws IOException
    {
        if (fileIsOpen)
        {
           try{
            this.filein.close();
            this.fileout.close();
           }
           catch (NullPointerException ex)
           {
             //do nothing;  
           }
           fileIsOpen = false;
        }
        
    }
    
    public String getLine() throws IOException, IllegalReadingMethodException
    {
        if (readLine && forReading && fileIsOpen) {
         return filein.readLine();
        }
        throw(new IllegalReadingMethodException());
    }
    
    public String getRecord() throws IOException, IllegalReadingMethodException
    {
        if (!readLine && forReading && fileIsOpen)
        {
            char buffer[] = null;
            int read;
            read = filein.read(buffer, 0, recordlength);
            if (read > 0)
            {
                return (new String(buffer, 0, read));
            }
            return null;
        }
        throw(new IllegalReadingMethodException());
        
    }
    
    public void writeLine(String line) throws IOException, IllegalReadingMethodException
    {
        if (readLine && !forReading && fileIsOpen) {
         fileout.write(line);
         fileout.newLine();
        }
        throw(new IllegalReadingMethodException());
    }
    
    public void writeRecord(char buffer[]) throws IOException, IllegalReadingMethodException
    {
        if (!readLine && !forReading && fileIsOpen)
        {
            fileout.write(buffer);
        }
        throw(new IllegalReadingMethodException());
        
    }
    
    public boolean isFileOpen()
    {
        return this.fileIsOpen;
    }
    
    public boolean isForReading()
    {
        return this.forReading;
    }
    
    public String getFilename()
    {
        return this.filename;
    }
    
    public String getSeparator()
    {
        return this.separator;
    }
    
    public Path getPath()
    {
        return this.filePath;
    }
    
    public void setHasHeaders (boolean val)
    {
        this.hasHeaders = val;
    }
    
    public boolean hasHeaders ()
    {
        return this.hasHeaders;
    }

}
