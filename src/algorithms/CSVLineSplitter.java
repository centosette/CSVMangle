/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import config.Config;
import rules.Rules;
import rules.Rules.TextDelimitersSplit;

/**
 *
 * @author marco
 */
public class CSVLineSplitter {
    public static String[] split(String toSplit, String separator, String delimiter, TextDelimitersSplit delimiterPolicy) throws TextDelimitersMustBeEvenException
    {
        String[] splitted;
        String[] tmp = new String[1];
        int occurrence;
        
        
        occurrence = occurs(toSplit, delimiter);
        
        if (occurrence%2 != 0) throw new TextDelimitersMustBeEvenException("Text delimiters must be even");
        
        splitted = toSplit.split(separator, -1);
        tmp[0] = splitted[0];
        
        int count = 0;
        for(int i = 0; i < splitted.length; i++)
        {
            String builder = splitted[i];
            while (true)
            {
                occurrence = occurs(builder, delimiter);
                if(occurrence%2 == 0) 
                {
                    count++;
                    tmp = java.util.Arrays.copyOf(tmp, count);
                    builder = applyDelimiterPolicy(builder, delimiter, delimiterPolicy);
                    tmp[count - 1] = builder;
                    break;
                }
                else
                {
                    builder = builder.concat(separator);
                    builder = builder.concat(splitted[i+1]);
                    i++;
                }
            }
        }
        
        return tmp;
        
    }
    
    public static String[] split(String toSplit) throws TextDelimitersMustBeEvenException
    {
        return split(toSplit, Config.DEFAULT_SEPARATOR, Config.DEFAULT_TEXT_DELIMITER, Rules.TextDelimitersSplit.REMOVE_ALL_DELIMITERS);
    }
    
    private static int occurs(String inString, String text)
    {
        int count = 0;
        int index = -1;
        while ((index = inString.indexOf(text, index+1)) != -1) count++;
        return count;
    }
    
    private static String applyDelimiterPolicy (String text, String delimiter, TextDelimitersSplit policy)
    {
        switch (policy)
        {
            case LEAVE_DELIMITERS:
                break;
            case REMOVE_ALL_DELIMITERS:
                text = text.replaceAll(delimiter, "");
                break;
            case REMOVE_START_TRAIL_DELMITERS:
                text = text.replaceFirst("\\s*" + delimiter, "");
                if (occurs(text, delimiter)%2==0) break;
                text = text.replaceAll("\\s*" + delimiter + "$", "");
                break;
            
                
        }
        return text;
    }
}
