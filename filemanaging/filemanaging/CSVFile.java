package filemanaging; 

import config.Config;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;

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
    private boolean haveGuessedLines = false;
    private long guessedLines = 0;
    private long size = 0;

    /**
     * Costruttore degli oggetti di classe  CSVFile
     */
    public CSVFile(Path filePath
            , String separator
            , int recordlength
            , Charset cs
            , boolean hasHeaders) throws IOException 
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
        this.size = Files.size(filePath);
        if (recordlength > 0) 
        {
            this.guessedLines = (long) Math.ceil((double)(size/recordlength));
            this.haveGuessedLines = true;
        }
        else
        {
            //line guess is deferred to openforreading operation
        }
        
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
           this.setClosed();
        }
        
    }
    
    public void delete() throws IOException
    {
        if (this.fileIsOpen) this.close();
        Files.delete(filePath);
    }
    
    public String getLine() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException
    {
        if (!fileIsOpen) this.openRead();
        if (readLine && forReading) {
         return filein.readLine();
        }
        if (!readLine && forReading)
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
    
    
    
    public ArrayList<String> readSample(int maxSize) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException
    {
        ArrayList<String> sample = new ArrayList<>();
        int count = 0;
        String line;
        while ((line = this.getLine()) != null)
        {
            sample.add(line);
            count++;
            if (count==maxSize) return sample;
        }
        return sample;
        
    }
        
    public void writeLine(String line) throws IOException, IllegalWritingMethodException
    {
        if (!fileIsOpen) openWrite();
        if (readLine && !forReading) {
         fileout.write(line);
         fileout.newLine();
        }
        if (!readLine && !forReading) {
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
        throw(new IllegalWritingMethodException());
    }
    
    public void writePool(CSVLinePool pool) throws IOException, IllegalWritingMethodException
    {
        for(ICSVLine l : pool)
        {
            this.writeLine(l.toString());
        }
    }
    
    public void appendLine(String line) throws IOException, IllegalWritingMethodException
    {
        if (!fileIsOpen) openAppend();
        writeLine(line);
    }
    
    public void appendPool(CSVLinePool pool) throws IOException, IllegalWritingMethodException
    {
        if (!fileIsOpen) openAppend();
        writePool(pool);
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
    
    public long getGuessedLines()
    {
        if(haveGuessedLines) return this.guessedLines;
        return 0L;
    }
    
    public long getSize()
    {
        return this.size;
    }
    
    private void setClosed()
    {
        this.fileIsOpen = false;
        this.filein = null;
        this.fileout = null;
    }
    
    private void setOpen()
    {
        this.fileIsOpen = true;
    }
    
   
    private void openRead() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException
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
            guessLines();
            this.setOpen();
        }
        catch (IOException ex)
        {
            this.setClosed();
            throw(ex);
        }
    }
    
    private void openWrite() throws IOException
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
            this.setOpen();
        }
        catch (IOException ex)
        {
            this.setClosed();
            throw(ex);
        }
    }
    
    private void openAppend() throws IOException
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
            this.setOpen();
        }
        catch (IOException ex)
        {
            this.setClosed();
            throw(ex);
        }
    }

    private void createHeaders() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException {
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

    private void guessLines() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException {
        if (!haveGuessedLines)
        {
            long totalLength = 0;
            ArrayList<String> sample = this.readSample(Config.SAMPLE_SIZE_SMALL);
            for (String s : sample)
            {
                totalLength += s.getBytes().length + Config.DEFAULT_NEWLINE_BYTES;
            }
            this.guessedLines = (long)Math.ceil((double)this.size/((double)(totalLength/Config.SAMPLE_SIZE_SMALL)));
            this.haveGuessedLines = true;
            
        }
    }

    @Override
    public void rewind() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException {
        reOpen(); //there must be some way better than this...

    }
    private void reOpen() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException {
        if (this.fileIsOpen && this.forReading) {
            this.close();
            this.openRead();
        }
    }

}
