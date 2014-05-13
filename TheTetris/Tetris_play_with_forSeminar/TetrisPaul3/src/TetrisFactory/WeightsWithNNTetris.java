/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisFactory;

import java.util.LinkedList;
import net.percederberg.tetris.Figure;
import tetris3.Cenario;
import tetris3.PlacementDecider;

/**
 *
 * @author paul.kline
 */
public class WeightsWithNNTetris extends OldWeightMethodTetris implements weightsWithNNTemplate {
    
    LinkedList<Cenario> whoseTheBest = new LinkedList<Cenario>();
    public boolean PauseOnDiff = true;
    
    @Override
    public void handleFigureStart() {
        
        int rotation;

        // Move next figure to current
        figure = nextFigure;
        moveLock = false;
        rotation = nextRotation;
        nextFigure = randomFigure();
        
        nextFigure.rotateRandom();
        nextRotation = nextFigure.getRotation();

        // Handle figure preview
        if (preview) {
            previewBoard.clear();
            nextFigure.attach(previewBoard, true);
            nextFigure.detach();
        }

        // Attach figure to game board
        figure.setRotation(rotation);
        if (!figure.attach(board, false)) {
            previewBoard.clear();
            figure.attach(previewBoard, true);
            figure.detach();
            handleGameOver();
        }
//        if (sout) {
//            System.out.println("Figure Orientation: " + figure.getRotation());
//        }
        handleMakeDecision();

        //   System.out.println("The actual best is: " + placementDecider.getBest().staticvalue);
//        if (sout) {
//            placementDecider.soutBoard();
//            System.out.println("FIGURE: ");
//            placementDecider.getFig().soutFigure();
//        }
        movePiece(placementDecider);
//        if (sout) {
//            placementDecider.getBest().soutBoard();
//            System.out.println("THIS IS BEST FIGURE:");
//            placementDecider.getBest().getMyfig().soutFigure();
//        }
//        if (false) {
//            System.out.println("Zero: " + placementDecider.getBest().zero);
//            System.out.println("Direct Zeros under: " + placementDecider.getBest().directzerosunder);
//            System.out.println("Flatratio: " + placementDecider.getBest().flatratio);
//            System.out.println("Hegit: " + placementDecider.getBest().height);
//            System.out.println("LINES: " + placementDecider.getBest().linescleared);
//            System.out.println("Sides touching pieces: " + placementDecider.getBest().sidestouchingpieces);
//            System.out.println("Sides touching edges: " + placementDecider.getBest().sidestouchingedges);
//            System.out.println("direct Peice Under: " + placementDecider.getBest().directpeiceunder);
//            System.out.println("");
//        }

    }
    
    @Override
    public void handleMakeDecision() {
        placementDecider = null;
        placementDecider = new PlacementDecider(board, figure, nextFigure, CO.weights);
        
        whoseTheBest = placementDecider.SetTopXChoices(5);
        for (Cenario cenario : whoseTheBest) {
            System.out.println("Static Value: " + cenario.staticvalue);
        }

        for (int i = 0; i < whoseTheBest.size(); i++) {
            //         System.out.println("Static Value: " + whoseTheBest.get(i).staticvalue);

        }
        
        if (!UseNN) {
            placementDecider.MakeDecision();
            return;
        }
//        placementDecider.SetmyCO(CO);
        if ((placementDecider.MakeADecisionAndAskNNIfNotSure() && !placementDecider.GetIsNNChoiceSameAsG_Choice())
                || placementDecider.HoleIsMade()) {
            
            if (PauseOnDiff) {
                thread.setPaused(true);
                handlePause();
            }
            
        }
    }
}
