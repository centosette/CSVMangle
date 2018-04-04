package filemanaging;


/**
 * Represents a field in a row.
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */

public interface ICSVField
{
    
    public String getName();
        
    public void setName(String name);
    
    public String getValue();
   
    public void setValue(String value);
    
    public int getPosition();
    
    public void setPosition(int position);
    
}
