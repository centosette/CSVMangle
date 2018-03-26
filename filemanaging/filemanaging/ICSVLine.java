package filemanaging;


/**
 * Represents a row or line in a CSV file. The line is a record in the file
 * and the data are separated by special characters (separators).
 * 
 */

public interface ICSVLine extends Iterable<ICSVField>
{
   public int getFieldCount();
   public String toString();
   public String getSeparator();
   public String getHeaders();
   public int getStringSize();
   public String getValue(int index);
   public String getName(int index);
}
