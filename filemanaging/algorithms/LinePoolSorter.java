/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import filemanaging.CSVLinePool;
import rules.CSVSortKeySpec;

/**
 *
 * @author chiarand
 */
public interface LinePoolSorter {
    
    public CSVLinePool sort (CSVLinePool inpool, CSVSortKeySpec ks);
    
}
