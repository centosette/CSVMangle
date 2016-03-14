/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanaging;

import config.Config;
import java.util.ArrayList;

/**
 *
 * @author marco
 */
public class CSVHeadLine extends ArrayList<ICSVField> implements ICSVLine{
    
    private String text;
    private String separator = Config.DEFAULT_SEPARATOR;
    private int headerCount = 0;
    
    
    public CSVHeadLine (String text, String separator)
    {
        this.text = text;
        this.separator = separator;
        
        
        for (String s : text.split(separator))
        {
            this.add(new CSVHeader(s, this.headerCount));
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
    
    public String getHeaders() {
        return this.text;
    }
    
    public int getFieldCount()
    {
        return this.headerCount;
    }
    
    public String getSeparator()
    {
        return this.separator;
    }

    @Override
    public int getStringSize() {
        return this.text.length();
    }
}
