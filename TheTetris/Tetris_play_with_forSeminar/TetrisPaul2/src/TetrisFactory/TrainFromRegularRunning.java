/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisFactory;


import TetrisFactory.HelpfulStuff.BoardConverter;
import java.awt.Color;
import javax.swing.JOptionPane;
import net.percederberg.tetris.SquareBoard;
import tetrispaul2.APlacementRecord;
import tetrispaul2.PlacementDecider;

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

    public void handleFigureStart() {
//Paul edit


        figure = nextFigure;
        nextFigure = randomFigure();
        System.out.println("handling!!!!");

        BoardConverter bc = new BoardConverter();

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
        }

//                // Move next figure to current
//                System.out.println(++counter);
//                int thepiece = pieces.pop();
//                playedPieces.add(thepiece);
//                nextFigure = figures[thepiece - 1];
//            } else {
//                nextFigure = figures[1];
//                currentBoard = boards.pop();
//                playedBoards.add(currentBoard);
//                SetBoardToThisBoard(currentBoard);
//                System.out.println("The next figure is made up!");
//            }
//
//            //end

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
//        } else {
//
//            PrintGoodAndEvil();
//            handleGameOver();
//        }


//paul edit
//        while (figure.canMoveTo(figure.GetxPos(), figure.GetyPos() + 1, figure.getRotation()) && !figure.isAllVisible()) {
//            figure.moveDown();
//        }
//end
        movePiece(placementDecider);
    }

    @Override
    public void ActionWhenAgree() {
        BoardConverter bc = new BoardConverter();
        //make the reds real.
        bc.ConvertThisColorToThisColor(board, Color.RED, Color.YELLOW);


        //get rid of the figure in there.
        byte[][] keepBoard = bc.ConvertToByte_specializeCurrentFigure(board, figure, 0);

        System.out.println("this is supposed to just be where red becomes a two");
        APlacementRecord ap = new APlacementRecord();
        System.out.println(ap.GetStringBoard(keepBoard));
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
        System.out.println("my handle down!!!");
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
}
