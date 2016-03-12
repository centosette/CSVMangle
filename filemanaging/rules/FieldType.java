/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import config.Config.DataType;
import filemanaging.CSVFile;

/**
 *
 * @author marco
 */
public class FieldType {
    private int position;
    private DataType type;
    private boolean nulls = false;

    
    public FieldType (DataType type, int position, boolean nulls)
    {
        this.type = type;
        this.position = position;
        this.nulls = nulls;
    }
    
    public FieldType (DataType type, int position)
    {
        this(type, position, false);
    }
    
    public int getPosition()
    {
        return this.position;
    }
    
    public DataType getDataType()
    {
        return this.type;
    }
    
    public void setDataType (DataType type)
    {
        this.type = type;
    }
    
    public boolean hasNulls()
    {
        return this.nulls;
    }
    
    public void setNulls(boolean nulls)
    {
        this.nulls = nulls;
    }
    
    
}
