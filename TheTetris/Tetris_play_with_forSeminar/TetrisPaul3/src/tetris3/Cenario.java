/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris3;

import TetrisHelpful.BoardConverter;

/**
 *
 * @author Paul
 */
public class Cenario {
    // int rotation;

    byte[][] myboard;
    private byte[][] myboardAfterPlacementBeforeLinesClearedBeforeAllOnes;
    ByteFig myfig;
    short piecewidth = -1;
    short pieceheight = -1;
    byte[] rr= new byte[4];
    byte[] cc= new byte[4];
    Cenario cen2 = null;
    public double staticvalue = 0;
    private boolean sout = false;
    public short linescleared = 0;

    //recent change
    int myLeftPlacement;

    public Cenario(byte[][] boardp, int[] weights, int figure) {
        myboard = DuplicateBoard(boardp);
        myfig= new ByteFig(figure);
        SetWeights(weights);
        //setPieceLocs();
        SetccAndrrUsingThisMarker((byte)2);
        setStaticValue();
        removeLines();
    }

    public Cenario(byte[][] boardp, ByteFig figp, int leftpos) {
        rr = new byte[4];
        cc = new byte[4];
        for (int i = 0; i < 4; i++) {
            rr[i] = 0;
            cc[i] = 0;
        }
        myboard = DuplicateBoard(boardp);
        myfig = null;
        myfig = new ByteFig(figp, figp.type);
        piecewidth = (short) (myfig.getRightmost() - myfig.getLeftmost() + 1);
        pieceheight = (short) (myfig.getDownmost() - myfig.getUpmost() + 1);
        setPieceLocs();
        setcc(leftpos);
        placePiece(leftpos);

    }

    public byte[][] DuplicateBoard(byte[][] board) {
        BoardConverter bc = new BoardConverter();
        return bc.Duplicate(board);
    }

    public Cenario(byte[][] boardp, ByteFig figp, int leftpos1, ByteFig figp2, int leftpos2) {
        this(boardp, figp, leftpos1);
        cen2 = new Cenario(myboard, figp2, leftpos2);
        staticvalue += cen2.staticvalue;
        // setStaticValue();
        myboard = cen2.myboard;

    }

    public Cenario(byte[][] boardp, ByteFig figp, int leftpos1, int[] weights) {

        SetWeights(weights);

        rr = new byte[4];
        cc = new byte[4];
        for (int i = 0; i < 4; i++) {
            rr[i] = 0;
            cc[i] = 0;
        }
        myboard = new byte[boardp.length][boardp[0].length];
        for (int i = 0; i < boardp.length; i++) {

            System.arraycopy(boardp[i], 0, myboard[i], 0, boardp[0].length);
        }
        myfig = null;
        myfig = new ByteFig(figp, figp.type);
        //myfig=figp;

        // myfig.board=(byte[][]) figp.board.clone();
        piecewidth = (short) (myfig.getRightmost() - myfig.getLeftmost() + 1);
        pieceheight = (short) (myfig.getDownmost() - myfig.getUpmost() + 1);
        setPieceLocs();
        setcc(leftpos1);
        placePiece(leftpos1);

    }

    public Cenario(byte[][] boardp, ByteFig figp, int leftpos1, ByteFig figp2, int leftpos2, int[] weights) {//int w1, int w2, int w3, int w4, int w5, int w6, int w7, int w8) {
        this(boardp, figp, leftpos1, weights);//w1,w2,w3,w4,w5,w6,w7,w8);
        cen2 = new Cenario(myboard, figp2, leftpos2, weights);//w1,w2,w3,w4,w5,w6,w7,w8);

        //setStaticValue();
        //deliberately don't set myboard to cen2.myboard;
        staticvalue += cen2.staticvalue;
    }

    public void SetccAndrrUsingThisMarker(byte marker){
        int index=0;
     //   System.out.println((null==myboard)? "NULL!!!!!!!!" : "not null");
        for (int r = 0; r < myboard.length; r++) {
            for (int c = 0; c < myboard[0].length; c++) {
                if (myboard[r][c]==marker) {
                    rr[index]=(byte) r;
                    cc[index]=(byte) c;
                    index++;
                    if (index==4) {
                        return;
                    }
                            
                }
                
            }
            
        }
        
    }
    private void setPieceLocs() {
        byte a = 0;

        for (int r = 0; r < myfig.board.length; r++) {
            for (int c = 0; c < myfig.board[0].length; c++) {
                if (myfig.board[r][c] == 2) {
                    rr[a] = (byte) r;
                    cc[a] = (byte) c;
                    a++;
                    if (a == 4) {
                        return;
                    }
                }
            }

        }
    }

    private boolean canMoveDown(byte[] rp, byte[] cp) {

        for (int i = 0; i < 4; i++) {

            if ((rp[i] + 1) >= myboard.length || myboard[rp[i] + 1][cp[i]] == 1) {
                return false;
            }
        }
        return true;

    }

    private void moveAllWayDown(byte[] rp, byte[] cp) {
        if (canMoveDown(rp, cp)) {
            for (int i = 0; i < 4; i++) {
                rp[i]++;
            }
            moveAllWayDown(rp, cp);
        }
    }

    private void setcc(int leftpos) {
        myLeftPlacement = leftpos;
        for (int i = 0; i < 4; i++) {
            cc[i] += leftpos - myfig.getLeftmost();
        }

    }

    private void placePiece(int leftpos) {
        moveAllWayDown(rr, cc);
        myboardAfterPlacementBeforeLinesClearedBeforeAllOnes = DuplicateBoard(myboard);
        MakeTheRRandTheCCAllThisNumber(myboardAfterPlacementBeforeLinesClearedBeforeAllOnes, (byte) 2);
        makeOnes();
        setStaticValue();
        removeLines();
    }

    private void makeOnes() {
        MakeTheRRandTheCCAllThisNumber(myboard, (byte) 1);

    }

    public void soutBoard() {
        soutBoard(myboard);
    }

    public void soutMyWeights() {
        System.out.println("** " + zeroweight + ", " + directzerosunderweight
                + ", " + linesclearedweight + ", " + flatratioweight + ", "
                + heightweight + ", " + sidestouchingpiecesweight + ", "
                + sidestouchingedgesweight + ", " + directpieceunderweight);

    }
    double zeroweight = -6;  //-3, -24, 22, -15, 6, 10, 17  32561
    double directzerosunderweight = -7; //  69995 ** -9, -36, 30, 47, 18, 24, 45, -4
    double linesclearedweight = 47;      //Score: 283230 ** -3, -8, 43, 4, 14, 31, 47, 45
    double flatratioweight = 7;          //378839 -11, -14, 4, -26, 21, 21, 44, 44
    double heightweight = 14;              //305618 ** -3, -17, 16, -6, 19, 18, 25, 35 
    double sidestouchingpiecesweight = 34;//Score: 1652546 ** -6, -7, 43, 4, 14, 32, 52, 48!!!!!
    double sidestouchingedgesweight = 50;  //1904732	-6	-7	47	7	14	34	50	41
    double directpieceunderweight = 41;
    public short height = 0;
    public double flatratio = 0;
    public short zero = 0;
    public short directzerosunder = 0;
    //linescleared is elsewhere;
    public short sidestouchingpieces = 0;
    public short sidestouchingedges = 0;
    public short directpeiceunder = 0;

    private void setStaticValue() {

        //int[] zeros={0,0,0,0,0,0,0};
        //SetWeights(zeros);
        height = 0;
        flatratio = 0;
        zero = 0;
        directzerosunder = 0;
        //@@@@@@@@@@@linescleared is elsewhere;
        sidestouchingpieces = 0;
        sidestouchingedges = 0;
        directpeiceunder = 0;

        zero = getZerosUnder();
        directzerosunder = getDirectZerosUnder();
        flatratio = (float) (myfig.getpiecewidth()) / (float) (myfig.getpieceheight());
        height = getHeightValues();
        sidestouchingpieces = getSidesTouchingPiecesvalue();
        sidestouchingedges = getSidesTouchingEdges();
        directpeiceunder = getDirectPieceUnder();

        staticvalue += directzerosunder * directzerosunderweight;
        staticvalue += flatratio * flatratioweight;
        staticvalue += (zero * zeroweight);
        //  staticvalue += linescleared * linesclearedweight;
        staticvalue += height * heightweight;
        staticvalue += sidestouchingpieces * sidestouchingpiecesweight;
        staticvalue += sidestouchingedges * sidestouchingedgesweight;
        staticvalue += directpeiceunder * directpieceunderweight;

    }

    private short getZerosUnder() {
        short count = 0;
        for (int i = 0; i < 4; i++) {

            for (int j = rr[i] + 1; j < myboard.length; j++) {
                if (myboard[j][cc[i]] == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private short getDirectZerosUnder() {
        short count = 0;
        for (int i = 0; i < 4; i++) {
            if (rr[i] + 1 < myboard.length && myboard[rr[i] + 1][cc[i]] == 0) {
//                System.out.println((rr[i]+1) + " " + cc[i]);
//                soutBoard();
                count++;
            }
        }
        return count;
    }

    private boolean isfullline(byte[] boardp) {
        for (int i = 0; i < boardp.length; i++) {
            if (boardp[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private void removeLines() {
        for (int r = 0; r < myboard.length; r++) {
            if (isfullline(myboard[r])) {
                linescleared++;
                removeLine(r);
            }
        }
        staticvalue += linescleared * linesclearedweight;
    }

    private void removeLine(int rp) {
        for (int r = rp; r > 0; r--) {
            for (int c = 0; c < myboard[0].length; c++) {
                myboard[r][c] = myboard[r - 1][c];
            }
        }
        for (int i = 0; i < myboard[0].length; i++) {
            myboard[0][i] = 0;
        }
    }

    private short getHeightValues() {
        short height = 0;
        for (int i = 0; i < 4; i++) {
            height += rr[i];
        }
        return height;
    }

    private short getSidesTouchingPiecesvalue() {
        short touches = 0;
        for (int i = 0; i < 4; i++) {
            if (cc[i] - 1 >= 0 && cc[i] + 1 < myboard[0].length) {
                if ((!isme(rr[i], (byte) (cc[i] - 1))) && myboard[rr[i]][cc[i] - 1] != 0) {
                    touches++;
                }
                if ((!isme(rr[i], (byte) (cc[i] + 1))) && myboard[rr[i]][cc[i] + 1] != 0) {
                    touches++;
                }
            }
        }
        return touches;
    }

    private boolean isme(byte rp, byte cp) {

        for (int i = 0; i < 4; i++) {
            if (rp == rr[i] && cp == cc[i]) {
                return true;
            }
        }
        return false;
    }

    private short getSidesTouchingEdges() {
        short touches = 0;
        for (int i = 0; i < 4; i++) {
            if (cc[i] == 0 || cc[i] == myboard[0].length - 1) {
                touches++;
            }
        }
        return touches;
    }

    private short getDirectPieceUnder() {
        short count = 0;
        for (int i = 0; i < 4; i++) {
            if (rr[i] + 1 == myboard.length || (rr[i] + 1 < myboard.length
                    && (!isme((byte) (rr[i] + 1), cc[i])) && myboard[rr[i] + 1][cc[i]] == 1)) {
                count++;
            }
        }

        return count;

    }

    public byte[] getCc() {
        return cc;
    }

    public void setCc(byte[] cc) {
        this.cc = cc;
    }

    public Cenario getCen2() {
        return cen2;
    }

    public void setCen2(Cenario cen2) {
        this.cen2 = cen2;
    }

    public byte[][] getMyboard() {
        return myboard;
    }

    public void setMyboard(byte[][] myboard) {
        this.myboard = myboard;
    }

    public ByteFig getMyfig() {
        return myfig;
    }

    public void setMyfig(ByteFig myfig) {
        this.myfig = myfig;
    }

    public short getPieceheight() {
        return pieceheight;
    }

    public void setPieceheight(short pieceheight) {
        this.pieceheight = pieceheight;
    }

    public short getPiecewidth() {
        return piecewidth;
    }

    public void setPiecewidth(short piecewidth) {
        this.piecewidth = piecewidth;
    }

    public byte[] getRr() {
        return rr;
    }

    public void setRr(byte[] rr) {
        this.rr = rr;
    }

    public boolean isSout() {
        return sout;
    }

    public void setSout(boolean sout) {
        this.sout = sout;
    }

    public double getStaticvalue() {
        return staticvalue;
    }

    private void SetWeights(int[] weights) {
        //int w1, int w2, int w3, int w4, int w5, int w6, int w7, int w8) {
        zeroweight = weights[0];//w1;
        directzerosunderweight = weights[1];//w2;
        linesclearedweight = weights[2];//w3;
        flatratioweight = weights[3];//w4;
        heightweight = weights[4];//w5;
        sidestouchingpiecesweight = weights[5];//w6;//1
        sidestouchingedgesweight = weights[6];//w7;
        directpieceunderweight = weights[7];//w8;
    }

    public byte[][] getMyboardAfterPlacementBeforeLinesClearedBeforeAllOnes() {
        return myboardAfterPlacementBeforeLinesClearedBeforeAllOnes;
    }

    public void soutBoard(byte[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private void MakeTheRRandTheCCAllThisNumber(byte[][] board, byte ip) {
        for (int i = 0; i < 4; i++) {

            board[rr[i]][cc[i]] = ip;
        }
    }
    
    public double[] Getvalues(){
        double[] values = new double[PlacementDecider.BUTTKICKINWEIGHTS.length];
//        w1 (total blanks under)	
//        w2 (blanks imiediatly under placement)	
//        w3 (lines cleared)	
//        w4 (width/height)	
//                w5 (hight)	
//                w6 (touching another piece)	
//        w7 (touching edge)	
//        w8 (piece/floor immediately under me)
//        
        int i=0;
        values[i++]=zero;
        values[i++]=directzerosunder;
        values[i++]=linescleared;
        values[i++]=flatratio;
        values[i++]=height;
        values[i++]=sidestouchingpieces;
        values[i++]=sidestouchingedges;
        values[i++]=directpeiceunder;
       return values;
    }

}
