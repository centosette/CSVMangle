/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import config.Config.DataType;
import datastructures.AssociativeCollection;
import datastructures.DuplicatedPairException;
import datastructures.NaiveCollection;

/**
 *
 * @author marco
 */
public class Rules {
    public static enum Rule {ISKEY, GROUP, SUM, COUNT, COUNT_DISTINCT, MAX, MIN, IGNORE, UNDEFINED, ANY}
    public static enum Nullable{YES, NO}
    public static enum SortDirection{ASC, DESC}
    public static enum TextDelimitersSplit{REMOVE_START_TRAIL_DELMITERS, REMOVE_ALL_DELIMITERS, LEAVE_DELIMITERS}
    public static final int NONRELEVANT = -1;
    
    /**
     * returns a map of the compatibilities between data types and applicable rules.
     * 
     * @return 
     */
    public static AssociativeCollection<DataType, Rule> getTypeRuleCompatibilityMap()
    {
        AssociativeCollection<DataType, Rule> map = new NaiveCollection<>();
        try{
            map.addPair(DataType.NUMERIC, Rules.Rule.ANY);
            map.addPair(DataType.TEXT, Rules.Rule.ISKEY);
            map.addPair(DataType.TEXT, Rules.Rule.GROUP);
            map.addPair(DataType.TEXT, Rules.Rule.COUNT);
            map.addPair(DataType.TEXT, Rules.Rule.COUNT_DISTINCT);
            map.addPair(DataType.TEXT, Rules.Rule.MAX);
            map.addPair(DataType.TEXT, Rules.Rule.MIN);
            map.addPair(DataType.TEXT, Rules.Rule.IGNORE);
            map.addPair(DataType.DATE, Rules.Rule.ISKEY);
            map.addPair(DataType.DATE, Rules.Rule.GROUP);
            map.addPair(DataType.DATE, Rules.Rule.COUNT);
            map.addPair(DataType.DATE, Rules.Rule.COUNT_DISTINCT);
            map.addPair(DataType.DATE, Rules.Rule.MAX);
            map.addPair(DataType.DATE, Rules.Rule.MIN);
            map.addPair(DataType.DATE, Rules.Rule.IGNORE);
            map.addPair(DataType.UNDEFINED, Rules.Rule.ISKEY);
            map.addPair(DataType.UNDEFINED, Rules.Rule.GROUP);
            map.addPair(DataType.UNDEFINED, Rules.Rule.COUNT);
            map.addPair(DataType.UNDEFINED, Rules.Rule.COUNT_DISTINCT);
            map.addPair(DataType.UNDEFINED, Rules.Rule.IGNORE);
            map.addPair(DataType.ANY, Rules.Rule.ISKEY);
            map.addPair(DataType.ANY, Rules.Rule.GROUP);
            map.addPair(DataType.ANY, Rules.Rule.COUNT);
            map.addPair(DataType.ANY, Rules.Rule.COUNT_DISTINCT);
            map.addPair(DataType.ANY, Rules.Rule.IGNORE);
        }
        catch (DuplicatedPairException ex)
        {
            throw new Error("Compatibility configuration map contains an error (duplicated rule): check class Rules\n");
        }
        return map;

    }
    
}
