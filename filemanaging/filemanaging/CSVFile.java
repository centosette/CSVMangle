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
    private String separator = Config.DEFAULT_SEPARATOR;
    private int recordlength = 0;
    private boolean readLine = true;
    private Path filePath;
    private BufferedReader filein;
    private BufferedWriter fileout;
    private boolean forReading = true;
    private boolean fileIsOpen = false;
    private boolean hasHeaders = true;
    private Charset cs = Charset.defaultCharset();
    private CSVHeadLine headers = null;

    /**
     * Costruttore degli oggetti di classe  CSVFile
     */
    public CSVFile(Path filePath
            , String separator
            , int recordlength
            , Charset cs
            , boolean hasHeaders) 
    {
        // inizializza le variabili d'istanza
        this.filePath = filePath;
        this.filename = filePath.toString();
        this.separator = separator;
        this.hasHeaders = hasHeaders;
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
        this(Paths.get(filename, ""),Config.DEFAULT_SEPARATOR,recordlength, Charset.defaultCharset(), false);
    }

    public CSVFile(String filename) throws IOException
    {
        this(Paths.get(filename, ""),Config.DEFAULT_SEPARATOR,0, Charset.defaultCharset(), false);
    }
 
    
    public void openRead() throws IOException, IllegalReadingMethodException
    {
        if (this.fileIsOpen)
        {
            this.close();
        }
        this.forReading = true;
        try
        {
            this.filein = Files.newBufferedReader(filePath,cs);
            createHeaders();
            this.fileIsOpen = true;
        }
        catch (IOException ex)
        {
            this.fileIsOpen = false;
            throw(ex);
        }
    }
    
    public void openWrite() throws IOException
    {
        if (this.fileIsOpen)
        {
            this.close();
        }
        this.forReading = false;
        try
        {
            {
                this.fileout = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING);
            }
            this.fileIsOpen = true;
        }
        catch (IOException ex)
        {
            this.fileIsOpen = false;
            throw(ex);
        }
    }
    
    public void openAppend() throws IOException
    {
        if (this.fileIsOpen)
        {
            this.close();
        }
        this.forReading = false;
        try
        {
            {
                this.fileout = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND);
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
    
    public CSVLine getCSVLine() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException
    {
        String line;
        line = this.getLine();
        if (line == null) return null;
        return new CSVLine(line, this.separator, this.headers);
    }
    
    public CSVLinePool getPool(int maxSize) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException
    {
        CSVLinePool pool = new CSVLinePool();
        int count = 0;
        CSVLine line;
        while ((line = this.getCSVLine()) != null)
        {
            pool.add(line);
            count++;
            if (count==maxSize) return pool;
        }
        return pool;
    }
        
    public void writeLine(String line) throws IOException, IllegalReadingMethodException
    {
        if (readLine && !forReading && fileIsOpen) {
         fileout.write(line);
         fileout.newLine();
        }
        if (!readLine && !forReading && fileIsOpen) {
            char buffer[];
            if (line.length() < this.recordlength)
            {
                for(int i = 0; i < (this.recordlength - line.length()); i++)
                {
                    line = line.concat(" ");
                }
            }
            buffer = line.toCharArray();
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
    
    public CSVHeadLine getHeaders()
    {
        return this.headers;
    }
    
    public void setHeaders(CSVHeadLine headers)
    {
        this.headers = headers;

    }

    private void createHeaders() throws IOException, IllegalReadingMethodException {
        String line;
        line = this.getLine();
        
        if (this.hasHeaders())
        {
            this.headers = new CSVHeadLine(line, this.separator);
        }
        else
        {
            String fakeline = "";
            int count = 0;
            for (String s : line.split(this.separator))
            {
                fakeline = "FIELD_" + count + this.separator;
                count++;
            }
            line = fakeline.substring(0, fakeline.lastIndexOf(this.separator)-1);
            this.headers = new CSVHeadLine(line, this.separator);
        }
        
    }
        

}
