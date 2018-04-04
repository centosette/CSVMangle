/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import config.Config.DataType;
import datastructures.AssociativeCollection;
import rules.Rules.Rule;

/**
 *
 * @author marco
 */
public class FieldRule {
    private FieldType fType;
    private Rule rule;
    public FieldRule (FieldType fType, Rule rule) throws IncompatibleTypeAndRuleException
    {
        this.fType = fType;
        this.rule = rule;
    }
    public Rule getRule()
    {
        return this.rule;
    }
    
    public boolean setRule (Rule r) throws IncompatibleTypeAndRuleException
    {
        if(checkCompatibility(r))
        {
            this.rule = r;
            return true;
        }
        return false;
        
    }
    
    public FieldType getFieldType()
    {
        return this.fType;
    }
    
    private boolean checkCompatibility (Rule r)
    {
        AssociativeCollection<DataType, Rule> map = Rules.getTypeRuleCompatibilityMap();
        DataType t = this.fType.getDataType();
        //return compatibility check!
        boolean checkPair = map.checkPair(t, rule);
        return checkPair;
    }
}
