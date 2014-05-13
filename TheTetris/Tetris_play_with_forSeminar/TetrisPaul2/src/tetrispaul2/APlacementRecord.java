/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrispaul2;

import TetrisFactory.HelpfulStuff.BoardConverter;



/**
 *
 * @author paul.kline
 */
public class APlacementRecord {

    private byte[][] board;
    private int pieceCode;

    public int getPieceCode() {
        return pieceCode;
    }

    public void setPieceCode(int pieceCode) {
        this.pieceCode = pieceCode;
    }
    private String name = "";

    public Boolean getIsGood() {
        return isGood;
    }

    public void setIsGood(Boolean isGood) {
        this.isGood = isGood;
    }

    public byte[][] getBoard() {
        return board;
    }

    public void setBoard(byte[][] board) {
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean IsisGoodNull() {
        return (isGood == null);
    }
    private Boolean isGood;

    public APlacementRecord(int pcode, byte[][] boardp, String namep, boolean isgoodp) {
        board = boardp;
        pieceCode = pcode;
        name = namep;
        isGood = isgoodp;
    }

    public APlacementRecord(APlacementRecord aprp) {
        BoardConverter bx = new BoardConverter();
        this.isGood = aprp.isGood;
        this.board = bx.Duplicate(aprp.GetBoard());
        this.name = aprp.GetName();
        this.pieceCode = aprp.getPieceCode();

    }

    public APlacementRecord(int pcode, byte[][] boardp, String namep) {
        board = boardp;
        pieceCode = pcode;
        name = namep;
    }

    public APlacementRecord(int pcode, byte[][] boardp) {
        board = boardp;
        pieceCode = pcode;

    }

    public APlacementRecord() {
    }

    @Override
    public String toString() {
        return "Piece:\n" + pieceCode + "\nBoard:\n" + GetStringBoard(board);

    }

    public String GetStringBoard(byte[][] board) {
        String string = "";
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                string += (board[r][c] + " ");
            }
            string += '\n';
        }
        string += '\n';
        return string;
    }

    public byte[][] GetBoard() {
        return board;
    }

    public String GetName() {
        return name;
    }

    public void AppendToName(String text) {
        name += text;
    }

    public void SetName(String namep) {
        name = namep;
    }
}
