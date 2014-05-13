/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bytefigtestering;

import TetrisHelpful.TetrisWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul.kline
 */
public class GameThread implements Runnable {

    private PaulGame1 mygame = null;
    private Thread T;
    private boolean shouldPrintToFile;
    private String wheretoOutput = null;

    public GameThread(PaulGame1 paulgamep) {
        shouldPrintToFile = false;
        //   System.out.println(paulgamep.GetStringWeights());
        mygame = paulgamep;
        T = new Thread(this, ((paulgamep.GetStringWeights()==null)? "No Weights": paulgamep.GetStringWeights()));

    }

    public GameThread(PaulGame1 paulgamep, String outputfile) {
        shouldPrintToFile = true;
        wheretoOutput = outputfile;
        mygame = paulgamep;
        T = new Thread(this, paulgamep.GetStringWeights());

    }

    @Override
    public void run() {
        mygame.PlayGame();
        // System.out.println("FINISHED~~~~ID: "+ mygame.getID() + "  " + mygame.soutMe());

        String details=mygame.soutMe() + 
                " NN:"+ mygame.getMyNNCode().name() +
                " Asking Conditions:"+mygame.getMyGACO().isCareIfTopIsnotFrequent()+" "+ mygame.getMyGACO().isCareIfHoleIsMadeByPlacement();
        mygame.getMyGACO().getProgressGUI().UpdateScoresOutput(details);
        mygame.getMyGACO().getProgressGUI().getTxtAScores().setCaretPosition(mygame.getMyGACO().getProgressGUI().getTxtAScores().getDocument().getLength());

        mygame.getMyGACO().getMyAIPlayer().NotifyUpdateOutput("FINISHED~~ID: " + mygame.getID() + "" + mygame.soutMe());
        mygame.getMyGACO().getProgressGUI().NotifyGameCompleted(mygame);

//        if (shouldPrintToFile) {
//            TetrisWriter tw = new TetrisWriter();
//            
//            tw.AppendToFile(mygame.soutMe(), wheretoOutput);
//        }else{
//            mygame.soutMe();
//        }
    }

    public void PlayMe() {
        T.start();
//        try {
//            T.join();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public PaulGame getMygame() {
        return mygame;
    }

//    public void setMygame(PaulGame mygame) {
//        this.mygame = mygame;
//    }
    public Thread getT() {
        return T;
    }

    public void setT(Thread T) {
        this.T = T;
    }

    public boolean isShouldPrintToFile() {
        return shouldPrintToFile;
    }

    public void setShouldPrintToFile(boolean shouldPrintToFile) {
        this.shouldPrintToFile = shouldPrintToFile;
    }

    public String getWheretoOutput() {
        return wheretoOutput;
    }

    public void setWheretoOutput(String wheretoOutput) {
        this.wheretoOutput = wheretoOutput;
    }
    
    public synchronized void PleasePause(){
        if (null!=T && T.isAlive()) {
            try {
                synchronized(T){
                    T.wait();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}
