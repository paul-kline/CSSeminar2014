/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrispaul4;

import tetrispaul4.WeightSelector;
import NeuralThings.PaulNetworkFactory;
import NeuralThings.PaulNetworkFactory.NNCodes;
import TemplateMethod.AbstractGame;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import net.percederberg.tetris.Game;

/**
 *
 * @author paul.kline
 */
public class ControllerObject implements Serializable {

    protected NNCodes myNNCode = NNCodes.NN3Valuesc;
    protected boolean KeepHistory;
    protected int HistoryHowMany;
    protected boolean ShuffleFails = false;
    protected TrainingSetupGUI myTrainingGUI;
    protected String playerName;
    protected boolean needPlayerName = true;
    protected boolean AutoDrop;
    protected int LevelSpeed;
    protected boolean PauseOnDiff;
    protected AbstractGame myGame;
    protected WeightSelector myConfiger;
    protected Boolean UseNN = true;
    public int[] weights = new int[]{-3, -8, 43, 4, 14, 31, 47, 45};
    protected boolean shouldStartTesting = false;
    protected boolean UseWeights = true;
    protected boolean careIfTopIsnotFrequent;
    protected boolean careIfHoleIsMadeByPlacement;
    private int TopChoicesNumer=-1;
    private boolean TakeFirstPos=false;
    private float myPercentHigherRequirement=Float.NaN;

    public AbstractGame getMyGame() {
        return myGame;
    }

    public NNCodes getNNCode() {
        return myNNCode;
    }

    public boolean ShouldUseWeights() {
        return UseWeights;
    }

    public boolean isCareIfTopIsnotFrequent() {
        return careIfTopIsnotFrequent;
    }

    public void setCareIfTopIsnotFrequent(boolean careIfTopIsnotFrequent) {
        this.careIfTopIsnotFrequent = careIfTopIsnotFrequent;
    }

    public boolean isCareIfHoleIsMadeByPlacement() {
        return careIfHoleIsMadeByPlacement;
    }

    public void setCareIfHoleIsMadeByPlacement(boolean careIfHoleIsMadeByPlacement) {
        this.careIfHoleIsMadeByPlacement = careIfHoleIsMadeByPlacement;
    }

    public int[] getWeights() {
        return weights;
    }

    public void setWeights(int[] weights) {
        this.weights = weights;
    }

    public boolean isShouldStartTesting() {
        return shouldStartTesting;
    }

    public void setShouldStartTesting(boolean shouldStartTesting) {
        this.shouldStartTesting = shouldStartTesting;
    }

    public boolean isAutoDrop() {
        return AutoDrop;
    }

    public void setAutoDrop(boolean AutoDrop) {
        this.AutoDrop = AutoDrop;
    }

    public int getLevelSpeed() {
        return LevelSpeed;
    }

    public void setLevelSpeed(int LevelSpeed) {
        this.LevelSpeed = LevelSpeed;
    }

    public void setMyGame(AbstractGame myGame) {
        this.myGame = myGame;
    }

    public WeightSelector getMyConfiger() {
        return myConfiger;
    }

    public void setMyConfiger(WeightSelector myConfiger) {
        this.myConfiger = myConfiger;
    }

    public Boolean getUseNN() {
        return UseNN;
    }

    public void setUseNN(Boolean UseNN) {
        this.UseNN = UseNN;
    }

    public void setUseWeights(boolean b) {
        UseWeights = b;
    }

    public ControllerObject() {
    }

    public ControllerObject(AbstractGame tgame, WeightSelector ws) {
        myGame = tgame;
        myConfiger = ws;
    }

    void SetAutoDrop(boolean b) {

        AutoDrop = b;
        if (null == myGame) {
            return;
        }
        myGame.SetAutomatic(AutoDrop);
    }

    void setSpeedFast() {
        LevelSpeed = 30;
        if (null == myGame) {
            return;
        }
        myGame.AllowedToChangeScore = false;
        myGame.SetLevel(LevelSpeed);
        myGame.handleLevelModification();
    }

    void setLevelAppropriate() {
        if (null == myGame) {
            return;
        }
        myGame.AllowedToChangeScore = true;
        myGame.handleLevelModification();

    }

    void setCustomSpeedLevel(int parseInt) {
        LevelSpeed = parseInt;
        if (null == myGame) {
            return;
        }
        myGame.AllowedToChangeScore = false;
        myGame.SetLevel(LevelSpeed);
        myGame.handleLevelModification();
    }

    void setWeightSelector(WeightSelector GetInstance) {
        myConfiger = GetInstance;
    }

    void setGame(AbstractGame abgame) {
        myGame = abgame;
    }

    void setUseNN(boolean selected) {
        UseNN = selected;
    }

    void setPauseOnDiff(boolean selected) {
        PauseOnDiff = selected;
    }

    public void BeginIterations() {
        shouldStartTesting = true;
    }

    public void setNN(NNCodes n) {
        myNNCode = n;
    }

    public NNCodes getMyNNCode() {
        return myNNCode;
    }

    public void setMyNNCode(NNCodes myNNCode) {
        this.myNNCode = myNNCode;
    }

    void setKeepHistory(boolean selected, int x) {
        KeepHistory = selected;
        HistoryHowMany = x;
    }

    void setShuffleFails(boolean selected) {
        ShuffleFails = selected;
    }

    public boolean GetShuffleFails() {
        return ShuffleFails;
    }

    public void handleFailSetTrainingCompleted(File FailFile) {

        JOptionPane.showMessageDialog(null, "Thanks! One Moment while I write this down...");

        int i = myTrainingGUI.StringIsInThisDictionaryAtLittleIndex(FailFile.getName(), myTrainingGUI.getDic(), 1);
        if (i >= 0) {
            myTrainingGUI.getDic().get(i)[0] = "C";
        }
        myTrainingGUI.ReWriteFileInfoFile();
        myTrainingGUI.updateLists();
        myTrainingGUI.repaint();
        myGame.quit();
    }

    public void setmyTrainingGUI(TrainingSetupGUI g) {
        myTrainingGUI = g;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setNeedPlayerName(boolean b) {
        this.needPlayerName = b;
    }

    public boolean getStillNeedPlayerName() {
        return needPlayerName;
    }

    public String GetPlayerName() {
        return playerName;
    }

    public boolean getAutoDrop() {
        return AutoDrop;
    }

    public boolean ShouldKeepHistory() {
        return KeepHistory;
    }

    public void setKeepHistory(boolean KeepHistory) {
        this.KeepHistory = KeepHistory;
    }

    public int getHistoryHowMany() {
        return HistoryHowMany;
    }

    public void setHistoryHowMany(int HistoryHowMany) {
        this.HistoryHowMany = HistoryHowMany;
    }

    public TrainingSetupGUI getMyTrainingGUI() {
        return myTrainingGUI;
    }

    public void setMyTrainingGUI(TrainingSetupGUI myTrainingGUI) {
        this.myTrainingGUI = myTrainingGUI;
    }

    public boolean isShuffleFails() {
        return ShuffleFails;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isNeedPlayerName() {
        return needPlayerName;
    }

    public boolean isUseWeights() {
        return UseWeights;
    }

    public boolean ShouldPauseOnDiff() {
        return PauseOnDiff;
    }

    public int getTopChoicesNumber() {
        return TopChoicesNumer;
    }
    public void setTopChoicesNumber(int x){
        TopChoicesNumer=x;
    }

    void setTakeFirstPositive(boolean selected) {
        TakeFirstPos=selected;
    }
    public boolean getTakeFirstPositive(){
        return TakeFirstPos;
    }

    void setPercentHigherRequirement(float input) {
        myPercentHigherRequirement=input;
    }
    public float getmyPercentHigherRequirement(){
        return myPercentHigherRequirement;
    }
}
