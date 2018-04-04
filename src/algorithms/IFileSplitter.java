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
 * A Splitter can divide a single ICSVFIle into multiple ones (a pool) of a 
 * fixed number of lines
 * 
 *
 * @author marco
 */
public interface IFileSplitter {
    
    public CSVFilePool split(ICSVFile file, int lines) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, IllegalWritingMethodException, TextDelimitersMustBeEvenException;
    
}
