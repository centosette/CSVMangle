/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import config.Config.DataType;
import datastructures.AssociativeCollection;
import datastructures.DuplicatedPairException;
import java.util.Map;
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
    
    public void setRule (Rule r) throws IncompatibleTypeAndRuleException
    {
        this.rule = r;
    }
    
    public FieldType getFieldType()
    {
        return this.fType;
    }
    
    private boolean checkCompatibility (FieldType ft, Rule r) throws DuplicatedPairException
    {
        AssociativeCollection<DataType, Rule> map = Rules.getTypeRuleCompatibilityMap();
        DataType t = ft.getDataType();
        //return compatibility check!
    }
}
