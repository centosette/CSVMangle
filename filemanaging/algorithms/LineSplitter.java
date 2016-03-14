/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import filemanaging.CSVFile;
import filemanaging.CSVFilePool;
import filemanaging.CSVLinePool;
import filemanaging.ICSVFile;
import filemanaging.IllegalFieldNumberInLineException;
import filemanaging.IllegalReadingMethodException;
import filemanaging.IllegalWritingMethodException;
import java.io.IOException;
import utils.UniqueGenerator;

/**
 *
 * @author marco
 */
public class LineSplitter implements Splitter{
    
    private ICSVFile file;
    private int numLines;
    private CSVFilePool pool;
    private UniqueGenerator ug;
    
    public LineSplitter ()
    {
        this.pool = new CSVFilePool();
        this.ug = UniqueGenerator.getInstance();
    }

    @Override
    public CSVFilePool split(ICSVFile file, int numLines) {
        this.file = file;
        this.numLines = numLines;
        return pool;       
        
    }
    
    private void splitSerial () throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, IllegalWritingMethodException
    {   
        for(;;)        
        {
            CSVLinePool lp = file.getPool(numLines);
            if (lp == null)
            {
                return;
            }
            
            ICSVFile poolMember = new CSVFile(ug.getUnique() + ".csv");
            poolMember.writePool(lp);
            pool.add(poolMember);
        }
    }
        

    
}
