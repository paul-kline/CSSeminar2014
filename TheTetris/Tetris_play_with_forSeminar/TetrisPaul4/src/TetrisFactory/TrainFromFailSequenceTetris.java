/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisFactory;

import TemplateMethod.AbstractGame;
import TetrisHelpful.BoardConverter;
import TetrisHelpful.TetrisReader;
import TetrisHelpful.TetrisWriter;
import java.awt.Color;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.percederberg.tetris.Figure;
import net.percederberg.tetris.Game;
import net.percederberg.tetris.SquareBoard;
import tetris3.APlacementRecord;
import tetris3.ByteFig;
import tetris3.Cenario;
import tetris3.PlacementDecider;
import tetrispaul4.ControllerObject;
import tetrispaul4.WeightSelector;

/**
 *
 * @author Paul
 */
public class TrainFromFailSequenceTetris extends AbstractGame {

    protected boolean stillNeedName = true;
    LinkedList<Integer> useThesePiecesFirst;

    public LinkedList<APlacementRecord> evil;
    public LinkedList<APlacementRecord> good;
    byte[][] currentBoard;
    public boolean useFailSet = true;
    protected boolean camefromOops = false;
    protected boolean stillNeedtoLoadFails = true;
    LinkedList<Integer> FailCaseIndexsofBoardsSequence;
    File FailFile;

    public TrainFromFailSequenceTetris() {
        futureBoards = new LinkedList<byte[][]>();
        futurePieces = new LinkedList<Integer>();
        playedBoards = new LinkedList<byte[][]>();
        playedPieces = new LinkedList<Integer>();
        evil = new LinkedList<APlacementRecord>();
        good = new LinkedList<APlacementRecord>();
        useThesePiecesFirst = new LinkedList<Integer>();
        FailCaseIndexsofBoardsSequence = new LinkedList<>();
    }
    protected String name = "";

    public TrainFromFailSequenceTetris(File get) {
        this();
        FailFile = get;
        LoadInEntireFailSequenceHelper(get);
        stillNeedtoLoadFails = false;
    }

    public TrainFromFailSequenceTetris(File g, ControllerObject co) {
        this(g);
        myCO = co;
        System.out.println("myCoAddress: " + myCO);
    }

    public TrainFromFailSequenceTetris(File g, ControllerObject co, String playerName) {
        this(g,co);
        myCO.setPlayerName(playerName);
        myCO.setNeedPlayerName(false);
    }

    @Override
    public void handleStart() {
        if (myCO.getStillNeedPlayerName()) {
            name = JOptionPane.showInputDialog(null, "Who are you?");
        }else{
            name = myCO.GetPlayerName();
        }
        automatic = false;
        evil.clear();
        good.clear();
        // Reset components
        board.setMessage(null);
        board.clear();
        previewBoard.clear();
        handleLevelModification();
        handleScoreModification();
        component.button.setLabel("Pause");

        if (stillNeedtoLoadFails || !useFailSet) {
            futureBoards.clear();
            futurePieces.clear();

        }
        if (stillNeedtoLoadFails) {
            LoadInEntireFailSequence();
        }
        if (useFailSet) {
            SetBoardToThisBoard(futureBoards.peek());
            currentBoard = futureBoards.peek();
            nextFigure = new Figure(1);
        } else {
            nextFigure = randomFigure();
        }


        // Reset score and figures
        level = 1;
        score = 0;
        figure = null;

        // int thepiece = futurePieces.pop();
//        playedPieces.add(thepiece);
//        nextFigure = figures[thepiece - 1];





        //nextFigure.rotateRandom();
        nextRotation = nextFigure.getRotation();



        // Start game thread
        thread.reset();
    }

    @Override
    public synchronized void handleTimer() {
        if (figure == null) {
            handleFigureStart();
        } else if (figure.hasLanded()) {
            handleFigureLanded();
        } else {
            if (!useFailSet) {
                figure.moveDown();
            }

        }
    }

    @Override
    public synchronized void handleKeyEvent(KeyEvent e) {

        // Handle start, pause and resume
        if (e.getKeyCode() == KeyEvent.VK_P) {
            handleButtonPressed();
            return;
        }

        // Don't proceed if stopped or paused
        if (figure == null || moveLock || thread.isPaused()) {
            return;
        }

        // Handle remaining key events
        switch (e.getKeyCode()) {

            case KeyEvent.VK_Y:
                ActionWhenAgree();
//                System.out.println("about to handle figure start!");
                // handleFigureStart();
//                System.out.println("You made it!");
                break;
            case KeyEvent.VK_LEFT:
                figure.moveLeft();
                break;

            case KeyEvent.VK_RIGHT:
                figure.moveRight();
                break;

            case KeyEvent.VK_DOWN:
                handleDown();




                break;
            case KeyEvent.VK_SPACE:
                figure.moveDown();
                break;
            case KeyEvent.VK_UP:

                if (e.isControlDown()) {
                    figure.rotateRandom();
                } else if (e.isShiftDown()) {
                    figure.rotateClockwise();
                } else {
                    figure.rotateCounterClockwise();
                }
                break;

            case KeyEvent.VK_S:
                if (level < 9) {
                    level++;
                    handleLevelModification();
                }
                break;

            case KeyEvent.VK_N:
                preview = !preview;
                if (preview && figure != nextFigure) {
                    nextFigure.attach(previewBoard, true);
                    nextFigure.detach();
                } else {
                    previewBoard.clear();
                }
                break;
            case KeyEvent.VK_P:
                break;
        }
    }
    int counter = 0;
    int indexOfCurrent;
    int indexOfNext;

    @Override
    public void handleFigureStart() {
//Paul edit
//        indexOfCurrent=indexOfNext;
//        figure = nextFigure;'
        BoardConverter bc = new BoardConverter();
//        if (camefromOops) {

//            if (null != figure) {
//                playedBoards.add(bc.ConvertToByte_specializeCurrentFigure(board, figure, 0));
//                playedPieces.add(figure.shapeshape);
//                camefromOops = false;
//            }

//        }

        int rotation = 0;
        if (useFailSet) {
            // nextFigure=figure;
            if (futureBoards.size() > 1) {

                System.out.println(futureBoards.size());
                //System.out.println("shuffleFails: "+ ShuffleFails);
                System.out.println("myco shuffleFails: " + myCO.GetShuffleFails());
                System.out.println();

                //<editor-fold defaultstate="collapsed" desc="comment">
                //match size
                //                if (futureBoards.size()!=futurePieces.size()) {
                //                    if (futureBoards.size()>futurePieces.size()) {
                //                        while (futureBoards.size()!=futurePieces.size()) {
                //                            futurePieces.addLast((int)(figures.length * Math.random()));
                //                        }
                //                    }else{
                //                        JOptionPane.showMessageDialog(null, "Uhoh, wrong size inputs perhaps?");
                //                    }
                //                }
                //</editor-fold>
                System.out.println("boards: " + futureBoards.size() + " pieces: " + futurePieces.size());

                indexOfCurrent = (myCO.GetShuffleFails()) ? (int) (Math.random() * futureBoards.size()) : futureBoards.indexOf(futureBoards.peek());


                System.out.println(indexOfCurrent + " That was my takemeIndex");
                figure = figures[futurePieces.remove(indexOfCurrent) - 1];
                nextFigure = figures[((indexOfCurrent + 1) < futurePieces.size()) ? futurePieces.get(indexOfCurrent + 1) - 1 : 1];
                currentBoard = futureBoards.remove(indexOfCurrent);

                playedBoards.add(currentBoard);
                SetBoardToThisBoard(currentBoard);

                if (!futurePieces.isEmpty()) {
//                    // Move next figure to current
//                    ++counter;
//                    System.out.println(counter);                
//                    int thepiece = futurePieces.remove(indexOfCurrent);
//                    playedPieces.add(thepiece);
//                    //int nextPiece=
//                    figure = figures[thepiece - 1];
                } else {
                    figure = figures[(int) (figures.length * Math.random())];
                    System.out.println("The next figure is made up!");
                }
                //end

                moveLock = false;
                rotation = nextRotation;




                //     nextFigure.rotateRandom();
                nextRotation = nextFigure.getRotation();


                // Handle figure preview
                if (preview) {
                    previewBoard.clear();
                    nextFigure.attach(previewBoard, true);
                    nextFigure.detach();
                }


                // Attach figure to game board
                figure.setRotation(rotation);
                Cenario result;
                Object[] objs = FindWhereToMatchBoard();
                result = (Cenario) objs[0];
                int j = (int) objs[1];





                if (!figure.attach(board, false)) {
                    System.out.println("I'm the one who thinks the game should end");
                    previewBoard.clear();
                    figure.attach(previewBoard, true);
                    figure.detach();
                    handleGameOver();
                }
                //     System.out.println("is figure attached:" + figure.isAttached());
                //move the piece AFTER you find where it needs to be
                //      System.out.println("is result null: " + (null == result));
                movePieceHelper(result, j);
                //show the whole piece if I can
                int howfar = 2;
                if (figure.canMoveTo(figure.GetxPos(), figure.GetyPos() + howfar, figure.getRotation())) {
                    for (int i = 0; i < howfar; i++) {
                        figure.moveDown();

                    }
                }



            } else {

                PrintGoodAndEvil();
                AddFailSetToClosedList();
                handleGameOver();
            }
        } else {
        }

//paul edit
//        while (figure.canMoveTo(figure.GetxPos(), figure.GetyPos() + 1, figure.getRotation()) && !figure.isAllVisible()) {
//            figure.moveDown();
//        }
//end

    }

//    @Override
    public void SetBoardToThisBoard(byte[][] byteBoard) {
        for (int i = 0; i < byteBoard.length; i++) {
            for (int j = 0; j < byteBoard[0].length; j++) {
                int g = (int) byteBoard[i][j];
                switch (g) {
                    case 0:
                        //continue;
                        board.setSquareColor(j, i, null);
                        break;
                    case 1:
                        board.setSquareColor(j, i, Color.yellow);
                        break;
                    case 2:
                        board.setSquareColor(j, i, Color.RED);
                        break;
                    default:
                        throw new AssertionError();
                }


            }

        }
    }

    //   @Override
    public void LoadInEntireFailSequence() {
        //     System.out.println("i'm here");
        //Create a file chooser
        TetrisReader tetrisReader = new TetrisReader();
        String path = TetrisFactory.path + "/FailSets";
        System.out.println(path);
        File file = tetrisReader.ChooseFileAtThisPath(path);
        LoadInEntireFailSequenceHelper(file);
    }

    private void LoadInEntireFailSequenceHelper(File file) {
        TetrisReader tetrisReader = new TetrisReader();
        LinkedList<APlacementRecord> placements = tetrisReader.READinEntireFile(file);
        System.out.println("placements size: " + placements.size());
        for (APlacementRecord aPlacementRecord : placements) {

            futureBoards.add(aPlacementRecord.GetBoard());
            futurePieces.add(aPlacementRecord.getPieceCode());
        }


    }

    protected void PrintGoodAndEvil() {
        PrintGoodAndEvil(("_GOOD_" + name + ".txt"), ("_EVIL_" + name + ".txt"));
//        TetrisWriter tw = new TetrisWriter();
//        String startExtention = TetrisFactory.path + TetrisFactory.Corrections + "/" + name +"/"+tw.GetTimeStamp(true, true, true, false, false, false);
//        File file = new File(startExtention);
//        boolean b=false;
//        if (!file.exists()) {
//            b=file.mkdirs();
//        }
//        System.out.println("T/F : I succeeded in making all directories. ANSWER: " + b);
//        
//        String timestamp = tw.GetTimeStamp(true,true,true,true,true,true);
//        String goodFile = startExtention + timestamp + "_GOOD_" + name + ".txt";
//        String evilFile = startExtention + timestamp + "_EVIL_" + name + ".txt";
//
//        tw.WritelHistoryToFile(evil, evilFile);
//        tw.WritelHistoryToFile(good, goodFile);

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    protected void PrintGoodAndEvil(String goodending, String evilending) {
        TetrisWriter tw = new TetrisWriter();
        String startExtention = TetrisFactory.path + TetrisFactory.Corrections + "/" + name + "/" + tw.GetTimeStamp(true, true, true, false, false, false);
        File file = new File(startExtention);
        boolean b = false;
        if (!file.exists()) {
            b = file.mkdirs();
        }
        System.out.println("T/F : I succeeded in making all directories. ANSWER: " + b);

        String timestamp = tw.GetTimeStamp(true, true, true, true, true, true);
        String goodFile = startExtention + "/" + timestamp + goodending;
        String evilFile = startExtention + "/" + timestamp + evilending;

        tw.WritelHistoryToFile(evil, evilFile);
        tw.WritelHistoryToFile(good, goodFile);

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

//    @Override
    public void OopsButtonPressed() {
        if (!useFailSet) {
            thread.setPaused(true);
            handlePause();
        }

        camefromOops = true;
        if (!evil.isEmpty() && !good.isEmpty()) {
            evil.removeLast();
            good.removeLast();

            System.out.println("I've popped the last board and piece!");
        } else {
            System.out.println("I didn't pop anything");
        }
        if (useFailSet) {
            JOptionPane.showMessageDialog(myWS, " Last correction removed from our records.");
            return;
        }
        OopsButtonHelperHandleFigStart(false);











        //        if (!evil.isEmpty() && !good.isEmpty()) {
//            evil.removeLast();
//            good.removeLast();
//            currentBoard = playedBoards.getLast();
//            System.out.println("I've popped the last board and piece!");
//        } else {
//            System.out.println("I didn't pop anything");
//        }

        //       futureBoards.addFirst(playedBoards.removeLast());
//        currentBoard = playedBoards.removeLast();
//        nextFigure = new Figure(playedPieces.removeLast());
//        figure.detach();
//        figure=null;
//        SetBoardToThisBoard(currentBoard);
//      // handleFigureStart();
//        
//       // board.PerhapsRedrawAll();

    }

//    @Override
    public void ActionWhenAgree() {

        BoardConverter bc = new BoardConverter();
        //make the reds real.
        bc.ConvertThisColorToThisColor(board, Color.RED, Color.YELLOW);


        //get rid of the figure in there.
        byte[][] keepBoard = bc.ConvertToByte_specializeCurrentFigure(board, figure, 0);

//        System.out.println("this is supposed to just be where red becomes a two");
        APlacementRecord ap = new APlacementRecord();
//        System.out.println(ap.GetStringBoard(keepBoard));
        SquareBoard sq = bc.ConvertToBoard_2_isa_1(keepBoard);
        keepBoard = bc.ConvertToByte_RedBecomesA_0(sq);
        SetBoardToThisBoard(keepBoard);

        if (board.hasFullLines()) {
            board.removeFullLines();
            if (level < 9 && board.getRemovedLines() / 20 > level) {
                level = board.getRemovedLines() / 20;
                handleLevelModification();
            }
        }






        // figure.moveAllWayDown();

        if (null != figure) {
            figure.detach();
            figure = null;
            moveLock = true;
        }

        //    SetBoardToThisBoard(futureBoards.peek());

    }

    //   @Override
    public void handleDown() {
        BoardConverter bc = new BoardConverter();

        //       System.out.println("super handleDown being executed!!!");
        TetrisWriter tetrisWriter = new TetrisWriter();
        //              APlacementRecord placementrecord = new APlacementRecord(figure.shapeshape, placementdecider.getBoard());
        System.out.println("evil board");
        System.out.println(tetrisWriter.GetStringBoard(currentBoard));
        APlacementRecord evilPlacement = new APlacementRecord(figure.shapeshape, currentBoard);

        figure.moveAllWayDown();
        //                PlacementDecider placementdecider2 = new PlacementDecider(board, figure, nextFigure);
        System.out.println("good board");
        //                placementdecider2.soutBoard();

        byte[][] b = bc.ConvertToByte_specializeCurrentFigure(board, figure, 2);

        APlacementRecord goodPlacement = new APlacementRecord(figure.shapeshape, b);
        System.out.println(goodPlacement.GetStringBoard(b));
        if (!bc.AreEquivelent(goodPlacement.GetBoard(), evilPlacement.GetBoard())) {
            evil.add(evilPlacement);
            good.add(goodPlacement);
        } else {
            System.out.println("HEY THOSE ARE THE SAME.");
        }

        moveLock = true;





        //  BoardConverter bc = new BoardConverter();
        bc.ConvertThisColorToThisColor(board, Color.RED, null);
    }

    private Object[] FindWhereToMatchBoard() {
        BoardConverter bc = new BoardConverter();
        ByteFig fig = new ByteFig(figure);

        for (int a = 1; a <= fig.getMaxOrientation(); a++) {
            fig.rotateToOrientation(a);
            for (int j = 0; j <= currentBoard[0].length - fig.getpiecewidth(); j++) {
                Cenario result = new Cenario(bc.ConvertToByte_RedBecomesA_0(board), fig, j, myCO.getWeights()); // w1, w2, w3, w4, w5, w6, w7, w8);
                System.out.println("comparing currentboard:");
                System.out.println((new TetrisWriter()).GetStringBoard(currentBoard));
                System.out.println("comparing cenario board:");
                System.out.println((new TetrisWriter()).GetStringBoard(
                        result.getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes()));

                if (bc.AreEquivelent(currentBoard, result.getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes())) {
//                    System.out.println("FOUND IT!!!!!");

                    j += placementDecider.getaxis(fig);
//                    System.out.println("Value of j:" + j);
                    //   movePieceHelper(cenario, j);

                    return new Object[]{result, j};
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Error, could not match placement", "Error", JOptionPane.WARNING_MESSAGE);
        return null;
    }

    private void AddFailSetToClosedList() {
        myCO.handleFailSetTrainingCompleted(FailFile);
    }

    protected void OopsButtonHelperHandleFigStart(boolean b) {
        if (useFailSet || b) {
            handleFigureStart();
        } else {
            System.out.println("in special handlefigstart. playedboardsempty? " + playedBoards.isEmpty());

            if ((playedBoards.size() < 2 || playedPieces.size() < 2)) {
                return;
            }


            //modify the score to reflect removing a piece.
            System.out.println("about to modify score");
            score = score - 1;
            counter--;
            handleScoreModification();

            playedBoards.removeLast();
            playedPieces.removeLast();

            useThesePiecesFirst.add(nextFigure.shapeshape);
            nextFigure = figure;

            figure = new Figure(playedPieces.getLast());

            BoardConverter bc = new BoardConverter();



            currentBoard = playedBoards.getLast();  //
            SetBoardToThisBoard(currentBoard);
            PlacementDecider placementDecider1 = new PlacementDecider(board, figure, nextFigure);
            placementDecider1.MakeDecision();

            //this is done again because the board had to first be set to make the placementdecider. :D
            currentBoard = placementDecider1.getBest().getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes();
            TetrisWriter tw = new TetrisWriter();
//            System.out.println("here is the current board");
//            System.out.println(tw.GetStringBoard(currentBoard));
            SetBoardToThisBoard(currentBoard);
            //  futureBoards.add(currentBoard);
            //   futurePieces.add(figure.shapeshape);

            int rotation = 0;


            moveLock = false;
            rotation = nextRotation;

            // Handle figure preview
            if (preview) {
                previewBoard.clear();
                System.out.println("NextFigure:" + nextFigure);
                if (null == nextFigure) {
                    nextFigure = randomFigure();
                }
                nextFigure.attach(previewBoard, true);
                nextFigure.detach();
            }


            // Attach figure to game board
            figure.setRotation(rotation);
            if (!figure.attach(board, false)) {
                //System.out.println("I'm the one who thinks the game should end");
                previewBoard.clear();
                figure.attach(previewBoard, true);
                figure.detach();
                //handleGameOver();
            }

            movePiece(placementDecider1);
        }
    }

    @Override
    public void handleLevelModification() {
        component.levelLabel.setText("Level: " + level);
        //thread.adjustSpeed();
    }
}
