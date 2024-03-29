/*
 * @(#)Game.java
 *
 * This work is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This work is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * Copyright (c) 2003 Per Cederberg. All rights reserved.
 */
package net.percederberg.tetris;

import TetrisFactory.TrainFromFailSequenceTetris;
import TetrisFactory.TrainFromRegularRunning;
import TetrisFactory.ViewTopChoices;
import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import tetrispaul2.Cenario;
import tetrispaul2.PlacementDecider;
//import tetrispaul2.PlacementDecider;

/**
 * The Tetris game. This class controls all events in the game and handles all
 * the game logics. The game is started through user interaction with the
 * graphical game component provided by this class.
 *
 * @version 1.2
 * @author Per Cederberg, per@percederberg.net
 */
public class Game extends Object implements GameInterface {

     /**
     * The controller object allows changes to be made between the forms.
     * 
     */
    public ControllerObject CO= new ControllerObject();
   
    /**
     * The main square board. This board is used for the game itself.
     */
    protected SquareBoard board = null;
    // protected PlacementDecider placementDecider;
    /**
     * The preview square board. This board is used to display a preview of the
     * figures.
     */
    protected SquareBoard previewBoard = new SquareBoard(5, 5);

    protected PlacementDecider placementDecider;

    /**
     * The figures used on both boards. All figures are reutilized in order to
     * avoid creating new objects while the game is running. Special care has to
     * be taken when the preview figure and the current figure refers to the
     * same object.
     */
    protected Figure[] figures = {
        new Figure(Figure.SQUARE_FIGURE),
        new Figure(Figure.LINE_FIGURE),
        new Figure(Figure.S_FIGURE),
        new Figure(Figure.Z_FIGURE),
        new Figure(Figure.RIGHT_ANGLE_FIGURE),
        new Figure(Figure.LEFT_ANGLE_FIGURE),
        new Figure(Figure.TRIANGLE_FIGURE)
    };
    /**
     * The graphical game component. This component is created on the first call
     * to getComponent().
     */
    protected GamePanel component = null;
    /**
     * The thread that runs the game. When this variable is set to null, the
     * game thread will terminate.
     */
    protected GameThread thread = null;
    /**
     * The game level. The level will be increased for every 20 lines removed
     * from the square board.
     */
    protected int level = 1;
    /**
     * The current score. The score is increased for every figure that is
     * possible to place on the main board.
     */
    protected int score = 0;
    /**
     * The current figure. The figure will be updated when
     */
    protected Figure figure = null;
    /**
     * The next figure.
     */
    protected Figure nextFigure = null;
    /**
     * The rotation of the next figure.
     */
    protected int nextRotation = 0;
    /**
     * The figure preview flag. If this flag is set, the figure will be shown in
     * the figure preview board.
     */
    protected boolean preview = true;
    /**
     * The move lock flag. If this flag is set, the current figure cannot be
     * moved. This flag is set when a figure is moved all the way down, and
     * reset when a new figure is displayed.
     */
    protected boolean moveLock = false;
    protected boolean sout = false; //Paul put this here
    protected boolean automatic = true;
    protected boolean fast = false;

    /**
     * Creates a new Tetris game. The square board will be given the default
     * size of 10x20.
     */
    public Game() {
        this(10, 20);
    }

    @Override
    public Game GetInstance() {
        return this;
    }

    /**
     * Creates a new Tetris game. The square board will be given the specified
     * size.
     *
     * @param width the width of the square board (in positions)
     * @param height the height of the square board (in positions)
     */
    public Game(int width, int height) {
        board = new SquareBoard(width, height);
        board.setMessage("Press start");
        thread = new GameThread();

    }

    /**
     * Kills the game running thread and makes necessary clean-up. After calling
     * this method, no further methods in this class should be called. Neither
     * should the component returned earlier be trusted upon.
     */
    @Override
    public void quit() {
        thread = null;
    }

    /**
     * Returns a new component that draws the game.
     *
     * @return the component that draws the game
     */
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new GamePanel();
        }
        return component;
    }

    /**
     * Handles a game start event. Both the main and preview square boards will
     * be reset, and all other game parameters will be reset. Finally the game
     * thread will be launched.
     */
    @Override
    public void handleStart() {
        System.out.println("handling start!");
        // Reset score and figures
        level = 1;
        score = 0;
        figure = null;
        nextFigure = randomFigure();
        nextFigure.rotateRandom();
        nextRotation = nextFigure.getRotation();

        // Reset components
        board.setMessage(null);
        board.clear();
        previewBoard.clear();
        handleLevelModification();
        handleScoreModification();
        component.button.setLabel("Pause");

        // Start game thread
        thread.reset();
    }

    /**
     * Handles a game over event. This will stop the game thread, reset all
     * figures and print a game over message.
     */
    @Override
    public void handleGameOver() {

        // Stop game thred
        thread.setPaused(true);

        // Reset figures
        if (figure != null) {
            figure.detach();
        }
        figure = null;
        if (nextFigure != null) {
            nextFigure.detach();
        }
        nextFigure = null;

        // Handle components
        board.setMessage("Game Over");
        component.button.setLabel("Start");
        // placementDecider.getBest().soutMyWeights();
        //  placementDecider = null;
    }

    /**
     * Handles a game pause event. This will pause the game thread and print a
     * pause message on the game board.
     */
    @Override
    public void handlePause() {
        thread.setPaused(true);
        board.setMessage("Paused");
        component.button.setLabel("Resume");
    }

    /**
     * Handles a game resume event. This will resume the game thread and remove
     * any messages on the game board.
     */
    @Override
    public void handleResume() {
        board.setMessage(null);
        component.button.setLabel("Pause");
        thread.setPaused(false);
    }

    /**
     * Handles a level modification event. This will modify the level label and
     * adjust the thread speed.
     */
    @Override
    public void handleLevelModification() {
        component.levelLabel.setText("Level: " + level);
        thread.adjustSpeed();
    }

    /**
     * Handle a score modification event. This will modify the score label.
     */
    @Override
    public void handleScoreModification() {
        component.scoreLabel.setText("Score: " + score);
    }

    /**
     * Handles a figure start event. This will move the next figure to the
     * current figure position, while also creating a new preview figure. If the
     * figure cannot be introduced onto the game board, a game over event will
     * be launched.
     */
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

    }

    /**
     * Handles a figure landed event. This will check that the figure is
     * completely visible, or a game over event will be launched. After this
     * control, any full lines will be removed. If no full lines could be
     * removed, a figure start event is launched directly.
     */
    @Override
    public void handleFigureLanded() {

        // Check and detach figure
        if (figure.isAllVisible()) {

            score += 10;
            handleScoreModification();
        } else {
            handleGameOver();
            return;
        }
        figure.detach();
        figure = null;

        // Check for full lines or create new figure
        if (board.hasFullLines()) {
            board.removeFullLines();
            if (level < 9 && board.getRemovedLines() / 20 > level) {
                level = board.getRemovedLines() / 20;
                handleLevelModification();
            }
        } else {
            handleFigureStart();
        }
    }

    /**
     * Handles a timer event. This will normally move the figure down one step,
     * but when a figure has landed or isn't ready other events will be
     * launched. This method is synchronized to avoid race conditions with other
     * asynchronous events (keyboard and mouse).
     */
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

    /**
     * Handles a button press event. This will launch different events depending
     * on the state of the game, as the button semantics change as the game
     * changes. This method is synchronized to avoid race conditions with other
     * asynchronous events (timer and keyboard).
     */
    @Override
    public synchronized void handleButtonPressed() {
        if (nextFigure == null) {
            handleStart();
        } else if (thread.isPaused()) {
            handleResume();
        } else {
            handlePause();
        }
    }

    /**
     * Handles a keyboard event. This will result in different actions being
     * taken, depending on the key pressed. In some cases, other events will be
     * launched. This method is synchronized to avoid race conditions with other
     * asynchronous events (timer and mouse).
     *
     * @param e the key event
     */
    @Override
    public synchronized void handleKeyEvent(KeyEvent e) {

        // Handle start, pause and resume
        if (e.getKeyCode() == KeyEvent.VK_P) {
            handleButtonPressed();
            return;
        }

        // Don't proceed if stopped or paused
        if (figure == null || moveLock || thread.isPaused()) {
            return;
        }

        // Handle remaining key events
        switch (e.getKeyCode()) {

            case KeyEvent.VK_LEFT:
                figure.moveLeft();
                break;

            case KeyEvent.VK_RIGHT:
                figure.moveRight();
                break;

            case KeyEvent.VK_DOWN:
                figure.moveAllWayDown();

                moveLock = true;
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                if (e.isControlDown()) {
                figure.rotateRandom();
            } else if (e.isShiftDown()) {
                figure.rotateClockwise();
            } else {
                figure.rotateCounterClockwise();
            }
                break;

            case KeyEvent.VK_S:
                if (level < 9) {
                level++;
                handleLevelModification();
            }
                break;

            case KeyEvent.VK_N:
                preview = !preview;
                if (preview && figure != nextFigure) {
                    nextFigure.attach(previewBoard, true);
                    nextFigure.detach();
                } else {
                    previewBoard.clear();
                }
                break;
        }
    }

    @Override
    public Class<?> GetClassInstance() {
        return this.getClass();
    }

    /**
     * Returns a random figure. The figures come from the figures array, and
     * will not be initialized.
     *
     * @return a random figure
     */
    @Override
    public Figure randomFigure() {
        return figures[(int) (Math.random() * figures.length)];
    }

    //mine. I put this here long ago.
//    protected void movePiece(Cenario best) {
//        int rot = figure.getRotation();
//        if (sout) {
//            System.out.println("rot: " + rot);
//        }
//        while (best.getMyfig().getOrientation() != rot) {
//            if (rot < best.getMyfig().getOrientation()) {
//                //create some sort of delay
//                figure.rotateClockwise();
//
//                //put some sort of delay here
//                figure.setRotation(++rot);
//            } else {
//                figure.rotateCounterClockwise();
//                figure.setRotation(--rot);
//            }
//
//
//        }
//        int start = 5;
//        while (start != placementDecider.getLeftplace()) {
//            if (start > placementDecider.getLeftplace()) {
//                this.figure.moveLeft();
//                start--;
//            } else {
//                this.figure.moveRight();
//                start++;
//            }
//        }
//        if (automatic) {
//            this.figure.moveAllWayDown();
//        }
//        if (sout) {
//            System.out.println("Figure rotation: " + figure.getRotation());
//
//        }
//        //int loc=figure.
//    }
    /**
     * The game time thread. This thread makes sure that the timer events are
     * launched appropriately, making the current figure fall. This thread can
     * be reused across games, but should be set to paused state when no game is
     * running.
     */
    public class GameThread extends Thread {

        /**
         * The game pause flag. This flag is set to true while the game should
         * pause.
         */
        public boolean paused = true;
        /**
         * The number of milliseconds to sleep before each automatic move. This
         * number will be lowered as the game progresses.
         */
        protected int sleepTime = 500;

        /**
         * Creates a new game thread with default values.
         */
        public GameThread() {
        }

        /**
         * Resets the game thread. This will adjust the speed and start the game
         * thread if not previously started.
         */
        public void reset() {
            adjustSpeed();
            setPaused(false);
            if (!isAlive()) {
                this.start();
            }
        }

        /**
         * Checks if the thread is paused.
         *
         * @return true if the thread is paused, or false otherwise
         */
        public boolean isPaused() {
            return paused;
        }

        /**
         * Sets the thread pause flag.
         *
         * @param paused the new paused flag value
         */
        public void setPaused(boolean paused) {
            this.paused = paused;
        }

        /**
         * Adjusts the game speed according to the current level. The sleeping
         * time is calculated with a function making larger steps initially an
         * smaller as the level increases. A level above ten (10) doesn't have
         * any further effect.
         */
        public void adjustSpeed() {
            sleepTime = 4500 / (level + 5) - 250;
            if (sleepTime < 1) {
                sleepTime = 1;
            }
        }

        /**
         * Runs the game.
         */
        public void run() {
            while (thread == this) {
                // Make the time step
                handleTimer();

                // Sleep for some time
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ignore) {
                    // Do nothing
                }

                // Sleep if paused
                while (paused && thread == this) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) {
                        // Do nothing
                    }
                }
            }
        }
    }

    /**
     * A game panel component. Contains all the game components.
     */
    public SquareBoard[] allboards;
    public class GamePanel extends Container {

        /**
         * The component size. If the component has been resized, that will be
         * detected when the paint method executes. If this value is set to
         * null, the component dimensions are unknown.
         */
        protected Dimension size = null;
        /**
         * The score label.
         */
        protected Label scoreLabel = new Label("Score: 0");
        /**
         * The level label.
         */
        public Label levelLabel = new Label("Level: 1");
        /**
         * The generic button.
         */
        public Button button = new Button("Start");
        protected Button Autobutton = new Button("Toggle Auto");
        protected Button speedButton = new Button("Toggle Speed");

        /**
         * Creates a new game panel. All the components will also be added to
         * the panel.
         */
        public GamePanel() {
            super();
            initComponents();
        }

        /**
         * Paints the game component. This method is overridden from the default
         * implementation in order to set the correct background color.
         *
         * @param g the graphics context to use
         */
        public void paint(Graphics g) {
            Rectangle rect = g.getClipBounds();

            if (size == null || !size.equals(getSize())) {
                size = getSize();
                resizeComponents();
            }
            g.setColor(getBackground());
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
            super.paint(g);
        }

        /**
         * Initializes all the components, and places them in the panel.
         */
        
        
        protected void initComponents() {
            GridBagConstraints c;

            // Set layout manager and background
            setLayout(new GridBagLayout());
            setBackground(Configuration.getColor("background", "#d4d0c8"));

            // Add game board
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.gridheight = 4;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.fill = GridBagConstraints.BOTH;
            this.add(board.getComponent(), c);

            // Add next figure board
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0;
            c.weightx = 0.2;
            c.weighty = 0.18;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(15, 15, 0, 15);
            this.add(previewBoard.getComponent(), c);

            // Add score label
            scoreLabel.setForeground(Configuration.getColor("label",
                    "#000000"));
            scoreLabel.setAlignment(Label.CENTER);
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 1;
            c.weightx = 0.3;
            c.weighty = 0.05;
            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(0, 15, 0, 15);
            this.add(scoreLabel, c);

            // Add level label
            levelLabel.setForeground(Configuration.getColor("label",
                    "#000000"));
            levelLabel.setAlignment(Label.CENTER);
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 2;
            c.weightx = 0.3;
            c.weighty = 0.05;
            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(0, 15, 0, 15);
            this.add(levelLabel, c);

            // Add generic button
            button.setBackground(Configuration.getColor("button", "#d4d0c8"));
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 3;
            c.weightx = 0.3;
            c.weighty = 1.0;
            c.anchor = GridBagConstraints.NORTH;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.insets = new Insets(15, 15, 15, 15);
            this.add(button, c);

            if (GetClassInstance() == ViewTopChoices.class) {
                //String[] WeightString=new String[1];
                
                
                //System.out.println(WeightString[0]);
                
              //  JOptionPane.showMessageDialog(null, "noooooo");
                c = new GridBagConstraints();
//                c.gridx = 0;
//                c.gridy = 0;
                c.gridheight = 4;
                c.weightx = .20;
                c.weighty = .2;
                c.anchor = GridBagConstraints.SOUTH;


//                c.fill = GridBagConstraints.BOTH; 
                allboards= new SquareBoard[5];
                for (int i = 0; i < allboards.length; i++) {
                   allboards[i]= new SquareBoard(10, 20);
                    this.add(allboards[i].getComponent(),c);
                }
//                for (SquareBoard myboard : allboards) {
//                    myboard   
//                    ;
//                }
                
                
               
            }

            //addition here
            if (GetClassInstance() == TrainFromFailSequenceTetris.class || GetClassInstance() == TrainFromRegularRunning.class) {
                Button oopsButton = new Button("Oops");
                oopsButton.setBackground(Configuration.getColor("button", "#d4d0c8"));
                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 3;
                c.weightx = 0.3;
                c.weighty = 1.0;
                c.anchor = GridBagConstraints.NORTH;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(150, 100, 100, 100);
                this.add(oopsButton, c);

                Label label = new Label("textLabelhere");

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 3;
                c.weightx = 0.3;
                c.weighty = 1.0;
                c.anchor = GridBagConstraints.NORTH;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(170, 100, 100, 100);
                this.add(label, c);

                oopsButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleOopsButtonPressed();
                        component.requestFocus();
                    }

                    protected void handleOopsButtonPressed() {
                        ((TrainFromFailSequenceTetris) (GetInstance())).OopsButtonPressed();
                    }
                });

            } else {
                WeightSelector.man(CO);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                Autobutton.setBackground(Configuration.getColor("button", "#d4d0c8"));
                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 3;
                c.weightx = 0.3;
                c.weighty = 1.0;
                c.anchor = GridBagConstraints.NORTH;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(50, 50, 50, 50);
                this.add(Autobutton, c);

                speedButton.setBackground(Configuration.getColor("button", "#d4d0c8"));
                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 3;
                c.weightx = 0.3;
                c.weighty = 1.0;
                c.anchor = GridBagConstraints.NORTH;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(100, 100, 100, 100);
                this.add(speedButton, c);
                Autobutton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleAutoButtonPressed();
                        component.requestFocus();
                    }

                    protected void handleAutoButtonPressed() {

                        if (automatic) {
                            automatic = false;
                            thread.sleepTime = 45;
                        } else {

                            automatic = true;
                        }
                    }
                });

                speedButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handlespeedButtonPressed();
                        component.requestFocus();
                    }

                    protected void handlespeedButtonPressed() {
                        if (fast) {
                            level = board.getRemovedLines() / 20;
                            handleLevelModification();
                            fast = false;
                        } else {
                            level = 20;
                            handleLevelModification();
                            thread.adjustSpeed();
                            fast = true;
                        }
                    }
                });
            }

            // Add event handling            
            enableEvents(KeyEvent.KEY_EVENT_MASK);
            this.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    handleKeyEvent(e);
                }
            });
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleButtonPressed();
                    component.requestFocus();
                }
            });
        }

        /**
         * Resizes all the static components, and invalidates the current
         * layout.
         */
        protected void resizeComponents() {
            Dimension size = scoreLabel.getSize();
            Font font;
            int unitSize;

            // Calculate the unit size
            size = board.getComponent().getSize();
            size.width /= board.getBoardWidth();
            size.height /= board.getBoardHeight();
            if (size.width > size.height) {
                unitSize = size.height;
            } else {
                unitSize = size.width;
            }

            // Adjust font sizes
            font = new Font("SansSerif",
                    Font.BOLD,
                    3 + (int) (unitSize / 1.8));
            scoreLabel.setFont(font);
            levelLabel.setFont(font);
            font = new Font("SansSerif",
                    Font.PLAIN,
                    2 + unitSize / 2);
            button.setFont(font);

            // Invalidate layout
            scoreLabel.invalidate();
            levelLabel.invalidate();
            button.invalidate();
        }
    }

    protected void movePiece(PlacementDecider pcp) {
        if (null == pcp) {
            System.out.println("you were null pcp!!!");
            return;
        }
        System.out.println("calling movePiece!!!!");
        Cenario best = pcp.getBest();
        int rot = figure.getRotation();
        if (sout) {
            System.out.println("rot: " + rot);
        }
        while (best.getMyfig().getOrientation() != rot) {
            if (rot < best.getMyfig().getOrientation()) {
                //create some sort of delay
                figure.rotateClockwise();

                //put some sort of delay here
                figure.setRotation(++rot);
            } else {
                figure.rotateCounterClockwise();
                figure.setRotation(--rot);
            }

        }
        int start = 5;
        while (start != pcp.getLeftplace()) {
            if (start > pcp.getLeftplace()) {
                figure.moveLeft();
                start--;
            } else {
                figure.moveRight();
                start++;
            }
        }
        if (automatic) {
            figure.moveAllWayDown();
        }
        if (sout) {
            System.out.println("Figure rotation: " + figure.getRotation());

        }
        System.out.println("done moving piece!");
        //int loc=figure.
    }

}
