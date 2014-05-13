
package propertrainingcaseconverter2;

import TetrisHelpful.BoardConverter;
import TetrisHelpful.TetrisReader;
import TetrisHelpful.TetrisWriter;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import tetris3.APlacementRecord;

/**
 *
 * @author Paul
 */
public class ProperTrainingCaseConverter2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TetrisReader tr = new TetrisReader();
        File folder = tr.ChooseDirectoryAtThisPath("./");
        System.out.println(folder);

        // = new File(".//PlaceFilesToConvertHere");
        if (!folder.exists()) {
            JOptionPane.showMessageDialog(null, "Oops! Nothing converted!");
        }
        File[] listOfFiles = folder.listFiles();
        BoardConverter bc = new BoardConverter();
        LinkedList<APlacementRecord> placements = 
                bc.GetAllPlacementRecordsInDirectory(folder);

        System.out.println(placements.getFirst().
                GetStringBoard(placements.getFirst().GetBoard()));
        List<double[]> trainingRows = bc.GetValuesAndIsGood(placements);

        TetrisWriter tw = new TetrisWriter();
        tw.WriteTrainingRows(trainingRows, "./RESULTFromPTC2.txt", ',');
    }

}
