package filemanaging;


/**
 * Aggiungi qui una descrizione dell'interfaccia ICSVLine
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */

public interface ICSVLine extends Iterable<ICSVField>
{
   public int getFieldCount();
   public String toString();
   public String getSeparator();
   public String getHeaders();
}
