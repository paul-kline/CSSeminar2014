/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testingstuff;


import TetrisFactory.HelpfulStuff.TetrisReader;
import java.io.File;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paul.kline
 */
public class TetrisReaderTest {
    
    public TetrisReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ChooseDirectoryAtThisPath method, of class TetrisReader.
     */
    @Test
    public void testChooseDirectoryAtThisPath() {
        System.out.println("ChooseDirectoryAtThisPath");
        String path = "";
        TetrisReader instance = new TetrisReader();
        File expResult = null;
        File result = instance.ChooseDirectoryAtThisPath(path);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ChooseFileAtThisPath method, of class TetrisReader.
     */
    @Test
    public void testChooseFileAtThisPath() {
        System.out.println("ChooseFileAtThisPath");
        String path = "";
        TetrisReader instance = new TetrisReader();
        File expResult = null;
        File result = instance.ChooseFileAtThisPath(path);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of READinEntireFile method, of class TetrisReader.
     */
    @Test
    public void testREADinEntireFile() {
        System.out.println("READinEntireFile");
        File file = null;
        TetrisReader instance = new TetrisReader();
        LinkedList expResult = null;
        LinkedList result = instance.READinEntireFile(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SplitScoreString method, of class TetrisReader.
     */
    @Test
    public void testSplitScoreString() {
        System.out.println("SplitScoreString");
        String str = "9083268,-2,-11,48,0,14,32,50,45";
        String splitter = ",";
        TetrisReader instance = new TetrisReader();
        int[] expResult = new int[]{9083268, -2, -11, 48, 0, 14, 32, 50, 45};
        int[] result = instance.SplitScoreStringToIntArray(str, splitter);
        String failurestr="";
        boolean success=true;
        for (int i = 0; i < result.length; i++) {
            if(expResult[i]!= result[i]){
                failurestr+=expResult[i] + "is not equal to "+ result[i] + "index:"+ i+"\n";
                success=false;
            }
            
        }
        assertTrue(success);
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        String Result="";
        for (int i = 0; i < result.length; i++) {
            Result+=result[i] + ",";
            
        }
        //fail("Failure: " + failurestr+Result);
    }
}