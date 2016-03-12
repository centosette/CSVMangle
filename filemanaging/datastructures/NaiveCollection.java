/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;


import datastructures.DataStructParam.Behaviour;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author marco
 */
public class NaiveCollection <K, V> implements AssociativeCollection <K, V>{
    
    private final int EMPTY = -1;
    private final int INIT_SIZE = 32;
    private final int STEP_SIZE = 32;
    private final int KCOLUMN = 0;
    private final int VCOLUMN = 1;
    private final int COLUMNS = 2;
    private enum Column {KEYS, VALUES, ALL}
    private double loadFactor = 0.75;
    private double actualKLoad = 0.0;
    private double actualVLoad = 0.0;
    private double actualALoad = 0.0;
    private int actualKSize = 0;
    private int actualVSize = 0;
    private int associationCount = 0;
    private ArrayList<K> keys;
    private ArrayList<V> values;
    private int[][] association;
    private Behaviour behaviour;
    
    
    
    
    public NaiveCollection (Behaviour behaviour)
    {
        this.keys = new ArrayList<>(INIT_SIZE);
        this.values = new ArrayList<>(INIT_SIZE);
        this.association = new int[this.COLUMNS][INIT_SIZE];
        initAssociations();
        this.actualKSize = INIT_SIZE;
        this.actualVSize = INIT_SIZE;
        this.behaviour = behaviour;

    }
    public NaiveCollection ()
    {
        this(Behaviour.EXCEPTION);
    }
    
    private boolean updateStats(double loadFactor)
    {
        formallyCheckLoadFactor(loadFactor);
        int aSize = this.association[KCOLUMN].length;
        
        int aLoad = 0;
        
        for (int i = 0; i < aSize; i++)
        {
            if(association[KCOLUMN][i] != EMPTY) aLoad++;
        }
        
        associationCount = aLoad;
        actualKLoad = (double)((double)keys.size()/(double)actualKSize);
        actualVLoad = (double)((double)values.size()/(double)actualVSize);
        actualALoad = (double)((double)aLoad/(double)aSize);
        
        if(actualKLoad > loadFactor 
                || actualVLoad > loadFactor 
                || actualALoad > loadFactor) return true;
        return false;
    }
    
    private void initAssociations()
    {
        //set everything to EMPTY, association count to 0.
        int rows = this.association[KCOLUMN].length;
        for (int i = 0; i < rows; i++)
        {
            this.association[KCOLUMN][i] = this.EMPTY;
            this.association[VCOLUMN][i] = this.EMPTY;
        }
        this.associationCount = 0;
    }
    
    private void formallyCheckLoadFactor(double lf)
    {
        if(lf < 0.5 || lf > 0.95) throw new IllegalArgumentException("Load Factor can be between 0.5 and 0.95");
    }
    
    private void formallyCheckStepSize(int step)
    {
        if (step < 1) throw new IllegalArgumentException("Step must be 1 or more");
    }
    
    private void upsizeAssociation(int step)
    {
        formallyCheckStepSize(step);
        int rows = this.association[KCOLUMN].length;
        int newlen = rows + step;
        int temp[][] = new int[this.COLUMNS][newlen];
        for(int col = 0; col < this.COLUMNS; col++)
        {
            int i = 0;
            for(int j = 0; j < rows; j++)
            {
                temp[col][j] = this.association[col][j];
                i++;
            }
            for(int j = i + 1; j < newlen; j++)
            {
                temp[col][j] = this.EMPTY;
            }
        }
        this.association = temp;
        this.actualALoad = (double)((double)associationCount/(double)this.association[KCOLUMN].length);
        
    }
    
    private boolean lookupAssociation (int key, int value)
    {
        for(int j = 0; j < this.association[KCOLUMN].length; j++)
        {
            if(this.association[VCOLUMN][j] == value 
                    && this.association[KCOLUMN][j] == key) return true;

        }
        return false;
    }
    
    private void upsize(Column col, int step)
    {
        formallyCheckStepSize(step);
        if(col == Column.KEYS || col == Column.ALL)
        {
            this.keys.ensureCapacity(actualKSize + step);
            actualKSize += step;
            this.actualKLoad = (double)((double)keys.size()/(double)actualKSize);
        }
        if(col == Column.VALUES || col == Column.ALL)
        {
            this.values.ensureCapacity(actualVSize + step);
            actualVSize += step;
            this.actualVLoad = (double)((double)values.size()/(double)actualVSize);
        }
        
    }
    
    private void cleanupAssociation (double targetLoadFactor)
    {
        //delete all the EMPTY parts such that the new ratio comes close to targetLoadFactor
        //makes sense only if the number of EMPTY is very high with respect to the association's size
        //that may show up if a great number of removals take place. In that case, the actual load factor should be low
        //and the target should be higher, for a better memory space usage.
        formallyCheckLoadFactor(targetLoadFactor);
        updateStats(this.loadFactor);
        if(this.actualALoad >= targetLoadFactor) return;
        //we have to reduce the vector length in order to achieve a grater LF
        int newlen = (int)Math.ceil((double)this.associationCount/this.actualALoad);
        int temp[][] = new int[COLUMNS][newlen];
        int j = 0;
        for(int i = 0; i < association[KCOLUMN].length; i++)
        {
            if(association[KCOLUMN][i] != EMPTY && association[VCOLUMN][i] != EMPTY)
            {
                temp[KCOLUMN][j] = association[KCOLUMN][i];
                temp[VCOLUMN][j] = association[VCOLUMN][i];
                j++;
            }
            if(this.association[KCOLUMN][i] == this.EMPTY && this.association[VCOLUMN][i] == this.EMPTY)
            {
                continue;
            }
            throw new Error("Fatal error: associative array is not aligned, row " + i);
            
        }
        for (int i = j+1; i < newlen; i++)
        {
            temp[KCOLUMN][j] = EMPTY;
            temp[VCOLUMN][j] = EMPTY;
        }
        this.association = temp;
        this.actualALoad = (double)((double)(this.associationCount)/(double)newlen);
    }
    
    private void checkLoad(double targetLoadFactor)
    {
        //upsize if necessary
        formallyCheckLoadFactor(targetLoadFactor);
        if (!updateStats(targetLoadFactor)) return;
        if (this.actualALoad > targetLoadFactor)
        {
            int newlen = (int)Math.ceil((double)this.associationCount/targetLoadFactor);
            upsizeAssociation(newlen - association[KCOLUMN].length);
        }
        if (this.actualKLoad > targetLoadFactor)
        {
            int newlen = (int)Math.ceil((double)keys.size()/targetLoadFactor);
            upsize(Column.KEYS, newlen - keys.size());
        }
        if (this.actualVLoad > targetLoadFactor)
        {
            int newlen = (int)Math.ceil((double)values.size()/targetLoadFactor);
            upsize(Column.KEYS, newlen - keys.size());
        }
    }
    
    private void checkLoad()
    {
        this.checkLoad(this.loadFactor);
    }
    
    private int getFirstEmpty()
    {
        int i;
        for (i = 0; i < this.association[KCOLUMN].length; i++)
        {
            if(this.association[KCOLUMN][i] == this.EMPTY && this.association[VCOLUMN][i] == this.EMPTY) return i;
            if(this.association[KCOLUMN][i] == this.EMPTY || this.association[VCOLUMN][i] == this.EMPTY)
            {
                throw new Error("Fatal error: associative array is not aligned, row " + i);
            }
        }
        //no empty!
        upsizeAssociation(this.STEP_SIZE);
        if(this.association[KCOLUMN][i] == this.EMPTY && this.association[VCOLUMN][i] == this.EMPTY) return i;
        throw new Error("getFirstEmpty: cannot locate an empty element after upsize! row " + i);
    }
    
    private boolean removeKey(K key)
    {
        // HARD
        if (key == null) throw new NullPointerException("Key cannot be null"); 
        int kIndex = keys.indexOf(key);
        if (kIndex < 0) return false;
        keys.set(kIndex, null);
        return true;
        
    }
    
    private boolean removeValue (V val)
    {
        // HARD
        if (val == null) throw new NullPointerException("Value cannot be null"); 
        int vIndex = values.indexOf(val);
        if (vIndex < 0) return false;
        keys.set(vIndex, null);
        return true;
    }
//-------------------------------------PUBLIC----------------------------------------
    @Override
    public List<V> getValues(K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        List<V> vals = new ArrayList<>();
        int kIndex = keys.indexOf(key);
        if (kIndex < 0) return null;
        int nonempty = 0;
        for(int i = 0; i < this.association[KCOLUMN].length; i++)
        {
            if(association[KCOLUMN][i] == kIndex)
            {
                vals.add(values.get(association[VCOLUMN][i]));
                nonempty++;
                
                if (nonempty == associationCount) return vals;
            }
            if(association[KCOLUMN][i] != EMPTY) nonempty++;
            if (nonempty == associationCount) return vals;
            
        }
        return vals;
        
    }

    @Override
    public List<K> getKeys(V value) {
        if (value == null) throw new NullPointerException("Value cannot be null");
        List<K> ks = new ArrayList<>();
        int vIndex = values.indexOf(value);
        if (vIndex < 0) return null;
        int nonempty = 0;
        for(int i = 0; i < this.association[VCOLUMN].length; i++)
        {
            if(association[VCOLUMN][i] == vIndex)
            {
                ks.add(keys.get(association[KCOLUMN][i]));
                nonempty++;
                if (nonempty == associationCount) return ks;
            }
            if(association[VCOLUMN][i] != EMPTY) nonempty++;
            if (nonempty == associationCount) return ks;
            
        }
        return ks;
    }

    @Override
    public boolean checkKey(K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        return keys.contains(key);
    }

    @Override
    public int countKey(K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        int kIndex = keys.indexOf(key);
        if (kIndex < 0) return 0;
        int match = 0;
        int nonempty = 0;
        for(int i = 0; i < this.association[KCOLUMN].length; i++)
        {
            if(association[KCOLUMN][i] == kIndex)
            {
                match++;
                nonempty++;
                if (nonempty == associationCount) return match;
            }
            if(association[KCOLUMN][i] != EMPTY) nonempty++;
            if (nonempty == associationCount) return match;
            
        }
        return match;
    }

    @Override
    public boolean checkValue(V value) {
        return values.contains(value);
    }

    @Override
    public int countValue(V value) {
        if (value == null) throw new NullPointerException("Value cannot be null");
        int vIndex = values.indexOf(value);
        if (vIndex < 0) return 0;
        int match = 0;
        int nonempty = 0;
        for(int i = 0; i < this.association[VCOLUMN].length; i++)
        {
            if(association[VCOLUMN][i] == vIndex)
            {
                match++;
                nonempty++;
                if (nonempty == associationCount) return match;
            }
            if(association[VCOLUMN][i] != EMPTY)nonempty++;
            if (nonempty == associationCount) return match;
        }
        return match;
    }

    @Override
    public boolean checkPair(K key, V value) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        if (value == null) throw new NullPointerException("Value cannot be null");
        int kIndex = keys.indexOf(key);
        if (kIndex < 0) return false;
        int vIndex = values.indexOf(value);
        if (vIndex < 0) return false;
        int nonempty = 0;
        for(int i = 0; i < this.association[KCOLUMN].length; i++)
        {
            if(association[KCOLUMN][i] == kIndex && association[VCOLUMN][i] == vIndex) return true;
            if(association[KCOLUMN][i] != EMPTY) nonempty++;
            if (nonempty == associationCount) return false;
            
        }
        return false;
    }

    @Override
    public int countPair(K key, V value) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        if (value == null) throw new NullPointerException("Value cannot be null");
        int kIndex = keys.indexOf(key);
        if (kIndex < 0) return 0;
        int vIndex = values.indexOf(value);
        if (vIndex < 0) return 0;
        int match = 0;
        int nonempty = 0;
        for(int i = 0; i < this.association[KCOLUMN].length; i++)
        {
            if(association[KCOLUMN][i] == kIndex && association[VCOLUMN][i] == vIndex)
            {
                match++;
                nonempty++;
                if (nonempty == associationCount) return match;
            }
            if(association[KCOLUMN][i] != EMPTY) nonempty++;
            if (nonempty == associationCount) return match;
            
        }
        return match;
    }

    @Override
    public boolean addPair(K key, V value) throws DuplicatedPairException {
        int keyIndex, valueIndex;
        boolean isnew = false;
        if (key == null) throw new NullPointerException("Key cannot be null");
        if (value == null) throw new NullPointerException("Value cannot be null");
        keyIndex = this.keys.indexOf(key);
        if (keyIndex == -1)
        {
            isnew = true;
            this.keys.add(key);
            keyIndex = this.keys.indexOf(key);
        }
        valueIndex = this.values.indexOf(value);
        if (valueIndex == -1)
        {
            isnew = true;
            this.values.add(value);
            valueIndex = this.values.indexOf(value);
        }
        int pos = getFirstEmpty();
        if (isnew)
        {
            //simpler
            this.association[KCOLUMN][pos] = keyIndex;
            this.association[VCOLUMN][pos] = valueIndex;
            associationCount++;
            return true;
        }
        else
        {
            //checkitout!
            if (lookupAssociation(keyIndex, valueIndex))
            {
                if (this.behaviour == Behaviour.RETURN)
                {
                    return false;
                }
                else
                {
                    throw new DuplicatedPairException("Pair <" + keys.get(keyIndex) + " , " + values.get(valueIndex) + "> already exists");
                }
                
            }
            this.association[KCOLUMN][pos] = keyIndex;
            this.association[VCOLUMN][pos] = valueIndex;
            associationCount++;
            return true;
            
            
        }
        
    }
    
    
    //ABOUT REMOVAL METHODS:
    /*
     * In this implementation, K's and V's are not to be removed, they are set to null
     * The reason lies in the fact that would the object be actually removed from the list
     * the indexes would change, invalidating the association matrix.
     * A consistent removal should remap the whole association matrix to the new indexes
     * This kind of operation would be very time and resource expensive, but
     * could come in necessary if many "remove's" are issued during the instance lifetime.
     * */
    /*******************************************************
     * 
     * @param key
     * @return 
     */

    @Override
    public int removePairsByKey(K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        int kIndex = keys.indexOf(key);
        if (kIndex < 0) return 0;
        int match = 0;
        
        List<V> vals = getValues(key);
        Iterator<V> vi = vals.iterator();
        match = vals.size();
        while (vi.hasNext())
        {
            V v = vi.next();
            removePair(key, v);
        }
        return match;
    }

    @Override
    public int removePairsByValue(V value) {
        if (value == null) throw new NullPointerException("Value cannot be null");
        int vIndex = keys.indexOf(value);
        if (vIndex < 0) return 0;
        int match = 0;
        
        List<K> ks = getKeys(value);
        Iterator<K> ki = ks.iterator();
        match = ks.size();
        while (ki.hasNext())
        {
            K k = ki.next();
            removePair(k, value);
        }
        return match;
    }

    @Override
    public boolean removePair(K key, V value) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        if (value == null) throw new NullPointerException("Value cannot be null");
        int kIndex = keys.indexOf(key);
        if (kIndex < 0) return false;
        int vIndex = values.indexOf(value);
        if (vIndex < 0) return false;
        int nonempty = 0;
        for(int i = 0; i < this.association[KCOLUMN].length; i++)
        {
            if(association[KCOLUMN][i] == kIndex && association[VCOLUMN][i] == vIndex)
            {
                association[KCOLUMN][i] = EMPTY;
                association[VCOLUMN][i] = EMPTY;
                if(this.countKey(key) == 0) removeKey(key);
                if(this.countValue(value) == 0) removeValue(value);
                associationCount--;
                return true;
            }
            if(association[KCOLUMN][i] != EMPTY) nonempty++;
            if (nonempty == associationCount) return false;
        }
        return false;
    }

    @Override
    public long getCount() {
        return this.associationCount;
    }
    
     @Override
    public void clear() {
        this.keys = new ArrayList<>(INIT_SIZE);
        this.values = new ArrayList<>(INIT_SIZE);
        this.association = new int[this.COLUMNS][INIT_SIZE];
        initAssociations();
        this.actualKSize = INIT_SIZE;
        this.actualVSize = INIT_SIZE;
    }
    
    

    @Override
    public void upsize(double targetLoadFactor) {
        this.checkLoad(targetLoadFactor);
    }
    
    public String toString()
    {
        String tmp;
        tmp = "Object: " + this.getClass().getName() + "\n";
        tmp += "Hash: " + Integer.toHexString(this.hashCode()) + "\n";;
        tmp += "Target Load Factor: " + this.loadFactor + "\n";
        tmp += "Keys Load Factor: " + this.actualKLoad + "\n";
        tmp += "Values Load Factor: " + this.actualVLoad + "\n";
        tmp += "Associations Load Factor: " + this.actualALoad + "\n";
        tmp += "Associations: " + this.associationCount + "\n";
        tmp += "Keys Load Factor: " + this.actualKLoad + "\n";
        tmp += "Values Load Factor: " + this.actualVLoad + "\n";
        tmp += "Associations Load Factor: " + this.actualALoad + "\n";
        
        return tmp;
    }

   
    
}
