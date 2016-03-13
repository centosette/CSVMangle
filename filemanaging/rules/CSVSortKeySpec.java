/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import java.lang.reflect.Array;
import java.util.ArrayList;
import rules.Rules.SortDirection;



/**
 *
 * @author marco
 */
public class CSVSortKeySpec {
    private FieldTypeList typeList;
    private int[] relevant;
    private SortDirection[] direction;
    
    
    public CSVSortKeySpec (FieldTypeList typeList, int[] relevant, SortDirection[] direction)
    {
        this.typeList = typeList;
        this.relevant = relevant;
        this.direction = direction;
        
        if (typeList.size() != relevant.length || typeList.size() != direction.length)
        {
            throw new IllegalArgumentException("typeList, relevant and direction must be the same length.");
        }
    }
    
    public CSVSortKeySpec (FieldTypeList typeList, String relevant) throws NumberFormatException, NegativeIndexForField
    {
        ArrayList<Integer> temp = new ArrayList<>();
    
        int val;
        for (String r : relevant.split(","))
        {
            
                val = Integer.parseInt(r); //throws NumberFormatException
                if (val < 0) throw new NegativeIndexForField();
                temp.add(new Integer(val));
        }
        if (typeList.size() != temp.size())
        {
            throw new IllegalArgumentException("typeList and relevant string must have the same number of elements");
        }
        this.relevant = (int[]) Array.newInstance(int.class, temp.size());
        for (int i = 0; i < temp.size(); i++)
        {
            this.relevant[i] = temp.get(i).intValue();
        }
        this.direction = (SortDirection[]) Array.newInstance(SortDirection.class, temp.size());
        for (int i = 0; i < direction.length; i++) direction[i] = SortDirection.ASC;
        checkRelevantArray();
        
    }
    
    private void checkRelevantArray() {
        int[] temp = java.util.Arrays.copyOf(relevant, relevant.length);
        java.util.Arrays.sort(temp);
        int prev = Rules.NONRELEVANT;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != Rules.NONRELEVANT && temp[i] == prev) {
                throw new IllegalArgumentException("Field Sorting Priority is misspecified: two or more fields have the same priority");
            }
            prev = temp[i];
        }

    }
    
}
