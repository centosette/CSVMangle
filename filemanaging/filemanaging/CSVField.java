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
    
    
    /**
     * Costruttore degli oggetti di classe  CSVField
     */
    public CSVField(String name, String value)
    {
        // inizializza le variabili d'istanza
        this.name = name;
        this.value = value;

    }
    
    public CSVField(CSVHeader header, String value)
    {
        // inizializza le variabili d'istanza
        this.name = header.getName();
        this.value = value;

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


}
