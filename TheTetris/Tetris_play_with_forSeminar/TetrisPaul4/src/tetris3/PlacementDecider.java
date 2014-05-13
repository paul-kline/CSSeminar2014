/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris3;

import NeuralThings.MomHighMomLittlePaul11;
import NeuralThings.MomHighValsPaulMomLittleNo3_4_or8;
import NeuralThings.NNHandClickedValues;
import NeuralThings.PaulNetworkFactory;
import TetrisHelpful.BoardConverter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import net.percederberg.tetris.Figure;
import net.percederberg.tetris.SquareBoard;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.flat.FlatNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.util.simple.EncogUtility;
import tetrispaul4.ControllerObject;

/**
 *
 * @author Paul
 */
public class PlacementDecider {

    public static final int[] BUTTKICKINWEIGHTS = 
    {-3, -8, 43, 4, 14, 31, 47, 45};// {-6, -7, 47, 7, 14, 34, 50, 41};
    protected byte[][] board;
    protected ByteFig fig;
    protected ByteFig nextfig;
    protected Cenario best;
    protected int[] weights;
    protected int leftplace = -1;
    protected boolean keepHistory = false;
    protected Cenario cenario;
    protected boolean sout = false;
    protected boolean madeTopChoices = false;
    private double[] NNRatings;
    public double[] getNNRatings(){return NNRatings;}

    public boolean isKeepHistory() {
        return keepHistory;
    }

    public void setKeepHistory(boolean keepHistory) {
        this.keepHistory = keepHistory;
    }

    public int getLeftplace() {
        return leftplace;
    }

    public Cenario getBest() {
        return best;
    }

    public byte[][] getBoard() {
        return board;
    }

    public PlacementDecider() {
        weights = BUTTKICKINWEIGHTS;
    }

    public PlacementDecider(SquareBoard squareBoard, Figure figp, Figure nextFigp) {
        fig = new ByteFig(figp);
        nextfig = new ByteFig(nextFigp);
        board = ConvertBoardToByte(squareBoard);

        //In this order:
        //zeroweight = -6;  //-3, -24, 22, -15, 6, 10, 17  32561
        //directzerosunderweight = -7; //  69995 ** -9, -36, 30, 47, 18, 24, 45, -4
        //linesclearedweight = 47;      //Score: 283230 ** -3, -8, 43, 4, 14, 31, 47, 45
        //flatratioweight = 7;          //378839 -11, -14, 4, -26, 21, 21, 44, 44
        //heightweight = 14;              //305618 ** -3, -17, 16, -6, 19, 18, 25, 35 
        //sidestouchingpiecesweight = 34;//Score: 1652546 ** -6, -7, 43, 4, 14, 32, 52, 48!!!!!
        //sidestouchingedgesweight = 50;  //1904732	-6	-7	47	7	14	34	50	41
        //directpieceunderweight = 41;
        weights = BUTTKICKINWEIGHTS;

        //MakeDecision();
    }

    public PlacementDecider(SquareBoard squareBoard, Figure figp, Figure nextFigp, int[] weightsp) {
        fig = new ByteFig(figp);
        nextfig = new ByteFig(nextFigp);

        BoardConverter bc = new BoardConverter();
        board = bc.ConvertToByte_specializeCurrentFigure(squareBoard, figp, 0);//ConvertBoardToByte(squareBoard);

        //In this order:
        //zeroweight = -6;  //-3, -24, 22, -15, 6, 10, 17  32561
        //directzerosunderweight = -7; //  69995 ** -9, -36, 30, 47, 18, 24, 45, -4
        //linesclearedweight = 47;      //Score: 283230 ** -3, -8, 43, 4, 14, 31, 47, 45
        //flatratioweight = 7;          //378839 -11, -14, 4, -26, 21, 21, 44, 44
        //heightweight = 14;              //305618 ** -3, -17, 16, -6, 19, 18, 25, 35 
        //sidestouchingpiecesweight = 34;//Score: 1652546 ** -6, -7, 43, 4, 14, 32, 52, 48!!!!!
        //sidestouchingedgesweight = 50;  //1904732	-6	-7	47	7	14	34	50	41
        //directpieceunderweight = 41;
        weights = weightsp;

        //MakeDecision();
    }

    public PlacementDecider(byte[][] boardp, ByteFig fig1p, ByteFig fig2p, int[] weightsp) { //int w1, int w2, int w3, int w4, int w5, int w6, int w7, int w8) {
        board = boardp;
        fig = fig1p;
        nextfig = fig2p;
        weights = weightsp;
        //DOITbitVersion(w1, w2, w3, w4, w5, w6, w7, w8);
        //DOITbitVersion(weights);
        //MakeDecision();
    }

    public void soutBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println("");
        }
    }

    public ByteFig getFig() {
        return fig;
    }

    public ByteFig getNextfig() {
        return nextfig;
    }

    public byte[][] ConvertBoardToByte(SquareBoard x) {
        byte[][] byteBoard = new byte[x.getBoardHeight()][x.getBoardWidth()];
        for (int r = 0; r < byteBoard.length; r++) {
            for (byte c = 0; c < byteBoard[0].length; c++) {
                byteBoard[r][c] = (byte) ((x.isSquareEmpty(c, r)) ? 0 : 1);
            }
        }

        return byteBoard;
    }

    public boolean MakeDecision() {
        // int counter = 0;

        fig.rotateToOrientation(1);
        nextfig.rotateToOrientation(1);

        best = new Cenario(board, fig, 0, nextfig, 0, weights);
        best.staticvalue = -999999999;
//        if (sout) {
//            System.out.println("This is the best board so far: ");
//            best.soutBoard();
//            System.out.println("This board should be empty and is myboard");
//            this.soutBoard();
//        }
        //one..
        for (int a = 1; a <= fig.getMaxOrientation(); a++) {
            fig.rotateToOrientation(a);
            //two...
            for (int b = 1; b <= nextfig.getMaxOrientation(); b++) {
                nextfig.rotateToOrientation(b);
//                if (sout) {
//                    System.out.println("piece b is this wide I think: " + nextfig.getpiecewidth());
//                }
                //three....
                for (int i = 0; i <= board[0].length - fig.getpiecewidth(); i++) {
                    //four! four nested loops
                    for (int j = 0; j <= board[0].length - nextfig.getpiecewidth(); j++) {
                        cenario = new Cenario(board, fig, i, nextfig, j, weights);
                        // counter++;
//                        if (sout) {
//                            System.out.println("TESTBOARD:");
//                            cenario.soutBoard();
//                        }
                        if (cenario.staticvalue > best.staticvalue) {
                            best = null;
                            best = cenario;
                            leftplace = i;
                            cenario = null;
                        }
                    }
                }
            }
        }
        leftplace += getaxis(best.myfig);
//        if (sout) {
//            System.out.println("This is how many combos I tried: ");// + counter);
//        }

//        if (shouldAskForHelp()) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(PlacementDecider.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        // System.out.println((shouldAskForHelp()));
        return true;//shouldAskForHelp();
    }

    public int getaxis(ByteFig myfig) {
        int type = myfig.type;
        int orientation = myfig.getOrientation();
//        if (sout) {
//            System.out.println("type: " + type + " orientation: " + orientation);
//        }

        switch (type) {

            case 1://BOXMAN
                return 1;

            case 2://LINE
                switch (orientation) {
                    case 1:
                        return 0;
                    case 2:
                        return 2;
                    default:

                        throw new AssertionError();
                }

            case 3:  //S FIGURE
                return 1;

            case 4://Z FIGURE

                return 1;
            case 5:  //RIGHT ANGLE FIGURE
                switch (orientation) {
                    case 1:

                        return 1;
                    case 2:
                        return 1;
                    case 3:
                        return 0;
                    case 4:
                        return 1;
                    default:
                        throw new AssertionError();
                }

            case 6: //LEFT ANGLE FIGURE
                switch (orientation) {
                    case 1:

                        return 1;
                    case 2:
                        return 1;
                    case 3:
                        return 0;
                    case 4:
                        return 1;
                    default:
                        throw new AssertionError();
                }
            case 7: //TRIANGLE FIGURE

                switch (orientation) {
                    case 1:

                        return 1;
                    case 2:
                        return 1;
                    case 3:
                        return 0;
                    case 4:
                        return 1;
                    default:
                        throw new AssertionError();
                }

            default:
                throw new IllegalArgumentException("No figure constant: "
                        + type);
        }

    }

    private void DOITbitVersion(int[] weights) {//int w1, int w2, int w3, int w4, int w5, int w6, int w7, int w8) {
        fig.rotateToOrientation(1);
        nextfig.rotateToOrientation(1);

        best = new Cenario(board, fig, 0, nextfig, 0, weights);// w1, w2, w3, w4, w5, w6, w7, w8);
        best.staticvalue = -999999999;
//        if (sout) {
//            System.out.println("This is the best board so far: ");
//            best.soutBoard();
//            System.out.println("This board should be empty and is myboard");
//            this.soutBoard();
//        }
        //nextfig.rotateToOrientation(1);
        for (int a = 1; a <= fig.getMaxOrientation(); a++) {
            fig.rotateToOrientation(a);
            for (int b = 1; b <= nextfig.getMaxOrientation(); b++) {
                nextfig.rotateToOrientation(b);
//                if (sout) {
//                    System.out.println("piece b is this wide I think: " + nextfig.getpiecewidth());
//                }
                for (int i = 0; i <= board[0].length - fig.getpiecewidth(); i++) {
                    for (int j = 0; j <= board[0].length - nextfig.getpiecewidth(); j++) {
                        cenario = new Cenario(board, fig, i, nextfig, j, weights);// w1, w2, w3, w4, w5, w6, w7, w8);

//                        if (sout) {
//                            System.out.println("TESTBOARD:");
//                            cenario.soutBoard();
//                        }
                        if (cenario.staticvalue > best.staticvalue) {
                            best = null;
                            best = cenario;
                            leftplace = i;
                            cenario = null;
                        }
                    }
                }
            }
        }
        leftplace += getaxis(best.myfig);
        if (sout) {
            //System.out.println("This is how many combos I tried: " + counter);
        }
    }
    LinkedList<Cenario> TopChoices;
    boolean IstopChoicesSet = false;

    public LinkedList<Cenario> getTopChoices() {
        if (IstopChoicesSet==false) {
            SetTopXChoices(myCO.getTopChoicesNumber());
        }
        return TopChoices;
    }

    public LinkedList<Cenario> SetTopXChoices(int x) {
        TopChoices = new LinkedList<Cenario>();
        //Integer

        fig.rotateToOrientation(1);
        int cenariosPresent = 0;
        nextfig.rotateToOrientation(1);

        best = new Cenario(board, fig, 0, nextfig, 0, weights);// w1, w2, w3, w4, w5, w6, w7, w8);
        best.staticvalue = -999999999;
        TopChoices.add(best);
        //nextfig.rotateToOrientation(1);
        for (int a = 1; a <= fig.getMaxOrientation(); a++) {
            fig.rotateToOrientation(a);
            for (int b = 1; b <= nextfig.getMaxOrientation(); b++) {
                nextfig.rotateToOrientation(b);
//                if (sout) {
//                    System.out.println("piece b is this wide I think: " + nextfig.getpiecewidth());
//                }
                for (int i = 0; i <= board[0].length - fig.getpiecewidth(); i++) {
                    for (int j = 0; j <= board[0].length - nextfig.getpiecewidth(); j++) {
                        cenario = new Cenario(board, fig, i, nextfig, j, weights);// w1, w2, w3, w4, w5, w6, w7, w8);                        
                        if (cenario.staticvalue > TopChoices.getLast().staticvalue) {
                            addAppropriately(x);
                        }
                    }
                }
            }
        }
        //System.out.println("");
        //leftplace += getaxis(best.myfig);
//        if (sout) {
//            //        System.out.println("This is how many combos I tried: " + counter);
//        }
        IstopChoicesSet = true;

        return TopChoices;

    }

    public void addAppropriately(int x) {

        int index = TopChoices.size();
        Cenario cen = TopChoices.getLast();
        //                           System.out.println("Index of getLast: " + TopChoices.indexOf(TopChoices.getLast()));
        if (cenario.staticvalue > cen.staticvalue) {
            while (cenario.staticvalue > cen.staticvalue && index >= 0) {
                cen = (--index > -1) ? TopChoices.get(index) : cen;
            }
            TopChoices.add(index + 1, cenario);
        }
        while (TopChoices.size() > x) {
            TopChoices.removeLast();
        }
    }
    int[] countOfOccur;

    public boolean MakeADecisionAndAskNNIfNotSure() {
        boolean needhelp = shouldAskForHelp();
        if (needhelp) {
            best = NNChooseBest();

        } else {
            best = TopChoices.getFirst();

        }
        leftplace = best.myLeftPlacement + getaxis(best.getMyfig());
        return needhelp;
    }

    public boolean shouldAskForHelp() {
        
        if (!IstopChoicesSet) {
            SetTopXChoices(((myCO.getTopChoicesNumber()==-1)? 5: myCO.getTopChoicesNumber()) );
        }
        
        if (myCO.getmyPercentHigherRequirement()!=Float.NaN) {
            if (TopChoices.get(1)!=null &&
                    (double)(TopChoices.get(0).getStaticvalue()-(double)TopChoices.get(1).getStaticvalue())/
                    (double)TopChoices.get(0).getStaticvalue()>=myCO.getmyPercentHigherRequirement()/100.0){
                //myCO.getMyGame().handlePause();
                //JOptionPane.showMessageDialog(null, "I ignored!!!!! Are you proud?");
                        return false;
            }
        }
        
//        System.out.println("care if top is not frequent: "+myCO.isCareIfTopIsnotFrequent());
        if (myCO.isCareIfTopIsnotFrequent()) {
            BoardConverter bc = new BoardConverter();
            int[] CountOfOccurances = new int[TopChoices.size()];
            for (int i = 0; i < CountOfOccurances.length; i++) {
                for (int j = 0; j < CountOfOccurances.length; j++) {
                    if (i == j) {
                        //  continue;
                    }
                    if (bc.AreEquivelent(TopChoices.get(i).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes(),
                            TopChoices.get(j).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes())) {
                        CountOfOccurances[i]++;
                    }
                }
            }
            countOfOccur = CountOfOccurances;
            // System.out.println("First choice shows up: " + CountOfOccurances[0]);
            for (int i = 1; i < CountOfOccurances.length; i++) {
                if (CountOfOccurances[0] <= CountOfOccurances[i]) {

                    //if the board is the same as the first, ignore that they have the same count.
                    if (bc.AreEquivelent(TopChoices.get(0).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes(),
                            TopChoices.get(i).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes())) {
                        continue;
                    }
                    return true;
                }
            }
        }

//        System.out.println("Care if hole is made: " +myCO.isCareIfHoleIsMadeByPlacement());
        if (myCO.isCareIfHoleIsMadeByPlacement() && HoleIsMade()) {
            return true;
        }
        return false;
    }

    public boolean HoleIsMade() {
        //if you are making a hole, maybe you should think about what you are doing.
      //  System.out.println("myweights null: "+ (myCO==null));
        Cenario cen = new Cenario(TopChoices.get(0).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes(), myCO.getWeights(), fig.type);
        if (cen.directzerosunder > 0) {
            //System.out.println("here is my count: " +cen.directzerosunder);
            return true;
        }
        return false;
    }

    public Cenario NNChooseBest() {
        // FlatNetwork x = NeuralNetwork.createNetwork();
        BasicNetwork x = CreateNeuralNet();

        BoardConverter bc = new BoardConverter();
        NNRatings = new double[TopChoices.size()];

        for (int i = 0; i < TopChoices.size(); i++) {

            // double[] input = bc.ConvertBoardToInputForNet(board, 10, 10);
            double[] input = GetAppropriateInputs(i, myCO.getNNCode());
            double[] output = new double[1];
            x.compute(input, output);
            NNRatings[i] = output[0];
//          System.out.println("output is of size: " + output.length);
//          System.out.println("here is my output for the top choice" + i+": " + output[0]);
        }
        int hIndex = -1;
        double hValue = -1.0;
//        System.out.println("takefirstpos: "+ myCO.getTakeFirstPositive());
        if (myCO.getTakeFirstPositive()) {
            for (int i = 0; i < NNRatings.length; i++) {
                if (NNRatings[i]>0) {
                    return TopChoices.get(i);
                }              
            }
        }
        
        for (int i = 0; i < NNRatings.length; i++) {
            if (NNRatings[i] > hValue) {
                hValue = NNRatings[i];
                hIndex = i;
            }

        }
        hIndex = (hIndex < 0) ? 0 : hIndex;
        return TopChoices.get(hIndex);

    }

    public boolean GetIsNNChoiceSameAsG_Choice() {
        BoardConverter bc = new BoardConverter();

        return bc.AreEquivelent(best.getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes(),
                TopChoices.getFirst().getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes());

    }
   private ControllerObject myCO = null;

    public void SetmyCO(ControllerObject co) {
        myCO = co;
    }

    public boolean ismyCONull() {
        return (null == myCO);
    }

    protected BasicNetwork CreateNeuralNet() {
        if (ismyCONull()) {
            JOptionPane.showMessageDialog(null, "failure to set CO for placement Decider.");
        }
        return new PaulNetworkFactory().AssembleNetwork(myCO.getNNCode());
    }

    protected double[] GetAppropriateInputs(int i, PaulNetworkFactory.NNCodes nnCode) {
        double[] input = null;
        switch (nnCode) {
            case NN2c:
            case NNSucky100Inputsc:
                byte[][] b = TopChoices.get(i).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes();
                BoardConverter bc = new BoardConverter();
                input = bc.ConvertBoardToInputForNet(b, 10, 10);
                break;
            case NNMomHighMomLittlePaul11:
                Cenario cen1 = new Cenario(TopChoices.get(i).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes(),
                        myCO.getWeights(), fig.type);
                
                //you forgot that this net only needs 6 inputs, 
                //remove width/height weight4 index: 3
                //remove floor or piece right under weight 8 index: 7
                double[] fewerInputs= new double[cen1.Getvalues().length-2];
                int index=0;
                for (int j = 0; j < cen1.Getvalues().length; j++) {
                    if (j==3 || j==7) {
                        continue;
                    }
                    fewerInputs[index++]= cen1.Getvalues()[j];

                 }
                input= MomHighMomLittlePaul11.NORMALIZE(fewerInputs);
                break;
            
            case NNMomHighMomLittlePaulNo_3_3_or8:
                 Cenario cen2 = new Cenario(TopChoices.get(i).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes(),
                        myCO.getWeights(), fig.type);
                
                //you forgot that this net only needs 6 inputs, 
                //remove width/height weight4 index: 3
                // remove lines cleared count weight3 index: 2
                //remove floor or piece right under weight 8 index: 7
                double[] fewerInputs2= new double[cen2.Getvalues().length-3];
                int index2=0;
                for (int j = 0; j < cen2.Getvalues().length; j++) {
                    if (j==3 || j==7 || j==2) {
                        continue;
                    }
                    fewerInputs2[index2++]= cen2.Getvalues()[j];

                 }
                input= MomHighValsPaulMomLittleNo3_4_or8.NORMALIZE(fewerInputs2);
                break;
                               
            default:
                Cenario cen = new Cenario(TopChoices.get(i).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes(),
                        myCO.getWeights(), fig.type);
                input = cen.Getvalues();
        }
        return input;
    }
}