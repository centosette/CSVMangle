/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import filemanaging.CSVFilePool;
import filemanaging.ICSVFile;

/**
 *
 * @author marco
 */
public class LineSplitter implements Splitter{
    
    private ICSVFile file;
    private int size;
    private CSVFilePool pool;
    
    public LineSplitter ()
    {
        this.pool = new CSVFilePool();
    }

    @Override
    public CSVFilePool split(ICSVFile file, int size) {
        this.file = file;
        this.size = size;
        
        
    }
    
    private void splitSerial ()
    {
        
    }
    
    private void splitParallel()
    {
        
    }
    
}
