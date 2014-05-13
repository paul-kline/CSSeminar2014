/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrispaul2;


import TetrisFactory.HelpfulStuff.BoardConverter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.percederberg.tetris.Figure;
import net.percederberg.tetris.SquareBoard;

/**
 *
 * @author Paul
 */
public class PlacementDecider {

    public static final int[] BUTTKICKINWEIGHTS = {-3, -8, 43, 4, 14, 31, 47, 45};// {-6, -7, 47, 7, 14, 34, 50, 41};
    byte[][] board;
    ByteFig fig;
    ByteFig nextfig;
    Cenario best;
    int[] weights;
    int leftplace = -1;
    private boolean keepHistory = false;
    Cenario cenario;
    protected boolean sout = false;
    protected boolean madeTopChoices = false;

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
        if (sout) {
            System.out.println("This is the best board so far: ");
            best.soutBoard();
            System.out.println("This board should be empty and is myboard");
            this.soutBoard();
        }
        //one..
        for (int a = 1; a <= fig.getMaxOrientation(); a++) {
            fig.rotateToOrientation(a);
            //two...
            for (int b = 1; b <= nextfig.getMaxOrientation(); b++) {
                nextfig.rotateToOrientation(b);
                if (sout) {
                    System.out.println("piece b is this wide I think: " + nextfig.getpiecewidth());
                }
                //three....
                for (int i = 0; i <= board[0].length - fig.getpiecewidth(); i++) {
                    //four! four nested loops
                    for (int j = 0; j <= board[0].length - nextfig.getpiecewidth(); j++) {
                        cenario = new Cenario(board, fig, i, nextfig, j, weights);
                        // counter++;
                        if (sout) {
                            System.out.println("TESTBOARD:");
                            cenario.soutBoard();
                        }
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
            System.out.println("This is how many combos I tried: ");// + counter);
        }

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

    protected int getaxis(ByteFig myfig) {
        int type = myfig.type;
        int orientation = myfig.getOrientation();
        if (sout) {
            System.out.println("type: " + type + " orientation: " + orientation);
        }

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
        if (sout) {
            System.out.println("This is the best board so far: ");
            best.soutBoard();
            System.out.println("This board should be empty and is myboard");
            this.soutBoard();
        }
        //nextfig.rotateToOrientation(1);
        for (int a = 1; a <= fig.getMaxOrientation(); a++) {
            fig.rotateToOrientation(a);
            for (int b = 1; b <= nextfig.getMaxOrientation(); b++) {
                nextfig.rotateToOrientation(b);
                if (sout) {
                    System.out.println("piece b is this wide I think: " + nextfig.getpiecewidth());
                }
                for (int i = 0; i <= board[0].length - fig.getpiecewidth(); i++) {
                    for (int j = 0; j <= board[0].length - nextfig.getpiecewidth(); j++) {
                        cenario = new Cenario(board, fig, i, nextfig, j, weights);// w1, w2, w3, w4, w5, w6, w7, w8);

                        if (sout) {
                            System.out.println("TESTBOARD:");
                            cenario.soutBoard();
                        }
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
            //        System.out.println("This is how many combos I tried: " + counter);
        }
    }
    LinkedList<Cenario> TopChoices;
    boolean IstopChoicesSet = false;

    public LinkedList<Cenario> getTopChoices() {
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
                if (sout) {
                    System.out.println("piece b is this wide I think: " + nextfig.getpiecewidth());
                }
                for (int i = 0; i <= board[0].length - fig.getpiecewidth(); i++) {
                    for (int j = 0; j <= board[0].length - nextfig.getpiecewidth(); j++) {
                        cenario = new Cenario(board, fig, i, nextfig, j, weights);// w1, w2, w3, w4, w5, w6, w7, w8);                        
                        if (cenario.staticvalue > TopChoices.getLast().staticvalue) {
//                            if (topx.size() < x) {
//                                topx.add(cenario);
//                            } else {


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
//                            }
                        }
                    }
                }
            }
        }
        System.out.println("");
        //leftplace += getaxis(best.myfig);
        if (sout) {
            //        System.out.println("This is how many combos I tried: " + counter);
        }
        IstopChoicesSet = true;



        return TopChoices;

    }
    int[] countOfOccur;

    public boolean MakeADecisionAndAskNNIfNotSure() {
        boolean needhelp=shouldAskForHelp();
        if (needhelp) {
            best=NNChooseBest();
            
        }else{
            best=TopChoices.getFirst();            
        }
        leftplace= best.myLeftPlacement + getaxis(best.getMyfig());
       return needhelp;
    }

    public boolean shouldAskForHelp() {
        if (!IstopChoicesSet) {
            SetTopXChoices(5);
        }

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
        System.out.println("First choice shows up: " + CountOfOccurances[0]);

        for (int i = 1; i < CountOfOccurances.length; i++) {
            if (CountOfOccurances[0] <= CountOfOccurances[i]) {
                return true;
            }
        }
        return false;

    }

    public Cenario NNChooseBest() {
        int highestFreqIndex = 0;
        int highestFreq=0;
        for (int i = 0; i < countOfOccur.length; i++) {
            if (highestFreq < countOfOccur[i]) {
                highestFreq = countOfOccur[i];
                highestFreqIndex=i;
            }
        } 
        leftplace= TopChoices.get(highestFreqIndex).myLeftPlacement;
        return TopChoices.get(highestFreqIndex);
    }
}
