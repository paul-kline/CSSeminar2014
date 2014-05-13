/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package propertrainingcaseconverter;





import TetrisHelpful.BoardConverter;
import TetrisHelpful.TetrisReader;
import TetrisHelpful.TetrisWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

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
        List<double[]> trainingRows =
                bc.ConvertFilesInDirectoryToNeurophFormat(folder, 10, 10, true);
        
        TetrisWriter tw = new TetrisWriter();
        tw.WriteTrainingRows(trainingRows, "./RESULTYAYYAYYAY.txt", ',');
               
        
    }
}
