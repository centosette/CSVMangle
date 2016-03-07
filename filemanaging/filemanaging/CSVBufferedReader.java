/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanaging;

import java.io.BufferedReader;
import java.io.Reader;

/**
 *
 * @author marco
 */
public class CSVBufferedReader extends BufferedReader{
    
    public CSVBufferedReader(Reader in, int sz) {
        super(in, sz);
    }
    
    public CSVBufferedReader(Reader in)
    {
        super(in);
    }
    
}
