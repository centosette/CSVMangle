/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author chiarand
 */
public class UniqueGenerator {
    
    private static UniqueGenerator inst = null;
    private long counter = Long.MAX_VALUE;
    
    private UniqueGenerator()
    {
        
    }
    public static UniqueGenerator getInstance()
    {
        if (inst == null)
        {
            inst = new UniqueGenerator();
        }
        return inst;
    }
    
    public synchronized String getUnique()
    {
        counter--;
        return Long.toHexString(counter);
        
    }
    
}
