/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NewTetrisDecoratorPackage;

import java.awt.Component;
import java.awt.event.KeyEvent;
import net.percederberg.tetris.Figure;
import net.percederberg.tetris.Game;

/**
 *
 * @author paul.kline
 */
public class TetrisDecorator implements GameInterface1 {
    
    protected GameInterface1 tempGame;

    public TetrisDecorator(GameInterface1 g){
        tempGame=g;
    }
    @Override
    public Class<?> GetClassInstance() {
        return tempGame.GetClassInstance();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Game GetInstance() {
        return tempGame.GetInstance();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component getComponent() {
        return tempGame.getComponent();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleButtonPressed() {
        tempGame.handleButtonPressed();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleFigureLanded() {
        tempGame.handleFigureLanded();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleFigureStart() {
        tempGame.handleFigureStart();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleGameOver() {
        tempGame.handleGameOver();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleKeyEvent(KeyEvent e) {
        tempGame.handleKeyEvent(e);
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleLevelModification() {
        tempGame.handleLevelModification();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handlePause() {
       tempGame.handlePause();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleResume() {
       tempGame.handleResume();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleScoreModification() {
        tempGame.handleScoreModification();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleStart() {
        tempGame.handleStart();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleTimer() {
        tempGame.handleTimer();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void quit() {
        tempGame.quit();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Figure randomFigure() {
        return tempGame.randomFigure();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isUseWeights() {
        return tempGame.isUseWeights();
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUseWeights(boolean b) {
        tempGame.setUseWeights(b);
        //throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
