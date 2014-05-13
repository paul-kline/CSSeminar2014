/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DecoratorPackage;

import java.awt.Component;
import java.awt.event.KeyEvent;
import net.percederberg.tetris.Figure;
import net.percederberg.tetris.Game;
import net.percederberg.tetris.GameInterface;

/**
 *
 * @author paul.kline
 */
public class GameGeneric extends Game implements GameInterface {

    Game GG;
    @Override
    public Class<?> GetClassInstance() {
        return GG.GetClassInstance();
    }

    @Override
    public Game GetInstance() {
        return GG.GetInstance();
    }

    @Override
    public Component getComponent() {
        return GG.getComponent();
    }

    @Override
    public void handleButtonPressed() {
        GG.handleButtonPressed();
    }

    @Override
    public void handleFigureLanded() {
        GG.handleFigureLanded();
    }

    @Override
    public void handleFigureStart() {
        GG.handleFigureStart();
    }

    @Override
    public void handleGameOver() {
        GG.handleGameOver();
    }

    @Override
    public void handleKeyEvent(KeyEvent e) {
        GG.handleKeyEvent(e);
    }

    @Override
    public void handleLevelModification() {
        GG.handleLevelModification();
    }

    @Override
    public void handlePause() {
        GG.handlePause();
    }

    @Override
    public void handleResume() {
        GG.handleResume();
    }

    @Override
    public void handleScoreModification() {
        GG.handleScoreModification();
    }

    @Override
    public void handleStart() {
        GG.handleStart();
    }

    @Override
    public void handleTimer() {
        GG.handleTimer();
    }

    @Override
    public void quit() {
        GG.quit();
    }

    @Override
    public Figure randomFigure() {
        return GG.randomFigure();
    }
    
}
