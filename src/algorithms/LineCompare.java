/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import config.Config.DataType;
import filemanaging.ICSVLine;
import java.util.List;
import rules.CSVSortKeySpec;
import rules.FieldType;

/**
 *
 * @author chiarand
 */
public class LineCompare {
    
    private CSVSortKeySpec ks;
    
    public LineCompare(CSVSortKeySpec ks)
    {
        this.ks = ks;
    }
    
    public int compare(ICSVLine line1, ICSVLine line2)
    {
        List<FieldType> ftl = ks.getFieldTypeList();
        int[] relv = ks.getRelevant();
        DataType dt;
        
        for (int i : relv)
        {
            int j = relv[i];
            dt = ftl.get(j).getDataType();
            String val1, val2;
            val1 = line1.getValue(j);
            val2 = line2.getValue(j);
            
        }
        return 0;
    }
    
    public boolean equals(ICSVLine line1, ICSVLine line2)
    {
        return false;
    }
    
    public boolean isGreater(ICSVLine greater, ICSVLine than)
    {
        return false;
    }
    
    public boolean isGreaterEqual(ICSVLine greater, ICSVLine than)
    {
        return false;
    }
    
}
