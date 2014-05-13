/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bytefigtestering;

import NeuralThings.PaulNetworkFactory;
import TetrisHelpful.BoardConverter;
import TetrisHelpful.TetrisWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.percederberg.tetris.Figure;
import net.percederberg.tetris.Game;
import tetris3.APlacementRecord;
import tetris3.ByteFig;
import tetris3.Cenario;
import tetris3.PersonalizedPlacementDecider;
import tetris3.PlacementDecider;

/**
 *
 * @author Paul
 */
public class PaulGame1 extends PaulGame {

    private GAControllerObject myGACO;
    private String ID = "-1";
    private PaulNetworkFactory.NNCodes myNNCode;
//    public PaulGame1(int[] weightsp){//int w1p, int w2p, int w3p, int w4p, int w5p, int w6p, int w7p, int w8p){
//        myWeights = new int[weightsp.length];
//        for (int i = 0; i < weightsp.length; i++) {
//            myWeights[i] = weightsp[i];
//        }
//        initialize();
//    }

    public PaulGame1(Seed seed, GAControllerObject gaco, String id) {
        this(gaco);
        if (seed.getWeights() != null) {
            myWeights = MakeAppropriateWeights(seed);
        }
        ID = id;
        useNN = (seed.getNncode() == PaulNetworkFactory.NNCodes.NONE) ? false : true;
        myNNCode = seed.getNncode();

    }

    public PaulGame1(GAControllerObject gaco) {
        setMyGACO(gaco);
    }

    public PaulGame1(PaulGame1 pg1, String newID) {
        this.setMyGACO(pg1.getMyGACO());
        useNN = pg1.isUseNN();
        myNNCode = pg1.getMyNNCode();
        ID = newID;
        if (pg1.getMyWeights() != null) {
            myWeights = MakeAppropriateWeightsHelper(pg1.getMyWeights());
        }

    }

    public PaulGame1(int Score) {
        score = Score;
    }

    public int[] MakeAppropriateWeights(Seed seed) {
        int[] baseweights = seed.getWeights();
        return MakeAppropriateWeightsHelper(baseweights);
    }

    public int[] MakeAppropriateWeightsHelper(int[] baseweights) {
        int[] w = new int[baseweights.length];
        for (int i = 0; i < myGACO.getShouldMutateWeightsArray().length; i++) {
            if (myGACO.getShouldMutateWeightsArray()[i]) {
                int sign = (Math.random() <= .5) ? 1 : -1;
                int MAXDIFF = 5;
                w[i] = (int) (baseweights[i] + sign * Math.random() * MAXDIFF);
            } else {
                w[i] = baseweights[i];
            }
        }
        return w;
    }

    private void initialize() {
        score = 0;
        board = new byte[20][10];
        int rand = (int) (Math.random() * 7 + 1);
        fig1 = new ByteFig(rand);
        rand = (int) (Math.random() * 7 + 1);
        fig2 = new ByteFig(rand);
        PplacementDecider = new PersonalizedPlacementDecider(board, fig1, fig2, myWeights, useNN, myGACO.isCareIfHoleIsMadeByPlacement(),
                myGACO.isCareIfTopIsnotFrequent(),myGACO.getmyPercentHigherRequirement(), myNNCode); //w1,w2,w3,w4,w5,w6,w7, w8);
        PplacementDecider.SetmyCO(myGACO);
    }

    //@Override
    public void PlayGame() {
        initialize();
        makeFirstMove();
        playRestOfGame();

    }

    private void makeFirstMove() {
        if (useNN) {
            PplacementDecider.MakeADecisionAndAskNNIfNotSure();
        } else {
            PplacementDecider.MakeDecision();
        }
        board = PplacementDecider.getBest().getMyboard();
        score++;
        AveHeight = bc.CalculateAveHeight(board, AveHeight, score);
        //maintest.getBest().soutBoard();
    }

    private void playRestOfGame() {

        while (thirdDownIsZeros()) {
            makeAMove();
            score++;
            AveHeight = bc.CalculateAveHeight(board, AveHeight, score);

        }
    }

    private void makeAMove() {
        fig1 = null;
        fig1 = fig2;
        fig2 = null;
        int rand = (int) (Math.random() * 7 + 1);
        fig2 = new ByteFig(rand);

        PplacementDecider = null;
        PplacementDecider = new PersonalizedPlacementDecider(board, fig1, fig2, myWeights,
                useNN, myGACO.isCareIfHoleIsMadeByPlacement(), myGACO.isCareIfTopIsnotFrequent(),myGACO.getmyPercentHigherRequirement(), myNNCode);//w1,w2,w3,w4,w5,w6,w7, w8);
        //just added
        PplacementDecider.SetmyCO(myGACO);
        //end just added.
        
        
        if (useNN) {
            PplacementDecider.MakeADecisionAndAskNNIfNotSure();
        } else {
            PplacementDecider.MakeDecision();
        }

        board = PplacementDecider.getBest().getMyboard();
        if (keepHistory) {
            Cenario cenario = PplacementDecider.getBest();
            APlacementRecord rememberMe = new APlacementRecord(cenario.getMyfig().getType(),
                    cenario.DuplicateBoard(cenario.getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes()));
            AddBoardToHistory(rememberMe);
        }
        //maintest.getBest().soutBoard();
    }

    private boolean thirdDownIsZeros() {
        for (int i = 0; i < board[0].length; i++) {
            if (board[2][i] != 0) {
                return false;
            }
        }
        return true;
    }

//    @Override
    public String soutMe() {
        if (myWeights==null) {
            return "Score: "+ score;
        }
        String Me = "Score: " + score + " ** " + myWeights[0] + ", " + myWeights[1] + ", " + myWeights[2]
                + ", " + myWeights[3] + ", " + myWeights[4] + ", " + myWeights[5] + ", " + myWeights[6] + ", " + myWeights[7]
                + " Average Height: " + AveHeight;
        // System.out.println(Me);
        return Me;

    }

//    @Override
    public boolean isKeepHistory() {
        return keepHistory;
    }

//    @Override
    public void setKeepHistory(boolean keepHistory) {
        this.keepHistory = keepHistory;
        if (null == placementHistory) {
            placementHistory = new LinkedList<APlacementRecord>();
        }
    }

    private void AddBoardToHistory(APlacementRecord record) {
        if (score > 100) {
            placementHistory.removeFirst();
        }
        placementHistory.add(record);

    }

//    @Override
    public void PrintHistory() {
        Calendar cal = Calendar.getInstance();
        String filePrefix = "..\\..\\failsets\\FailSequence_";
        String timestamp = (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_"
                + cal.get(Calendar.YEAR)
                + "___" + cal.get(Calendar.HOUR_OF_DAY)
                + "_" + cal.get(Calendar.MINUTE) + "___" + cal.get(Calendar.SECOND);

        //String fileName= filePrefix + timestamp + ".txt";
        String fileName = ".\\resources\\ImaFailure_" + timestamp + ".txt";

        TetrisWriter tetrisWriter = new TetrisWriter();
        tetrisWriter.WritelHistoryToFile(placementHistory, fileName);

        //URL testURL= this.getClass().
    }

//    @Override
    public String GetStringWeights() {
        if (myWeights==null) {
            return null;
        }
        return "Weights: " + myWeights[0] + ", " + myWeights[1] + ", " + myWeights[2]
                + ", " + myWeights[3] + ", " + myWeights[4] + ", " + myWeights[5] + ", " + myWeights[6] + ", " + myWeights[7];

    }

    public GAControllerObject getMyGACO() {
        return myGACO;
    }

    public void setMyGACO(GAControllerObject myGACO) {

        this.myGACO = myGACO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public PaulNetworkFactory.NNCodes getMyNNCode() {
        return myNNCode;
    }

    public void setMyNNCode(PaulNetworkFactory.NNCodes myNNCode) {
        this.myNNCode = myNNCode;
    }
}
