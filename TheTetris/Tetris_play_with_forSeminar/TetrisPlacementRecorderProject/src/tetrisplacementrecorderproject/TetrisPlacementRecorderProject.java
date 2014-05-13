/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisplacementrecorderproject;


import bytefigtestering.PaulGame;
import bytefigtestering.PaulGame1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
//import tetrispaul2.PlacementDecider;

/**
 *
 * @author paul.kline
 */
public class TetrisPlacementRecorderProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int[] modifiedweights = {-6, -7, 47, 7, 14, 34, 50, 41}; //{-6, -7, 47, 7, 14, 34, 50, 41};

        for (int i = 0; i < 100; i++) {
            PaulGame testToRecordMyHistory = new PaulGame1(modifiedweights);//PlacementDecider.BUTTKICKINWEIGHTS);
            testToRecordMyHistory.setKeepHistory(true);
            testToRecordMyHistory.PlayGame();
            testToRecordMyHistory.PrintHistory();
            PrintHistory(testToRecordMyHistory);
        }


    }

    private static void PrintHistory(PaulGame testToRecordMyHistory) {
        testToRecordMyHistory.PrintHistory();
    }
}
