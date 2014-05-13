/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bytefigtestering;

import TetrisHelpful.BoardConverter;
import java.util.LinkedList;
import tetris3.APlacementRecord;
import tetris3.ByteFig;
import tetris3.Cenario;
import tetris3.PersonalizedPlacementDecider;
import tetris3.PlacementDecider;

/**
 *
 * @author Paul
 */
public abstract class PaulGame {

    protected byte[][] board;
    protected ByteFig fig1;// = Game.randomFigure();
    protected ByteFig fig2;//= new Figure();
    protected Cenario firstcenario;//= new Cenario();
    protected int score=0;
    protected double AveHeight = 0;
    protected PersonalizedPlacementDecider PplacementDecider;
    protected LinkedList<APlacementRecord> placementHistory;

    public byte[][] getBoard() {
        return board;
    }

    public void setBoard(byte[][] board) {
        this.board = board;
    }

    public ByteFig getFig1() {
        return fig1;
    }

    public void setFig1(ByteFig fig1) {
        this.fig1 = fig1;
    }

    public ByteFig getFig2() {
        return fig2;
    }

    public void setFig2(ByteFig fig2) {
        this.fig2 = fig2;
    }

    public Cenario getFirstcenario() {
        return firstcenario;
    }

    public void setFirstcenario(Cenario firstcenario) {
        this.firstcenario = firstcenario;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getAveHeight() {
        return AveHeight;
    }

    public void setAveHeight(double AveHeight) {
        this.AveHeight = AveHeight;
    }

    public PersonalizedPlacementDecider getPplacementDecider() {
        return PplacementDecider;
    }

    public void setPplacementDecider(PersonalizedPlacementDecider PplacementDecider) {
        this.PplacementDecider = PplacementDecider;
    }

    public LinkedList<APlacementRecord> getPlacementHistory() {
        return placementHistory;
    }

    public void setPlacementHistory(LinkedList<APlacementRecord> placementHistory) {
        this.placementHistory = placementHistory;
    }

    public boolean isKeepHistory() {
        return keepHistory;
    }

    public void setKeepHistory(boolean keepHistory) {
        this.keepHistory = keepHistory;
    }

    public BoardConverter getBc() {
        return bc;
    }

    public void setBc(BoardConverter bc) {
        this.bc = bc;
    }

    public boolean isUseNN() {
        return useNN;
    }

    public void setUseNN(boolean useNN) {
        this.useNN = useNN;
    }

    public int[] getMyWeights() {
        return myWeights;
    }

    public void setMyWeights(int[] myWeights) {
        this.myWeights = myWeights;
    }
    protected boolean keepHistory;
    BoardConverter bc = new BoardConverter();
    protected boolean useNN;
    
    //int w1,w2,w3,w4,w5,w6,w7, w8=0;
    protected int[] myWeights;
}
