/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bytefigtestering;

/**
 *
 * @author Paul
 */
public interface PaulGameInter {

    String GetStringWeights();

    void PlayGame();

    void PrintHistory();

    boolean isKeepHistory();

    void setKeepHistory(boolean keepHistory);

    String soutMe();
    
}
