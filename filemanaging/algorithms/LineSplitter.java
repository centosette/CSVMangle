/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import config.Config;
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
public class LineSplitter implements ISplitter{
    
    private ICSVFile file;
    private int numLines;
    private final CSVFilePool pool;
    private final UniqueGenerator ug;
    
    public LineSplitter ()
    {
        this.pool = new CSVFilePool();
        this.ug = UniqueGenerator.getInstance();
    }

    @Override
    public CSVFilePool split(ICSVFile file, int numLines) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, IllegalWritingMethodException{
        this.file = file;
        this.numLines = numLines;
        splitSerial();
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
            
            ICSVFile poolMember = new CSVFile(Config.DEFAULT_WORKING_DIR + ug.getUnique() + ".csv");
            poolMember.writePool(lp);
            poolMember.close();
            pool.add(poolMember);
        }
    }
        

    
}
