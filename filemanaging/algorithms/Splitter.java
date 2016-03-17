/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import filemanaging.CSVFilePool;
import filemanaging.ICSVFile;
import filemanaging.IllegalFieldNumberInLineException;
import filemanaging.IllegalReadingMethodException;
import filemanaging.IllegalWritingMethodException;
import java.io.IOException;

/**
 *
 * @author marco
 */
public interface Splitter {
    
    public CSVFilePool split(ICSVFile file, int size) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, IllegalWritingMethodException;
    
}
