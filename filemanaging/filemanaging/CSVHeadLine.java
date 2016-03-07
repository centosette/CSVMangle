/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanaging;

import java.util.ArrayList;

/**
 *
 * @author marco
 */
public class CSVHeadLine extends ArrayList<CSVHeader>{
    
    private String text;
    private String separator = Config.DEFAULT_SEPARATOR;
    private int headerCount = 0;
    
    
    public CSVHeadLine (String text, String separator)
    {
        this.text = text;
        this.separator = separator;
        
        for (String s : text.split(separator))
        {
            this.add(new CSVHeader(s));
            headerCount++;
        }
    }
    
    public CSVHeadLine (String text)
    {
        this(text, Config.DEFAULT_SEPARATOR);
    }
    
    @Override
    public String toString()
    {
        return this.text;
    }
    
    public int getHeaderCount()
    {
        return this.headerCount;
    }
}
