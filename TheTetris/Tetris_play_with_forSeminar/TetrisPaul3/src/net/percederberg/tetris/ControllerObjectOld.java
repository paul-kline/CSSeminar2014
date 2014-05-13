/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.percederberg.tetris;

import NeuralThings.PaulNetworkFactory;
import NeuralThings.PaulNetworkFactory.NNCodes;
import TetrisFactory.WeightsWithNNTetris;

/**
 *
 * @author paul.kline
 */
public class ControllerObjectOld {
  
    private NNCodes myNNCode=NNCodes.NN3Valuesc;
    public NNCodes getNNCode(){ return myNNCode;}
    public int[] weights= new int[]{-3, -8, 43, 4, 14, 31,47, 45};
    private boolean shouldStartTesting=false;
    private boolean UseWeights=true;
    public boolean isUseWeights(){return UseWeights;}
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

    public Game getMyGame() {
        return myGame;
    }

    public void setMyGame(Game myGame) {
        this.myGame = myGame;
    }

    public WeightSelectorOld getMyConfiger() {
        return myConfiger;
    }

    public void setMyConfiger(WeightSelectorOld myConfiger) {
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
        myGame.setUseWeights(UseWeights);
    }
    private boolean AutoDrop;
    private int LevelSpeed;
    private boolean PauseOnDiff;
    Game myGame;
    WeightSelectorOld myConfiger;
    private Boolean UseNN=true;
   
    
    public ControllerObjectOld(){
     
    }
    public ControllerObjectOld(Game tgame, WeightSelectorOld ws){
        myGame=tgame;
        myConfiger=ws;
    }
    void SetAutoDrop(boolean b){
        
        AutoDrop=b;
        if(null==myGame) return;
        myGame.automatic=AutoDrop;
    }
    void setSpeedFast() {
        LevelSpeed=30;
        if(null==myGame) return;
        myGame.AllowedToChangeScore=false;
        myGame.level=LevelSpeed;
        myGame.handleLevelModification();
    }

    void setLevelAppropriate() {
        if(null==myGame) return;
        myGame.AllowedToChangeScore=true;
        myGame.handleLevelModification();
        
    }

    void setCustomSpeedLevel(int parseInt) {
        LevelSpeed= parseInt;
        if(null==myGame) return;
        myGame.AllowedToChangeScore=false;
        myGame.level=LevelSpeed;
        myGame.handleLevelModification();
    }

    void setWeightSelector(WeightSelectorOld GetInstance) {
        myConfiger=GetInstance;
    }


    void setGame(Game GetInstance) {
        myGame= GetInstance;
    }

    void setUseNN(boolean selected) {
        UseNN=selected;
        if(null==myGame) return;
        ((WeightsWithNNTetris)myGame).UseNN=UseNN;
    }

    void setPauseOnDiff(boolean selected) {
        PauseOnDiff=selected;
        if(null==myGame) return;
        ((WeightsWithNNTetris)myGame).PauseOnDiff=PauseOnDiff;
    }

    public void BeginIterations() {
        shouldStartTesting=true;
    }
    public void setNN(NNCodes n){
        myNNCode=n;
        myGame.setUseThisNN(myNNCode);
    }

    public NNCodes getMyNNCode() {
        return myNNCode;
    }

    public void setMyNNCode(NNCodes myNNCode) {
        this.myNNCode = myNNCode;
    }
   
}
