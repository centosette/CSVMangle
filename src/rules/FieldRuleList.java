/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import rules.Rules.Rule;

/**
 *
 * @author marco
 */
public class FieldRuleList extends ArrayList<FieldRule>{
    public FieldRuleList(FieldTypeList typeList, List<Rule> ruleList) throws IncompatibleTypeAndRuleException
    {
        if (typeList.size() != ruleList.size())
        {
            throw new IllegalArgumentException("FieldTypeList and List<Rule> must have the same size.");
        }
        Iterator<FieldType> t = typeList.iterator();
        Iterator<Rule> r = ruleList.iterator();
        
        while(t.hasNext() && r.hasNext())
        {
            this.add (new FieldRule(t.next(), r.next()));
        }
        
    }
    
    public FieldRuleList(FieldTypeList typeList) throws IncompatibleTypeAndRuleException
    {
        for(FieldType ft : typeList)
        {
            this.add(new FieldRule(ft, Rule.UNDEFINED));
        }
    }
    
}
