package algorithms;


/**
 * Implements the methods to merge-sort a CSVFile. MergeSort splits a large
 * file into several chunks, sorts the little chuncks, then recomposes the
 * sorted union by popping an element from each chunk and choosing the least
 * or the greatest (depending on the sorting direction). The sorting of the 
 * lines is based on a CSVSortKeySpec, which describes the fields that are
 * relevant to the sorting and which is the sort direction 
 * (ascending/descending) for each of these fields.
 * @see CSVSortKeySpec
 * 
 * @author (il tuo nome) 
 * @version (un numero di versione o una data)
 */
public class MergeSorter
{
    // variabili d'istanza - sostituisci l'esempio che segue con il tuo
    private int x;

    /**
     * Costruttore degli oggetti di classe  MergeSorter
     */
    public MergeSorter()
    {
        // inizializza le variabili d'istanza
        x = 0;
    }

    /**
     * Un esempio di metodo - aggiungi i tuoi commenti
     * 
     * @param  y   un parametro d'esempio per un metodo
     * @return     la somma di x e y
     */
    public int sampleMethod(int y)
    {
        // metti qui il tuo codice
        return x + y;
    }
}
