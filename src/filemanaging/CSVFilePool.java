package filemanaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Aggiungi qui una descrizione della classe CSVFilePool
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */
public class CSVFilePool extends ArrayList<ICSVFile>
{
    // variabili d'istanza
    
    private int fileCount=0;
    private int openFileCount = 0;

    /**
     * Costruttore degli oggetti di classe  CSVFilePool
     */
    public CSVFilePool()
    {
        super();
    }
    
    public boolean add(ICSVFile file)
    {
        boolean add = super.add (file);
        fileCount++;
        return add;
    }
    
    public int getFileCount()
    {
        return this.fileCount;
    }
    
    public int getOpenFileCount()
    {
        return this.openFileCount;
    }
    
    public void notifyClosed()
    {
        this.openFileCount --;
    }
    
    public void notifyOpen()
    {
        this.openFileCount ++;
    }
    
    @Override
    public void finalize() throws Throwable
    {
        try {
            for (ICSVFile f:this)
            {
                try {
                    f.close();
                } catch (IOException ex) {
                    //
                }
            }
        } finally {
            super.finalize();
        }
    }
    

}
