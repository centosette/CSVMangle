/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;

import java.util.List;

/**
 * An associative collection models a set of ordered couples, say (k, v).
 * Multiple couples of type (k, *) are allowed, but there cannot be two
 * identical values for the same key.
 * @author marco
 */
public interface AssociativeCollection <K, V>{
    public List<V> getValues(K key);
    public List<K> getKeys(V value);
    public boolean checkKey(K key);
    public int countKey(K key);
    public boolean checkValue(V value);
    public int countValue(V value);
    public boolean checkPair(K key, V value);
    public int countPair(K key, V value);
    public boolean addPair(K key, V value) throws DuplicatedPairException, NullPointerException;
    public int removePairsByKey(K key);
    public int removePairsByValue(V value);
    public boolean removePair(K key, V value);
    public long getCount();
    public void clear();
    public void upsize(double targetLoadFactor);
    public void checkLoadFactor();
    
}
