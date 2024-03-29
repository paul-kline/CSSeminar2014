/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisFactory;


import TetrisHelpful.BoardConverter;
import TetrisHelpful.TetrisWriter;
import java.awt.Color;
import tetris3.APlacementRecord;

/**
 *
 * @author Paul
 */
public class ViewTopChoices extends WeightsWithNNTetris {
    @Override
    public void handleFigureStart(){
        super.handleFigureStart();
        BoardConverter bc = new BoardConverter();

        for (int i = 0; i < allboards.length; i++) {
            TetrisWriter tw = new TetrisWriter();
            bc.MakeMyPartsHisParts(allboards[i], bc.ConvertToBoard_2_isa_RED(whoseTheBest.get(i).getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes()));
            allboards[i].PerhapsRedrawAll();
        
        }
    
    }
    
    
}
