/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisFactory;

import java.awt.event.KeyEvent;

/**
 *
 * @author paul.kline
 */
public interface TrainerFailInterface {

    void ActionWhenAgree();

    void LoadInEntireFailSequence();

    void OopsButtonPressed();

    void SetBoardToThisBoard(byte[][] byteBoard);

    void handleDown();

    void handleFigureStart();

    void handleKeyEvent(KeyEvent e);

    void handleStart();

    void handleTimer();
    
}
