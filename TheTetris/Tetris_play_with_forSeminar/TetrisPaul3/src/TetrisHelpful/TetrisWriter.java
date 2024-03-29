/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisHelpful;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tetris3.APlacementRecord;

/**
 *
 * @author paul.kline
 */
public class TetrisWriter {

    public boolean WriteListAsTrainingRows(LinkedList<APlacementRecord> placementHistory, String fileName, boolean IsGood, char separator) {
        int output = (IsGood) ? 1 : 0;

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(fileName);
            byte[][] board;


            for (APlacementRecord thingy : placementHistory) {
                board = thingy.GetBoard();
                //int size = 2 * board.length * board[0].length + 1;  
                StringBuilder sb = new StringBuilder(201);
                for (int r = 0; r < board.length; r++) {
                    for (int c = 0; c < board[0].length; c++) {
                        sb.append(board[r][c]);
                        sb.append(separator);
                    }
                }
                sb.append(output);


                writer.println(sb.toString());
                System.out.println(sb.toString());
                //jsut in case, why not.
                sb = null;
                board = null;
            }

        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND EXCEPTION. You suck.");
            return false;
            //Logger.getLogger(PaulGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        writer.close();

        return true;




    }

    public boolean WriteTrainingRows(List<double[]> trainingrows, String fileName, char separator) {


        PrintWriter writer = null;

        try {
            writer = new PrintWriter(fileName);
            //byte[][] board;



            for (double[] fs : trainingrows) {
                StringBuilder sb = new StringBuilder(201);
                for (int i = 0; i < fs.length; i++) {
                    sb.append(fs[i]);
                    sb.append(separator);
                }
                //get rid of that last seperator
                sb.deleteCharAt(sb.length() - 1);

                writer.println(sb.toString());
                System.out.println(sb.toString());
                //jsut in case, why not.
                sb = null;

            }

        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND EXCEPTION. You suck.");
            return false;
            //Logger.getLogger(PaulGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        writer.close();

        return true;




    }

    public boolean WritelHistoryToFile(LinkedList<APlacementRecord> placementHistory, String fileName) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);

            for (APlacementRecord element : placementHistory) {
                String outp = element.toString();


                writer.println(outp);
                System.out.println(outp);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND EXCEPTION. You suck.");
            return false;
            //Logger.getLogger(PaulGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        writer.close();

        return true;
    }

    public String GetStringBoard(byte[][] board) {
        StringBuilder sb = new StringBuilder(board.length * board[0].length);
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                sb.append(board[r][c]);
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public String GetTimeStamp(boolean month, boolean day, boolean year, boolean hour, boolean minute, boolean second) {
        Calendar cal = Calendar.getInstance();
        String returnStr = "";
        if (month) {
            returnStr += (cal.get(Calendar.MONTH) + 1) + "_";
        }
        if (day) {
            returnStr += (cal.get(Calendar.DAY_OF_MONTH) + 1) + "_";
        }
        if (year) {
            returnStr += (cal.get(Calendar.YEAR) + 1) + "____";
        }
        if (hour) {
            returnStr += (cal.get(Calendar.HOUR_OF_DAY) + 1) + "_";
        }
        if (minute) {
            returnStr += (cal.get(Calendar.MINUTE) + 1) + "_";
        }
        if (second) {
            returnStr += (cal.get(Calendar.SECOND) + 1) + "_";
        }

        return returnStr;
        //(cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_"
//                + cal.get(Calendar.YEAR)
//                + "___" + cal.get(Calendar.HOUR_OF_DAY)
//                + "_" + cal.get(Calendar.MINUTE) + "___" + cal.get(Calendar.SECOND);
    }

    public boolean AppendToFile(String toAppend, String location) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(location, true)))) {
            out.println(toAppend);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }



        return true;
    }
}
