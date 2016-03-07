/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanaging;

import java.io.BufferedWriter;
import java.io.Writer;

/**
 *
 * @author marco
 */
public class CSVBufferedWriter extends BufferedWriter{
    
    public CSVBufferedWriter(Writer out) {
        super(out);
    }
    
    public CSVBufferedWriter (Writer out, int sz)
    {
        super(out, sz);
    }
    
}
