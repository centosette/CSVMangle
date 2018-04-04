package filemanaging;


/**
 * Aggiungi qui una descrizione della classe CSVField
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */
public class CSVField implements ICSVField
{
    // variabili d'istanza - sostituisci l'esempio che segue con il tuo
    private String name;
    private String value;
    private int position;
    
    
    /**
     * Costruttore degli oggetti di classe  CSVField
     */
    public CSVField(String name, String value, int position)
    {
        // inizializza le variabili d'istanza
        this.name = name;
        this.value = value;
        this.position = position;

    }
    
    public CSVField(CSVHeader header, String value, int position)
    {
        // inizializza le variabili d'istanza
        this(header.getName(), value, position);

    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getValue()
    {
        return this.value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public int getPosition()
    {
        return this.position;
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }


}
