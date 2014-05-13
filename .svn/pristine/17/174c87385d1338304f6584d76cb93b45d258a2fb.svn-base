/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package propertrainingcaseconverter;

import TetrisFactory.HelpfulStuff.BoardConverter;
import TetrisFactory.HelpfulStuff.TetrisReader;
import TetrisFactory.HelpfulStuff.TetrisWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import tetrispaul2.APlacementRecord;

/**
 *
 * @author paul.kline
 */
public class ProperTrainingCaseConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TetrisReader tr = new TetrisReader();
         File folder= tr.ChooseDirectoryAtThisPath("./");
        System.out.println(folder);
        
        
        
       // = new File(".//PlaceFilesToConvertHere");
        if (!folder.exists()) {
            JOptionPane.showMessageDialog(null, "Oops! Nothing converted!");
        }
        File[] listOfFiles = folder.listFiles();       
        BoardConverter bc = new BoardConverter();
        List<float[]> trainingRows =bc.ConvertFilesInDirectoryToNeurophFormat(folder, 10, 10, true);
        
        TetrisWriter tw = new TetrisWriter();
        tw.WriteTrainingRows(trainingRows, "./RESULTYAYYAYYAY.txt", ',');
//        LinkedList<Boolean> isgoods= new LinkedList<Boolean>();      
//        LinkedList<String> filenames= new LinkedList<String>();       
//        LinkedList<byte[][]>[] ArrayofBoards = null;
//        LinkedList<Integer>[] ArrayofPieces = null;
//    
//        
//
//        TetrisWriter tw = new TetrisWriter();
//        TetrisReader tr = new TetrisReader();
//        for (int i=0 ,curr=0; i<listOfFiles.length; i++) {
//            File file = listOfFiles[i];
//            boolean isgood;
//            String name = file.getName();
//            if (name.contains("good") || name.contains("GOOD") || name.contains("Good")) {
//                isgood = true;
//            } else if (name.contains("bad") || name.contains("BAD") || name.contains("Bad") || name.contains("EVIL") || name.contains("evil") || name.contains("Evil")) {
//                isgood = false;
//            } else {
//                continue;
//            }
//            ArrayofBoards[curr]= new LinkedList<>();
//            ArrayofPieces[curr]= new LinkedList<>();
//            isgoods.add(isgood);
//            filenames.add(name);
//            tr.READinEntireFile(ArrayofBoards[curr], ArrayofPieces[curr], file);
//            curr++;            
//        }
        
        //now to print them to file!
        
        
        
    }
}
