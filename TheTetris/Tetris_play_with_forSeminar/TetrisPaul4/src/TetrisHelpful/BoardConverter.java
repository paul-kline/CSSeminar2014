/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisHelpful;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import net.percederberg.tetris.Figure;
import net.percederberg.tetris.SquareBoard;
import tetris3.APlacementRecord;
import tetris3.Cenario;
import tetris3.PlacementDecider;

/**
 *
 * @author paul.kline
 */
public class BoardConverter {

    Color ColorForAOne = Color.yellow;
    Color ColorForATwo = Color.RED;

    /**
     *Returns a byte matrix of the squareboard where each red entry of the
     * SquareBoard matrix is converted to a zero (empty space).
     * Empty space is converted to 0's.
     * Filled space (any other color) is converted to a 1.
     * @param SquareBoard the board to be converted
     * @return the converted byte matrix
     */
    public byte[][] ConvertToByte_RedBecomesA_0(SquareBoard SquareBoard) {
        byte[][] byteBoard = new byte[SquareBoard.getBoardHeight()][SquareBoard.getBoardWidth()];
        for (int r = 0; r < byteBoard.length; r++) {
            for (byte c = 0; c < byteBoard[0].length; c++) {
                byteBoard[r][c] = (byte) ((SquareBoard.isSquareEmpty(c, r)) ? 0 : 1);
            }
        }
        return byteBoard;
    }

    /**
     * Returns a byte matrix of the SquareBoard where each red entry of the
     * SquareBoard matrix is converted to a 2.
     * Empty space is converted to 0's.
     * Filled space (any other color) is converted to a 1.
     * @param squareBoard the SquareBoard to be converted.
     * @return the created byte matrix
     */
    public byte[][] ConvertToByte_RedBecomesA_2(SquareBoard squareBoard) {
        byte[][] byteBoard = new byte[squareBoard.getBoardHeight()][squareBoard.getBoardWidth()];
        for (int r = 0; r < byteBoard.length; r++) {
            for (byte c = 0; c < byteBoard[0].length; c++) {
                if (squareBoard.getSquareColor(c, r) == null) {
                    byteBoard[r][c] = (byte) 0;
                } else {
                    if (squareBoard.getSquareColor(c, r) == ColorForATwo) {
                        byteBoard[r][c] = (byte) 2;
                    } else {
                        byteBoard[r][c] = (byte) 1;
                    }
                }
                byteBoard[r][c] = (byte) ((squareBoard.isSquareEmpty(c, r)) ? 0 : 1);
            }
        }
        return byteBoard;
    }

    /**
     *Returns a SquareBoard where each entry that is a 2 in the byte matrix
     * should be treated as though it were a 1. That is, treat it like a
     * regular piece.
     * 
     * @param byteboard the byte matrix to convert
     * @return the converted SquareBoard
     */
    public SquareBoard ConvertToBoard_2_isa_1(byte[][] byteboard) {
        int width = byteboard[0].length;
        int height = byteboard.length;
        SquareBoard squareBoard = new SquareBoard(width, height);
        for (int i = 0; i < byteboard.length; i++) {
            for (int j = 0; j < byteboard[0].length; j++) {
                int g = (int) byteboard[i][j];
                switch (g) {
                    case 0:
                        //continue;
                        squareBoard.setSquareColor(j, i, null);
                        break;
                    case 2:
                    case 1:
                        squareBoard.setSquareColor(j, i, ColorForAOne);
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }
        return squareBoard;
    }

    /**
     * Returns a SquareBoard where a 2 in the byte matrix is converted to
     * a red square in the SquareBoard
     * @param byteboard the byte matrix to convert
     * @return the converted SquareBoard
     */
    public SquareBoard ConvertToBoard_2_isa_RED(byte[][] byteboard) {
        int width = byteboard[0].length;
        int height = byteboard.length;
        SquareBoard squareBoard = new SquareBoard(width, height);

        for (int i = 0; i < byteboard.length; i++) {
            for (int j = 0; j < byteboard[0].length; j++) {
                int g = (int) byteboard[i][j];
                switch (g) {
                    case 0:
                        //continue;
                        squareBoard.setSquareColor(j, i, null);
                        break;
                    case 1:
                        squareBoard.setSquareColor(j, i, ColorForAOne);
                        break;
                    case 2:
                        squareBoard.setSquareColor(j, i, ColorForATwo);
                        break;
                    default:
                        throw new AssertionError();
                }

            }

        }
        return squareBoard;
    }

    /**
     * Returns a byte matrix that contains a specific number to replace
     * the currently falling piece on the SquareBoard.
     * @param squareboard the SquareBoard to be converted
     * @param figure the Figure that is falling on the board
     * @param replacementInt value in the matrix to represent the falling piece 
     * @return
     */
    public byte[][] ConvertToByte_specializeCurrentFigure(SquareBoard squareboard, Figure figure, int replacementInt) {
        byte[][] byteboard = ConvertToByte_RedBecomesA_0(squareboard);

        Point[] points = figure.GetOnBoardLocation();
        for (int i = 0; i < points.length; i++) {
            int x = points[i].x;
            int y = points[i].y;
            if (squareboard.IsInBounds(x, y)) {
                byteboard[y][x] = (byte) replacementInt;
            }

        }
        return byteboard;

    }

    /**
     * In a given SquareBoard, this method does a color replacement
     * @param sqp the SquareBoard the color replacement will take place
     * @param colorout the new color
     * @param colorin the color you want replaced by "colorout"
     */
    public void ConvertThisColorToThisColor(SquareBoard sqp, Color colorout, Color colorin) {
        for (int i = 0; i < sqp.getBoardHeight(); i++) {
            for (int j = 0; j < sqp.getBoardWidth(); j++) {
                if (sqp.getSquareColor(j, i) == colorout) {
                    sqp.setSquareColor(j, i, colorin);
                }

            }

        }
    }

    /**
     * Returns a byte matrix that shrinks the given byte matrix to the specified
     * size. If the inputBoard does not have enough lines, lines are generated
     * and added to the bottom of the matrix so that the returned byte matrix
     * is always the specified size in the parameters.
     * @param inputBoard the byte matrix to shrink
     * @param rowSize the number of rows desired to be returned
     * @param columnSize the number of columns desired to be returned
     * @return the shrunken byte matrix
     */
    public byte[][] ShrinkAndAddRowsToBottomIfNecessary(byte[][] inputBoard, int rowSize, int columnSize) {
        byte[][] shrunkenBoard = new byte[rowSize][columnSize];

        Point p = GetFirstAppearanceOf(inputBoard, (byte) 2);

        int oldBoardRow = p.y;
        List<Integer> potentialBlanks = null;
        for (int r = 0; r < shrunkenBoard.length; r++, oldBoardRow++) {
            //TODO: give createfillerrow the point p to not waste time starting with the whole thing.
            shrunkenBoard[r] = (oldBoardRow < inputBoard.length) ? (byte[]) inputBoard[oldBoardRow].clone() : CreateFillerRow(inputBoard, (byte) 2, potentialBlanks, p);
        }
        return shrunkenBoard;

    }

    private Point GetFirstAppearanceOf(byte[][] inputBoard, byte num) {
        Point p = new Point(-1, -1);
        for (int r = 0; r < inputBoard.length; r++) {
            for (int c = 0; c < inputBoard[0].length; c++) {
                if (inputBoard[r][c] == num) {
                    p.x = c;
                    p.y = r;
                    return p;
                }
            }
        }
        return p;

    }

    private byte[] CreateFillerRow(byte[][] inputBoard, byte avoid, List<Integer> potentialBlanks, Point p) {
        byte[] fillerRow = new byte[inputBoard[0].length];
        //fill entirely with one's first.
        byte one = (byte) 1;
        for (int i = 0; i < fillerRow.length; i++) {
            fillerRow[i] = one;
        }

        //put a blank somewhere NOT under the piece placement.
        //If This hasn't been set yet for the board we are shrinking, set it.
        if (null == potentialBlanks) {
            //GetColumnNoNos returns a list of the columns that have a two in them.
            List<Integer> DontPutABlankHere = GetColumnNoNos(inputBoard, avoid, p);
            //CreatePotentialBlankSpots just does a set subtraction, that is..
            //{setOfColumnIndices} - {DontPutABlankHere}
            potentialBlanks = CreatePotentialBlankSpots(DontPutABlankHere, inputBoard[0].length);
        }

        int INDEXcolumnOfBlank = (int) Math.floor(Math.random() * potentialBlanks.size());
        int columnOfBlank = potentialBlanks.get(INDEXcolumnOfBlank);
        fillerRow[columnOfBlank] = (byte) 0;
        return fillerRow;

    }

    private List<Integer> GetColumnNoNos(byte[][] inputBoard, byte avoid, Point p) {

        List<Integer> NoNoList = new ArrayList<Integer>(4);
        for (int r = p.y; r < inputBoard.length; r++) {
            for (int c = 0; c < inputBoard[0].length; c++) {
                if (inputBoard[r][c] == avoid && !NoNoList.contains((Integer) c)) {
                    NoNoList.add(c);
                }
            }
        }
        return NoNoList;
    }

    private List<Integer> CreatePotentialBlankSpots(List<Integer> DontPutABlankHere, int width) {
        List<Integer> potentialBlankColumns = new ArrayList<Integer>(width - DontPutABlankHere.size());
        for (int i = 0; i < width; i++) {
            if (!DontPutABlankHere.contains(i)) {
                potentialBlankColumns.add(i);
            }
        }
        return potentialBlankColumns;
    }

    /**
     * Returns true or false depending on whether or not the two given
     * byte matrices represent the same matrix. There is an assumption that 
     * they are of the same dimensions.
     * @param b1 the first byte matrix to compare
     * @param b2 the second byte matrix to compare
     * @return true is they represent the same matrix, false otherwise
     */
    public boolean AreEquivelent(byte[][] b1, byte[][] b2) {
        for (int r = 0; r < b1.length; r++) {
            for (int c = 0; c < b1[0].length; c++) {
                if (b1[r][c] != b2[r][c]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a LinkedList of APlacementRecords that is a compilation of all
     * the APlacementRecords in all the files passed to this function.
     * @param files the files (as Object array) to 'harvest' APlacementRecords
     * @return a LinkedList of APlacementRecords
     */
    public LinkedList<APlacementRecord> GetPlacementRecordForFiles(Object[] files) {
        LinkedList<APlacementRecord> resultingRecords = new LinkedList<>();
        for (int i = 0; i < files.length; i++) {
            File file = (File) files[i];
            //adds all the APR's for one file in giant list!
            resultingRecords.addAll(getPlacementRecordForFile(file));
        }
        return resultingRecords;
    }

    /**
     * Returns a LinkedList of APlacementRecords that is a compilation of all
     * the APlacementRecords in the file passed to this function.
     * @param file the File that contains the APlacementRecords
     * @return the LinkedList of APlacementRecords
     */
    public LinkedList<APlacementRecord> getPlacementRecordForFile(File file) {
        String name = file.getName();
        TetrisReader tr = new TetrisReader();
        LinkedList<APlacementRecord> placements = tr.READinEntireFile(file);
        for (APlacementRecord aPlacementRecord : placements) {
            //default the name of each APR to be the name of the file. better
            //than nothin.
            aPlacementRecord.SetName(name);
        }
        return placements;
    }

    /**
     *
     * @param folder
     * @param shrinkto_rows
     * @param shrinkto_columns
     * @param includeMirrors
     * @return
     */
    public List<double[]> ConvertFilesInDirectoryToNeurophFormat(File folder, int shrinkto_rows, int shrinkto_columns, boolean includeMirrors) {
        LinkedList<APlacementRecord> united = GetAllPlacementRecordsInDirectory(folder);

        LinkedList<double[]> resultList = new LinkedList<>();

        for (APlacementRecord apr : united) {
            ConvertoTrainingRow(resultList, apr, shrinkto_rows, shrinkto_columns, includeMirrors);
        }

        return resultList;
    }

    /**
     *
     * @param folder
     * @return
     * @throws HeadlessException
     */
    public LinkedList<APlacementRecord> GetAllPlacementRecordsInDirectory(File folder) throws HeadlessException {
        if (!folder.exists()) {
            JOptionPane.showMessageDialog(null, "Oops! Nothing converted! Folder does not exist.");
        }
        File[] listOfFiles = folder.listFiles();
        LinkedList<Boolean> isgoods = new LinkedList<>();
        LinkedList<File> goodfiles = GetFilesContainingGoodEvidence(listOfFiles);
        LinkedList<File> badfiles = GetFilesContainingBadEvidence(listOfFiles);
        LinkedList<APlacementRecord> goodPlacementRecords = GetPlacementRecordForFiles(goodfiles.toArray());
        LinkedList<APlacementRecord> badPlacementRecords = GetPlacementRecordForFiles(badfiles.toArray());
        for (APlacementRecord apr : goodPlacementRecords) {
            apr.setIsGood(Boolean.TRUE);
        }
        for (APlacementRecord apr : badPlacementRecords) {
            apr.setIsGood(Boolean.FALSE);
        }
        //unite!
        LinkedList<APlacementRecord> united = goodPlacementRecords;
        united.addAll(badPlacementRecords);
        //Note that that just changed goodPlacementRecords to be both of them as well
        return united;
    }

    private boolean IsEvidenceOfGood(String name) {
        return (name.contains("good") || name.contains("GOOD") || name.contains("Good"));
    }

    private boolean IsEvidenceOfBad(String name) {
        return (name.contains("bad") || name.contains("BAD") || name.contains("Bad")
                || name.contains("EVIL") || name.contains("evil") || name.contains("Evil"));
    }

    private LinkedList<File> GetFilesContainingGoodEvidence(File[] listOfFiles) {
        LinkedList<File> resultFiles = new LinkedList<File>();
        for (File file : listOfFiles) {
            String name = file.getName();
            if (IsEvidenceOfGood(name)) {
                resultFiles.add(file);
            }
        }
        return resultFiles;
    }

    private LinkedList<File> GetFilesContainingBadEvidence(File[] listOfFiles) {
        LinkedList<File> resultFiles = new LinkedList<File>();
        for (File file : listOfFiles) {
            String name = file.getName();
            if (IsEvidenceOfBad(name)) {
                resultFiles.add(file);
            }
        }
        return resultFiles;
    }

    /**
     *
     * @param board
     * @param shrinkrow
     * @param shrinkcolumn
     * @return
     */
    public double[] ConvertBoardToInputForNet(byte[][] board, int shrinkrow, int shrinkcolumn) {
        byte[][] sboard = ShrinkAndAddRowsToBottomIfNecessary(board, shrinkrow, shrinkcolumn);
        return ConvertBoardToSingleRow(sboard);

    }

    /**
     *
     * @param resultList
     * @param apr
     * @param shrinkto_rows
     * @param shrinkto_columns
     * @param includeMirror
     */
    public void ConvertoTrainingRow(LinkedList<double[]> resultList, APlacementRecord apr, int shrinkto_rows, int shrinkto_columns, boolean includeMirror) {
        byte[][] shrunkedboard = ShrinkAndAddRowsToBottomIfNecessary(apr.GetBoard(), shrinkto_rows, shrinkto_columns);

        //Note the +1 is for space for the output. 1 being good! 0 being baaaaaad.
        double[] resultArray = new double[shrinkto_columns * shrinkto_rows + 1];
        for (int i = 0; i < shrunkedboard.length; i++) {
            for (int j = 0; j < shrunkedboard[0].length; j++) {
                resultArray[i + j] = shrunkedboard[i][j];
            }
        }
        resultArray[resultArray.length - 1] = ((apr.getIsGood()) ? 1 : -1);
        resultList.add(resultArray);
        if (includeMirror) {
            APlacementRecord apr2 = CreateNewAPRThatIsMirror(apr);
            ConvertoTrainingRow(resultList, apr2, shrinkto_rows, shrinkto_columns, false);
        }
    }

    /**
     *
     * @param apr
     * @return
     */
    public APlacementRecord CreateNewAPRThatIsMirror(APlacementRecord apr) {
        //use the clone constructor
        APlacementRecord aprWithMirrorBoard = new APlacementRecord(apr);
        //change the board to the mirrorimage of the old board.
        aprWithMirrorBoard.setBoard(GetMirrorOfBoard(aprWithMirrorBoard.GetBoard()));
        return aprWithMirrorBoard;

    }

    /**
     *
     * @param oldboard
     * @return
     */
    public byte[][] Duplicate(byte[][] oldboard) {
        byte[][] twinboard = new byte[oldboard.length][oldboard[0].length];
        for (int i = 0; i < oldboard.length; i++) {

            System.arraycopy(oldboard[i], 0, twinboard[i], 0, oldboard[0].length);
        }

        return twinboard;
    }

    private byte[][] GetMirrorOfBoard(byte[][] board) {
        //System.out.println("board is of this dimention: tall: " + board.length + "wide: " + board[0].length);

        byte[][] mymirrorImage = new byte[board.length][board[0].length];
        //System.out.println("mirrorboard is of this dimention: tall: " + mymirrorImage.length + "wide: " + mymirrorImage[0].length);
        for (int r = 0; r < mymirrorImage.length; r++) {
            for (int c = 0; c < mymirrorImage[0].length; c++) {
                // System.out.println(r+" " + c);
                //System.out.println(r+" "+ (board.length-1-c));
                mymirrorImage[r][c] = board[r][board[0].length - 1 - c];
            }
        }
//        
//         TetrisWriter tw = new TetrisWriter();
//
//        System.out.println("normal:");
//        System.out.println(tw.GetStringBoard(board));
//        System.out.println("mirror");
//        System.out.println(tw.GetStringBoard(mymirrorImage));
        return mymirrorImage;
    }

    /**
     *
     * @param receiver
     * @param doner
     */
    public void MakeMyPartsHisParts(SquareBoard receiver, SquareBoard doner) {
        for (int r = 0; r < receiver.getBoardHeight(); r++) {
            for (int c = 0; c < receiver.getBoardWidth(); c++) {
                receiver.setSquareColor(c, r, doner.getSquareColor(c, r));
            }
        }
    }

    private double[] ConvertBoardToSingleRow(byte[][] sboard) {
        double[] resultArray = new double[sboard.length * sboard[0].length];

        for (int i = 0; i < sboard.length; i++) {
            for (int j = 0; j < sboard[0].length; j++) {
                resultArray[i + j] = sboard[i][j];
            }
        }
        return resultArray;

    }

    /**
     *
     * @param placements
     * @return
     */
    public LinkedList<double[]> GetValuesAndIsGood(LinkedList<APlacementRecord> placements) {
        PlacementDecider pd = new PlacementDecider();
        
        LinkedList<double[]> results = new LinkedList<>();
        for (APlacementRecord apr : placements) {
            double[] f = GetValuesAndIsGood(apr);
            results.add(f);
        }
        return results;
    
    
    }

    //for a single placement record, get the values that are multiplied
    //by the weights to determine the static value of the cenario.
    //note that GetValues DOES NOT return the weights.
    /**
     *
     * @param apr
     * @return
     */
    public double[] GetValuesAndIsGood(APlacementRecord apr) {
        Cenario cen = new Cenario(apr.GetBoard(), PlacementDecider.BUTTKICKINWEIGHTS,apr.getPieceCode());
        double[] f= new double[PlacementDecider.BUTTKICKINWEIGHTS.length+1];
        double[] values = cen.Getvalues();
        for (int i = 0; i < f.length-1; i++) {
            f[i]=values[i];
        }
        f[f.length-1]= (float) ((apr.getIsGood())? 1.0 : -1.0);
        return f;
    }
    /**
     *
     * @param board
     * @param curAve
     * @param placements
     * @return
     */
    public double CalculateAveHeight(byte[][] board, double curAve, int placements){
        int height = findHeight(board);
//        System.out.println("Height: "+ height);
        return (curAve*(((double)(placements-1))/placements)) + height*(1.0/placements);
        
        
        
    }

    private int findHeight(byte[][] board) {
        int tallness= board.length;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c]!=0) {
                    return tallness-r;
                }
                
            }            
        }
        return 0;
    }
    
     /**
     *
     * @param orig
     * @return
     */
    public static Object deepCopy(Object orig) {
        Object obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }


}
