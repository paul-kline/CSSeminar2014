/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package bytefigtestering;

import GUIs.Progress;
import java.util.ArrayList;
import java.util.LinkedList;
import tetrispaul4.ControllerObject;

/**
 *
 * @author paul.kline
 */
public class GAControllerObject extends ControllerObject {

    private boolean[] ShouldMutateWeightsArray;
    private LinkedList<Seed> seeds;
    private int generations;
    private int gamesPerSeed;
    private int chooseTopXfromEachSeed;
    private Progress myProgrssGUI;
    private AIPlayer myAIPlayer;
    private boolean PAUSEEVERYTHING = false;
    private ArrayList<GameThread> CurrentlyRunningThreads= new ArrayList<>();

    public ArrayList<GameThread> getCurrentlyRunningThreads() {
        return CurrentlyRunningThreads;
    }

    public void setCurrentlyRunningThreads(ArrayList<GameThread> CurrentlyRunningThreads) {
        this.CurrentlyRunningThreads = CurrentlyRunningThreads;
    }

    public AIPlayer getMyAIPlayer() {
        return myAIPlayer;
    }

    public void setMyAIPlayer(AIPlayer aip) {
        myAIPlayer = aip;
    }

    public boolean[] getShouldMutateWeightsArray() {
        return ShouldMutateWeightsArray;
    }

    public void setShouldMutateWeightsArray(boolean[] ShouldMutateWeightsArray) {
        this.ShouldMutateWeightsArray = ShouldMutateWeightsArray;
    }

    public LinkedList<Seed> getSeeds() {
        return seeds;
    }

    public void setSeeds(LinkedList<Seed> seeds) {
        this.seeds = seeds;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public int getGamesPerSeed() {
        return gamesPerSeed;
    }

    public void setGamesPerSeed(int gamesPerSeed) {
        this.gamesPerSeed = gamesPerSeed;
    }

    public int getChooseTopXfromEachSeed() {
        return chooseTopXfromEachSeed;
    }

    public void setChooseTopXfromEachSeed(int chooseTopXfromEachSeed) {
        this.chooseTopXfromEachSeed = chooseTopXfromEachSeed;
    }

    public void setProgressGUI(Progress progress) {
        myProgrssGUI = progress;
    }

    public Progress getProgressGUI() {
        if (null == myProgrssGUI) {
            myProgrssGUI = new Progress(this);
        }
        return myProgrssGUI;
    }

    public int CalculateTotal() {
        int seedcount = seeds.size();
        int gen1GameCount = seedcount * gamesPerSeed;
        int total = gen1GameCount;
        for (int i = 1; i < generations; i++) {
            seedcount = seedcount * chooseTopXfromEachSeed;
            total += seedcount * gamesPerSeed;

        }
        return total;
    }

}