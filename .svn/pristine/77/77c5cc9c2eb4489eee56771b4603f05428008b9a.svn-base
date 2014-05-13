/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TemplateMethod;

import NeuralThings.PaulNetworkFactory;
import TetrisHelpful.BoardConverter;
import TetrisHelpful.TetrisWriter;
import java.awt.Label;
import java.util.LinkedList;
import tetrispaul4.ControllerObject;
import net.percederberg.tetris.Game;
import tetris3.APlacementRecord;
import tetris3.Cenario;
import tetris3.PersonalizedPlacementDecider;
import tetrispaul4.WeightSelector;
import tetris3.PlacementDecider;

/**
 *
 * @author paul.kline
 */
public class AbstractGame extends Game implements PlayingInterface {

    public ControllerObject myCO;
    protected LinkedList<byte[][]> playedBoards;
    protected LinkedList<Integer> playedPieces;
    protected LinkedList<byte[][]> futureBoards;
    protected LinkedList<Integer> futurePieces;
    public Label label;
 
    LinkedList<APlacementRecord> placementHistory = new LinkedList<>();
    //  protected boolean ShuffleFails=false;

    public AbstractGame() {
        super();
    }

    public AbstractGame(WeightSelector w, ControllerObject co) {
        this();
        myCO = co;
        myWS = w;

    }

    @Override
    public void handleFigureStart() {
        super.handleFigureStart();

       PlacementDecider currpd=null;
        if (myCO.ShouldUseWeights()) {
            handleMakeDecision();
            movePiece(placementDecider);
            currpd=placementDecider;
            
        }else if (myCO.getUseNN()) {
            PersonalizedPlacementDecider x = new PersonalizedPlacementDecider(board,figure,myCO);
            x.MakeADecisionAndAskNNIfNotSure();
            movePiece(x);
            currpd=x;
        }
      //  System.out.println(myCO.getMyConfiger());
        if (myCO.getMyConfiger().getMyTopChoiceViewer()!=null && myCO.getMyConfiger().getMyTopChoiceViewer().isVisible()) {
            myCO.getMyConfiger().setTopChoicesContent(currpd);
        }
        
    }

    public boolean shouldKeepHistory() {
        return true;
    }

   

   


    @Override
    public void handleMakeDecision() {
        placementDecider = null;
        
        placementDecider = new PlacementDecider(board, figure, nextFigure, myCO.getWeights());
        
        if (myCO.getUseNN()) {
            placementDecider.SetmyCO(myCO);
            placementDecider.MakeADecisionAndAskNNIfNotSure();
          
            if (myCO.ShouldPauseOnDiff()&& TestPauseConditions()) {
                thread.setPaused(true);
                handlePause();
            }
        } else {
            placementDecider.MakeDecision();
        }

    }

    @Override
    public void handleFigureLandedHook() {
        if (myCO.ShouldKeepHistory()) {
            // Cenario cenario = placementDecider.getBest();
            BoardConverter bc = new BoardConverter();
            APlacementRecord rememberMe = new APlacementRecord(figure.shapeshape,
                    bc.ConvertToByte_specializeCurrentFigure(board, figure, 2));
//            APlacementRecord rememberMe = new APlacementRecord(cenario.getMyfig().getType(),
//                    cenario.DuplicateBoard(cenario.getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes()));
//            System.out.println("HERE IS THE BOARD i AM KEEPING!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            System.out.println((new TetrisWriter()).GetStringBoard(rememberMe.GetBoard()));
            AddBoardToHistory(rememberMe);
        }
    }

    public boolean TestPauseConditions() {
        if (myCO.ShouldPauseOnDiff() && !placementDecider.GetIsNNChoiceSameAsG_Choice()) {
//            System.out.println("should pause is true");
            return true;
        }
        if (myCO.isCareIfHoleIsMadeByPlacement() && placementDecider.HoleIsMade()) {
//            System.out.println("Should pause is true");
            return true;
        }
//        System.out.println("should pause is false");
        return false;
    }

   
    


    protected void AddBoardToHistory(APlacementRecord record) {
        if (placementHistory.size() >= myCO.getHistoryHowMany()) {
            placementHistory.removeFirst();
        }
        placementHistory.add(record);

    }

//    public void setShuffleFails(boolean b) {
//    //    System.out.println("I am setting shuffleFails to this: "+ b);
//    //    this.ShuffleFails=b;
//    }
}
