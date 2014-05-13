/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisFactory;

import net.percederberg.tetris.Game;

/**
 *
 * @author Paul
 */
public class TetrisFactory {
    public static final int REGULAR=0;    
    public static final int OLDWEIGHTMETHOD=1;
    public static final int TRAINFROMFAILSEQUENCE=2;    
    public static final int SIDEBYSIDECOMPAREOFSAMEGAME=3;  
    public static final int WEIGHTSWITHNNTETRIS=4;    
    public static final int TRAINFROMREGULARRUNNING=5;    
    public static final String path= "./Resources";
    public static final String Corrections= "/Corrections";
    public static final String FailCases= "/Fail Cases";
    public static final int VIEWTOPCHOICES=6;
    
    




    public Game createGame(int type){
        switch (type) {
            case TetrisFactory.REGULAR:
                return new RegularTetris();            
            case TetrisFactory.OLDWEIGHTMETHOD:
                return new OldWeightMethodTetris();
                
            case TetrisFactory.TRAINFROMFAILSEQUENCE:
                return new TrainFromFailSequenceTetris();
                
            case TetrisFactory.SIDEBYSIDECOMPAREOFSAMEGAME:
                return new SideBySideCompareOfSameGameTetris();
            case TetrisFactory.WEIGHTSWITHNNTETRIS:
                return new WeightsWithNNTetris();
            case TetrisFactory.TRAINFROMREGULARRUNNING:
                return new TrainFromRegularRunning();
            case TetrisFactory.VIEWTOPCHOICES:
                 return new ViewTopChoices();
            default:
                throw new AssertionError();
        }
    }
}
