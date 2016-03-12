/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author marco
 */
public class CSVKeySpec {
    private FieldTypeList typeList;
    private int[] relevant;
    
    
    public CSVKeySpec (FieldTypeList typeList, int[] relevant)
    {
        this.typeList = typeList;
        this.relevant = relevant;
    }
    
    public CSVKeySpec (FieldTypeList typeList, String relevant) throws NumberFormatException, NegativeIndexForField
    {
        ArrayList<Integer> temp = new ArrayList<>();
    
        int val;
        for (String r : relevant.split(","))
        {
            
                val = Integer.parseInt(r); //throws NumberFormatException
                if (val < 0) throw new NegativeIndexForField();
                temp.add(new Integer(val));
        }
        this.relevant = (int[]) Array.newInstance(int.class, temp.size());
        for (int i = 0; i < temp.size(); i++)
        {
            this.relevant[i] = temp.get(i).intValue();
        }
    }
    
}
