/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.percederberg.tetris;

import java.awt.Component;
import java.awt.event.KeyEvent;

/**
 *
 * @author paul.kline
 */
public interface GameInterface {

    Class<?> GetClassInstance();

    Game GetInstance();

    /**
     * Returns a new component that draws the game.
     *
     * @return the component that draws the game
     */
    Component getComponent();

    /**
     * Handles a button press event. This will launch different events depending
     * on the state of the game, as the button semantics change as the game
     * changes. This method is synchronized to avoid race conditions with other
     * asynchronous events (timer and keyboard).
     */
    void handleButtonPressed();

    /**
     * Handles a figure landed event. This will check that the figure is
     * completely visible, or a game over event will be launched. After this
     * control, any full lines will be removed. If no full lines could be
     * removed, a figure start event is launched directly.
     */
    void handleFigureLanded();

    /**
     * Handles a figure start event. This will move the next figure to the
     * current figure position, while also creating a new preview figure. If the
     * figure cannot be introduced onto the game board, a game over event will
     * be launched.
     */
    void handleFigureStart();

    /**
     * Handles a game over event. This will stop the game thread, reset all
     * figures and print a game over message.
     */
    void handleGameOver();

    /**
     * Handles a keyboard event. This will result in different actions being
     * taken, depending on the key pressed. In some cases, other events will be
     * launched. This method is synchronized to avoid race conditions with other
     * asynchronous events (timer and mouse).
     *
     * @param e the key event
     */
    void handleKeyEvent(KeyEvent e);

    /**
     * Handles a level modification event. This will modify the level label and
     * adjust the thread speed.
     */
    void handleLevelModification();

    /**
     * Handles a game pause event. This will pause the game thread and print a
     * pause message on the game board.
     */
    void handlePause();

    /**
     * Handles a game resume event. This will resume the game thread and remove
     * any messages on the game board.
     */
    void handleResume();

    /**
     * Handle a score modification event. This will modify the score label.
     */
    void handleScoreModification();

    /**
     * Handles a game start event. Both the main and preview square boards will
     * be reset, and all other game parameters will be reset. Finally the game
     * thread will be launched.
     */
    void handleStart();

    /**
     * Handles a timer event. This will normally move the figure down one step,
     * but when a figure has landed or isn't ready other events will be
     * launched. This method is synchronized to avoid race conditions with other
     * asynchronous events (keyboard and mouse).
     */
    void handleTimer();

    /**
     * Kills the game running thread and makes necessary clean-up. After calling
     * this method, no further methods in this class should be called. Neither
     * should the component returned earlier be trusted upon.
     */
    void quit();

    /**
     * Returns a random figure. The figures come from the figures array, and
     * will not be initialized.
     *
     * @return a random figure
     */
    Figure randomFigure();
    
}
