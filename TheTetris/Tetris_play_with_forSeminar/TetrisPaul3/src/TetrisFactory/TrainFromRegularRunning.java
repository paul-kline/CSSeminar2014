/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisFactory;


import TetrisHelpful.BoardConverter;
import TetrisHelpful.TetrisWriter;
import java.awt.Color;
import javax.swing.JOptionPane;
import net.percederberg.tetris.Figure;
import net.percederberg.tetris.SquareBoard;
import tetris3.APlacementRecord;
import tetris3.PlacementDecider;

/**
 *
 * @author paul.kline
 */
public class TrainFromRegularRunning extends TrainFromFailSequenceTetris {

    @Override
    public void handleStart() {
        name = JOptionPane.showInputDialog(null, "Who are you?");
        automatic = false;

        boards.clear();
        pieces.clear();
        evil.clear();
        good.clear();
        // Reset components
        board.setMessage(null);
        board.clear();
        previewBoard.clear();
        handleLevelModification();
        handleScoreModification();
        component.button.setLabel("Pause");
        // LoadInEntireFailSequence();
        nextFigure = randomFigure();
        // figure = randomFigure();
//        InitializeNext();
//        SetBoardToThisBoard(boards.peek());
//        currentBoard = boards.peek();

        // Reset score and figures
        level = 1;
        score = 0;
        figure = null;

//        int thepiece = pieces.pop();
        //      playedPieces.add(thepiece);


        //nextFigure.rotateRandom();
        nextRotation = nextFigure.getRotation();

        // Start game thread
        thread.reset();
    }

    @Override
    public void handleFigureStart() {
//Paul edit
        BoardConverter bc = new BoardConverter();
        if (camefromOops) {
            if (null != figure) {
                playedBoards.add(bc.ConvertToByte_specializeCurrentFigure(board, figure, 0));
                playedPieces.add(figure.shapeshape);
                camefromOops = false;
            }

        }

        figure = nextFigure;
        nextFigure = (useThesePiecesFirst.isEmpty()) ? randomFigure() : new Figure(useThesePiecesFirst.removeLast());
        System.out.println("handling!!!!");



        PlacementDecider placementDecider = new PlacementDecider(board, figure, nextFigure);
        placementDecider.MakeDecision();
        currentBoard = placementDecider.getBest().getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes();
        SetBoardToThisBoard(currentBoard);


        boards.add(currentBoard);
        pieces.add(figure.shapeshape);




//        board = bc.ConvertToBoard_2_isa_RED(currentBoard);
//        board.PerhapsRedrawAll();
        int rotation = 0;
        currentBoard = boards.pop();


        //
        playedBoards.add(bc.ConvertToByte_specializeCurrentFigure(board, figure, 0));
        playedPieces.add(figure.shapeshape);




//        if (boards.peek() != null) {
//
//
//            if (!pieces.isEmpty()) {
//                currentBoard = boards.pop();
//                playedBoards.add(currentBoard);
//                SetBoardToThisBoard(currentBoard);
        if (++counter % 100 == 0) {
            PrintGoodAndEvil("_GOOD_Regular_" + name + ".txt", "_EVIL_Regular_run_" + name + ".txt");
            JOptionPane.showMessageDialog(component, "Thanks! Your corrections have been recorded!");
            evil.clear();
            good.clear();
            byte[][] keepboard = playedBoards.getLast();
            Integer keeppiece = playedPieces.getLast();
            playedBoards.clear();
            playedPieces.clear();
            playedBoards.add(keepboard);
            playedPieces.add(keeppiece);

        }
        label.setText("Countdown to Stopping Point: " + (100 - ((counter % 100) + 1)));
        moveLock = false;
        rotation = nextRotation;




        // Handle figure preview
        if (preview) {
            previewBoard.clear();
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
            handleGameOver();
        }

        movePiece(placementDecider);
    }

    @Override
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
        super.ActionWhenAgree(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleDown() {
//        System.out.println("my handle down!!!");
        super.handleDown(); //To change body of generated methods, choose Tools | Templates.
        BoardConverter bc = new BoardConverter();
        bc.ConvertThisColorToThisColor(board, Color.RED, null);
    }

    @Override
    public synchronized void handleTimer() {
        if (figure == null) {
            handleFigureStart();
        } else if (figure.hasLanded()) {
            handleFigureLanded();
        } else {
            figure.moveDown();
        }
    }

    @Override
    public void handleLevelModification() {
        component.levelLabel.setText("Level: " + level);
        //thread.adjustSpeed();
    }

    @Override
    public void OopsButtonPressed() {
        thread.setPaused(true);
        handlePause();
        camefromOops = true;
        if (!evil.isEmpty() && !good.isEmpty()) {
            evil.removeLast();
            good.removeLast();

            System.out.println("I've popped the last board and piece!");
        } else {
            System.out.println("I didn't pop anything");
        }
        OopsButtonHelperHandleFigStart(false);
    }

    private void OopsButtonHelperHandleFigStart(boolean b) {
        if (b) {
            handleFigureStart();
        } else {
            System.out.println("in special handlefigstart. playedboardsempty? " + playedBoards.isEmpty());
            if (playedBoards.size() < 2 || playedPieces.size() < 2) {
                return;
            }


            //modify the score to reflect removing a piece.
            score = score - 10;
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
            //  boards.add(currentBoard);
            //   pieces.add(figure.shapeshape);

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
}
