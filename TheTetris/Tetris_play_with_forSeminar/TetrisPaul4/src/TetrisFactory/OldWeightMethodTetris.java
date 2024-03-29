/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisFactory;

import TemplateMethod.AbstractGame;
import net.percederberg.tetris.Game;
import tetris3.Cenario;
import tetris3.PlacementDecider;

/**
 *
 * @author Paul
 */
public class OldWeightMethodTetris extends AbstractGame {
        public boolean UseNN=false;

   
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
        placementDecider = null;
//        placementDecider = new PlacementDecider(board, figure, nextFigure, CO.weights);
        placementDecider.MakeDecision();
        
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
     


}
