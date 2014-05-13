/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bytefigtestering;

import GUIs.GATrainingSettings;
import GUIs.Progress;
import TetrisHelpful.TetrisWriter;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import net.percederberg.tetris.Figure;
import net.percederberg.tetris.Game;
import net.percederberg.tetris.Main;
import tetrispaul4.ControllerObject;

/**
 *
 * @author Paul
 */
public class ByteFigTestering {

    /**
     * If true, this boolean will use the smarter decision maker to ask the NN
     * instead of the "classic" MakeDecision()"
     */



    public Progress getMyProg() {
        return myProg;
    }

    public void setMyProg(Progress myProg) {
        this.myProg = myProg;
    }

    private GATrainingSettings myGATrainingS;
   
    

    public void setNumberOfGamesToPlay(int numberOfGamesToPlay) {
        numberOfGamesToPlay = numberOfGamesToPlay;
    }

    public int getGamesCompleteSoFar() {
        return gamesCompleteSoFar;
    }

    public void setGamesCompleteSoFar(int gamesCompleteSoFar) {
        gamesCompleteSoFar = gamesCompleteSoFar;
    }

    public void Begin(String input, GAControllerObject gaco) throws NumberFormatException {
//        int THISMANYGAMES = 0;
//
//        boolean SHOULDCLONE = input.contains("c");
//        boolean USETESTWEIGHTS = input.contains("t");
//        char[] in = input.toCharArray();
//        for (int i = 0; i < in.length; i++) {
//            if (Character.isDigit(in[i])) {
//                THISMANYGAMES = Integer.parseInt(input.substring(i));
//                numberOfGamesToPlay = THISMANYGAMES;
//                break;
//            }
//        }
//        System.out.println("shouldclone: " + SHOULDCLONE + " usetestweights: " + USETESTWEIGHTS);
//        System.out.println(THISMANYGAMES);
//        int[] someweights = (USETESTWEIGHTS) ? new int[]{0, 0, 0, 0, 0, 0, 0, 0} : new int[]{-3, -8, 43, 4, 14, 31, 47, 45}; //{-320, -778, 4403, 324, 1316,3170,4743,4495}; {-2,-11,-48,0,14,32,50,45};//
//        someweights = (USETESTWEIGHTS) ? someweights : gaco.weights;
//
//        PaulGame pg;
//        TetrisWriter tw = new TetrisWriter();
//        String Writehere = "Z:\\TetrisGames\\"; //"C:\\Users\\Paul\\Desktop\\Results Here please\\";
//        String StartingLoc = Writehere;
//        Writehere += (USETESTWEIGHTS) ? "TEST" : "";
//        Writehere += (SHOULDCLONE) ? "UNITEDCloneScores" : "UNITEDExperiemtnalScores";
//        String stamp = tw.GetTimeStamp(true, true, true, true, true, true);
//        Writehere += stamp;
//        Writehere += ".txt";
//        //tw.GetTimeStamp(true, true, true, true, true, true)
//       
//
//        myProg = new Progress();
//        myProg.setVisible(true);
//        myProg.setTitle("ByteFigTestering: " + input);
//        myProg.displayWeights(someweights);
//        myProg.displayUsingNN(myGATrainingS.getMyGACO().getUseNN());
//        boolean success = false;
//        if (input.contains("l")) {
//            pg = new PaulGame2(someweights);
//
//        } else {
//            pg = new PaulGame1(someweights);
//
//        }
//        success = (SHOULDCLONE) ? PlayThisGameALotOfTimes(THISMANYGAMES, pg, Writehere)
//                : PlayThisManySlightMutationsToOneGame(THISMANYGAMES, pg, Writehere);
//        if (success) {
//            tw.AppendToFile("I finished." + tw.GetTimeStamp(true, true, true, true, true, true), StartingLoc + "WorkersOutput" + ".txt");
//        }
//        //  PlayThisGameALotOfTimes(THISMANY, pg, output);
//        System.out.println(success);
//        try {
//            Thread.sleep(5000);
//            System.exit(1);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Progress.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void SetControllerObjectIdeas(GAControllerObject geco) {
        if (null != geco.getUseNN()) {
//            useNN = geco.getUseNN();
            System.out.println("Okay, show I use nn?" + geco.getUseNN());
        }

    }
    Progress myProg;
   // GATrainingSettings gaTrainView;
     public int numberOfGamesToPlay = 0;
     int gamesCompleteSoFar = 0;

    /**
     * @param args the command line arguments
     */
   

//    public static boolean PlayThisManySlightMutationsToOneGame(int thismany, PaulGame pg, String outputfile) {
//        AIPlayer controller = new AIPlayer(pg, thismany);
//        System.out.println("pg weights are:" + pg.GetStringWeights());
//        controller.createEachChildNextGenBasedOn(pg);
//        controller.GivePopulationLife(outputfile);
//        return true;
//    }

//    private static boolean PlayThisGameALotOfTimes(int thismany, PaulGame pg, String outputfile) {
//        AIPlayer controller = new AIPlayer(pg, thismany);
//        controller.createNextGenOfClones(pg);
//        controller.GivePopulationLife(outputfile);
//        return true;
//    }


    void NotifyGameCompleted() {
        gamesCompleteSoFar++;
//        System.out.println(gamesCompleteSoFar);
//        System.out.println(numberOfGamesToPlay);
        int newVal = (int) (((double) gamesCompleteSoFar / (double) numberOfGamesToPlay) * 100.0);
//        System.out.println("SetNewVal!!" + newVal);
        myProg.setProgressTo(newVal);
        if (gamesCompleteSoFar == numberOfGamesToPlay) {
            myProg.setCompleted();
        }
        myProg.repaint();
    }

    public GATrainingSettings getMyGATrainingS() {
        return myGATrainingS;
    }

    public void setMyGATrainingS(GATrainingSettings myGATrainingS) {
        this.myGATrainingS = myGATrainingS;
    }
}
