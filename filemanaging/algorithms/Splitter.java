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
public interface Splitter {
    
    public CSVFilePool split(ICSVFile file, int size);
    
}
