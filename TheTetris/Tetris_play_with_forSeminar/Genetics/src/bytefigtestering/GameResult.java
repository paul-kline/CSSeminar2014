/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bytefigtestering;

/**
 *
 * @author Paul
 */
public class GameResult {
    private int score=0;
    private byte[] weights;
    
    public GameResult(int howmany, int scorep, byte[] weightsp ){
        score=scorep;
        weights=new byte[howmany];
        weights=weightsp;
    }
    
}
