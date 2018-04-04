package filemanaging;


/**
 * Aggiungi qui una descrizione della classe CSVHeader
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */
public class CSVHeader implements ICSVField
{
    private String name;
    private int position;

    public CSVHeader(String name, int position) {
        this.name = name;
    }
    
    public String getName ()
    {
        return this.name;
    }
    
    public int getPosition()
    {
        return this.position;
    }

    @Override
    public String getValue() {
        return this.getName();
    }

    @Override
    public void setValue(String value) {
        this.setName(value);
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

}
