/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import algorithms.TextDelimitersMustBeEvenException;
import config.Config;
import config.Config.DataType;
import filemanaging.CSVLinePool;
import filemanaging.ICSVField;
import filemanaging.ICSVFile;
import filemanaging.ICSVLine;
import filemanaging.IllegalFieldNumberInLineException;
import filemanaging.IllegalReadingMethodException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author marco
 */
public class FieldTypeList extends ArrayList<FieldType>{
    
    private ICSVFile file;
    
    public FieldTypeList(ICSVFile file)
    {
        this.file = file;
    }
    
    public ICSVFile getFile()
    {
        return this.file;
    }
    //
    public void guessFieldTypes() throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException
    {
        this.guessFieldTypes(Config.SAMPLE_SIZE_MID);
    }
    public void guessFieldTypes(int samplesize) throws IOException, IllegalReadingMethodException, IllegalFieldNumberInLineException, TextDelimitersMustBeEvenException
    {
        CSVLinePool pool = file.getPool(samplesize);
        int size = file.getHeaders().getFieldCount();
        for (int i = 0; i < size; i++)
        {
            this.add(new FieldType(DataType.UNDEFINED, i));
        }
        for (ICSVLine line : pool)
        {
            for (ICSVField f : line)
            {
                String val = f.getValue().trim();
                if (val.isEmpty()) this.get(f.getPosition()).setNulls(true);
                if (isDate(val)) {
                    DataType before = this.get(f.getPosition()).getDataType();
                    DataType after = DataType.DATE;
                    if (before != DataType.UNDEFINED && before != after)
                    {
                        this.get(f.getPosition()).setDataType(Config.DEFAULT_DATATYPE);
                        continue;
                    }
                    else
                    {
                        this.get(f.getPosition()).setDataType(after);
                        continue;
                    }
                    
                    
                }
                
                if (isNumeric(val)) {
                    DataType before = this.get(f.getPosition()).getDataType();
                    DataType after = DataType.NUMERIC;
                    if (before != DataType.UNDEFINED && before != after)
                    {
                        this.get(f.getPosition()).setDataType(Config.DEFAULT_DATATYPE);
                        continue;
                    }
                    else
                    {
                        this.get(f.getPosition()).setDataType(after);
                        continue;
                    }
                   
                }
                
                if (isText(val)) {
                    DataType before = this.get(f.getPosition()).getDataType();
                    DataType after = DataType.TEXT;
                    if (before != DataType.UNDEFINED && before != after)
                    {
                        this.get(f.getPosition()).setDataType(Config.DEFAULT_DATATYPE);
                        continue;
                    }
                    else
                    {
                        this.get(f.getPosition()).setDataType(after);
                        continue;
                    }
                    
                }
                
                
            }
        }
    }
    
    private boolean isNumeric(String text)
    {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            nf.parse(text);
        } catch (ParseException ex) {
            return false;
        }
        return true;
    }
    
    private boolean isText(String text)
    {
        return true;
    }
    
    private boolean isDate(String text)
    {
        
        for (int i : Config.dateFormats)
        {
            DateFormat df = DateFormat.getDateInstance(i);
            try {
                df.parse(text);
            } catch (ParseException ex) {
                continue;
            }
            return true;
        }
        
        return false;
    }
}
