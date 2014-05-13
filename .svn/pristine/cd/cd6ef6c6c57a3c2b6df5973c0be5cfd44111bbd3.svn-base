/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrispaul2;

import java.awt.Color;
import java.awt.Point;
import net.percederberg.tetris.Figure;
import net.percederberg.tetris.SquareBoard;

/**
 *
 * @author Paul
 */
public class ByteFig {

    byte[][] board;
    public int type=-1;

    public byte[][] getBoard() {
        return board;
    }
    public void setBoard(byte[][] board) {
        this.board = board;
    }
    public int getDownmost() {
        return downmost;
    }
    public void setDownmost(int downmost) {
        this.downmost = downmost;
    }
    public int getLeftmost() {
        return leftmost;
    }
    public void setLeftmost(int leftmost) {
        this.leftmost = leftmost;
    }
    public int getMaxOrientation() {
        return maxOrientation;
    }
    public void setMaxOrientation(int maxOrientation) {
        this.maxOrientation = maxOrientation;
    }
    public int getOrientation() {
        return orientation;
    }
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    public int getRightmost() {
        return rightmost;
    }
    public void setRightmost(int rightmost) {
        this.rightmost = rightmost;
    }
    public int[] getShapeX() {
        return shapeX;
    }
    public void setShapeX(int[] shapeX) {
        this.shapeX = shapeX;
    }
    public int[] getShapeY() {
        return shapeY;
    }
    public void setShapeY(int[] shapeY) {
        this.shapeY = shapeY;
    }
    public int getUpmost() {
        return upmost;
    }
    public void setUpmost(int upmost) {
        this.upmost = upmost;
    }
    public int getType(){
        return type;
    }
    
    private int maxOrientation;
    private int orientation;
    private int[] shapeX;
    private int[] shapeY;
    private int leftmost = 6;
    private int rightmost = -1;
    private int upmost = 6;
    private int downmost = -1;

    public ByteFig(Figure x) {
        //initialize(x.shapeshape);
        
        board = new byte[5][5];
        maxOrientation = 0; //gets changed in initialize(x.shapeshape)
        orientation = 0;
        shapeX = new int[4];
        shapeY = new int[4];
        type=x.shapeshape;
        initialize(x.shapeshape);
       // initializeRotation(x);
        findedges();

    }
    public ByteFig(int x){
        //initialize(x.shapeshape);
        
        board = new byte[5][5];
        maxOrientation = 0; //gets changed in initialize(x.shapeshape)
        orientation = 0;
        shapeX = new int[4];
        shapeY = new int[4];
        type=x;
        initialize(x);
       // initializeRotation(x);
        findedges();
    }
    public ByteFig(ByteFig x, int typep){
        board = new byte[5][5];
        maxOrientation = x.getMaxOrientation(); //gets changed in initialize(x.shapeshape)
        orientation = x.getOrientation();
        shapeX = new int[4];
        shapeY = new int[4];
        type=typep;
       for (int i = 0; i < 4; i++) {
            shapeX[i]=x.getShapeX()[i];
            shapeY[i]=x.getShapeY()[i];
        }
        leftmost=x.getLeftmost();
        rightmost=x.getRightmost();
        upmost=x.getUpmost();
        downmost=x.getDownmost();
        for (int i = 0; i < x.board.length; i++) {
            System.arraycopy(x.board[i], 0, board[i], 0, board[0].length);
        }
       
        findedges();
        
    }

    private void initialize(int type) {
        this.type=type;

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                board[r][c] = 0;
            }

        }




        // Initialize figure type variables
        switch (type) {
            
            case 1: //square
                maxOrientation = 1;
                // color = Configuration.getColor("figure.square", "#ffd8b1");
                shapeX[0] = -1;
                shapeY[0] = 0;
                shapeX[1] = 0;
                shapeY[1] = 0;
                shapeX[2] = -1;
                shapeY[2] = 1;
                shapeX[3] = 0;
                shapeY[3] = 1;



                break;
            case 2://LINE
                maxOrientation = 2;
                // color = Configuration.getColor("figure.line", "#ffb4b4");
                shapeX[0] = -2;
                shapeY[0] = 0;
                shapeX[1] = -1;
                shapeY[1] = 0;
                shapeX[2] = 0;
                shapeY[2] = 0;
                shapeX[3] = 1;
                shapeY[3] = 0;
                break;
            case 3:  //S FIGURE
                maxOrientation = 2;
                // color = Configuration.getColor("figure.s", "#a3d5ee");
                shapeX[0] = 0;
                shapeY[0] = 0;
                shapeX[1] = 1;
                shapeY[1] = 0;
                shapeX[2] = -1;
                shapeY[2] = 1;
                shapeX[3] = 0;
                shapeY[3] = 1;
                break;
            case 4://Z FIGURE
                maxOrientation = 2;
                //   color = Configuration.getColor("figure.z", "#f4adff");
                shapeX[0] = -1;
                shapeY[0] = 0;
                shapeX[1] = 0;
                shapeY[1] = 0;
                shapeX[2] = 0;
                shapeY[2] = 1;
                shapeX[3] = 1;
                shapeY[3] = 1;
                break;
            case 5:  //RIGHT ANGLE FIGURE
                maxOrientation = 4;
                //   color = Configuration.getColor("figure.right", "#c0b6fa");
                shapeX[0] = -1;
                shapeY[0] = 0;
                shapeX[1] = 0;
                shapeY[1] = 0;
                shapeX[2] = 1;
                shapeY[2] = 0;
                shapeX[3] = 1;
                shapeY[3] = 1;
                break;
            case 6: //LEFT ANGLE FIGURE
                maxOrientation = 4;
                //   color = Configuration.getColor("figure.left", "#f5f4a7");
                shapeX[0] = -1;
                shapeY[0] = 0;
                shapeX[1] = 0;
                shapeY[1] = 0;
                shapeX[2] = 1;
                shapeY[2] = 0;
                shapeX[3] = -1;
                shapeY[3] = 1;
                break;
            case 7: //TRIANGLE FIGURE
                maxOrientation = 4;
                //    color = Configuration.getColor("figure.triangle", "#a4d9b6");
                shapeX[0] = -1;
                shapeY[0] = 0;
                shapeX[1] = 0;
                shapeY[1] = 0;
                shapeX[2] = 1;
                shapeY[2] = 0;
                shapeX[3] = 0;
                shapeY[3] = 1;
                break;
            default:
                throw new IllegalArgumentException("No figure constant: "
                        + type);
        }

        for (int i = 0; i < 4; i++) {
            board[2 + shapeY[i]][2 + shapeX[i]] = 2;

        }
    }

    public void soutFigure() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println("");
        }
    }

    public void rotateCounterClockwise() {
        if (maxOrientation==1) {
            return;
        }
        byte[][] temp = new byte[5][5];
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                temp[r][c] = board[c][4 - r];
            }

        }
        board = temp;
        temp=null;
        orientation--;

    }

    public void rotateClockwise() {
        byte[][] temp = new byte[5][5];
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                temp[r][c] = board[4 - c][r];
            }

        }
        board = temp;
        temp=null;
        orientation++;

    }

    private void initializeRotation(Figure x) {
        System.out.println("Orientation: " + x.getRotation());
        while (orientation < x.getRotation()) {
            rotateClockwise();
           
        }
    }

    private void findedges() {
        leftmost = 6;
        rightmost = -1;
        upmost = 6;
        downmost = -1;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 2) {
                    if (leftmost > c) {
                        leftmost = c;
                    }
                    if (rightmost < c) {
                        rightmost = c;
                    }
                    if (upmost > r) {
                        upmost = r;
                    }
                    if (downmost < r) {
                        downmost = r;
                    }
                }

            }
        }
        
    }

    public void rotateToOrientation(int x) {
        if (x == -1) {
            findedges();
        } else {
            if (x < orientation) {
                while (x != orientation) {
                    rotateCounterClockwise();
                    

                }

            } else {
                while (x != orientation) {
                    rotateClockwise();
                    
                }
            }
            findedges();

        }
    }
    public int getpiecewidth(){
        
        return rightmost-leftmost +1;
    }
    
    public int getpieceheight(){
        
        return downmost-upmost +1;
    }
}
