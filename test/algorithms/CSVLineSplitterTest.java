/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import rules.Rules;

/**
 *
 * @author marco
 */
public class CSVLineSplitterTest {
    
    public CSVLineSplitterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of split method, of class CSVLineSplitter.
     */
    @Test
    public void testSplit_4args() throws Exception {
        System.out.println("split");
        String toSplit = "001208575,03/14/2013,03/11/2013,1800,12,77th Street,1241,626,INTIMATE PARTNER - SIMPLE ASSAULT,0416 0446 1243 2000,30,F,W,502,\"MULTI-UNIT DWELLING (APARTMENT, DUPLEX, ETC)\",400,\"STRONG-ARM (HANDS, FIST, FEET OR BODILY FORCE)\",AO,Adult Other,626,,,,6300    BRYNHURST                    AV,,\"(33.9829, -118.3338)\"";
        String separator = ",";
        String delimiter = "\"";
        Rules.TextDelimitersSplit delimiterPolicy = Rules.TextDelimitersSplit.REMOVE_START_TRAIL_DELMITERS;
        //String[] expResult = null;
        String[] result = CSVLineSplitter.split(toSplit, separator, delimiter, delimiterPolicy);
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        System.out.println(java.util.Arrays.toString(result));
    }

    /**
     * Test of split method, of class CSVLineSplitter.
     */
    @Ignore
    @Test
    public void testSplit_String() throws Exception {
        System.out.println("split");
        String toSplit = "";
        String[] expResult = null;
        String[] result = CSVLineSplitter.split(toSplit);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
