package bytefigtestering;

import GUIs.Progress;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tetris3.Cenario;
import tetris3.PlacementDecider;
/**
 *
 * @author Paul
 */
public class AIPlayer implements Runnable {

    private int improvementcounter = 0;
    private PaulGame1[] population = null;
    private int numSeedsInPop = -1;
    private LinkedList<PaulGame1> nextpop = new LinkedList<>();

    private boolean sout = false;
    private Progress progressGUI;
//    private ByteFigTestering mybft;
    private GAControllerObject myGACO;

    public AIPlayer(GAControllerObject gaco) {
        myGACO = gaco;
        numSeedsInPop = gaco.getSeeds().size();
    }

    public void PlayGameUsingGaco() {
        population = CreatePopulationFromSeeds();
        System.out.println("finished creating initial population");
        NotifyUpdateOutput("finished creating initial population");
        PlayPopulation(population);
        System.out.println("done playing first population");
        NotifyUpdateOutput("done playing first population");
        for (int i = 1; i < myGACO.getGenerations(); i++) {
            NotifyUpdateOutput("GENERATION: " + (i + 1));
            population = CreateNextPop();
            NotifyUpdateOutput("population length: " + population.length);

            PlayPopulation(population);
        }
        myGACO.getProgressGUI().setCompleted();
        NotifyUpdateOutput("Completed!!!!");
    } 


    //play all the games, but save the output of each game to this file.
    //hope it works with threads!!!
    public void GivePopulationLife(String recordOutcomesHere) {
        for (PaulGame1 paulGame : population) {
            GameThread gt = new GameThread(paulGame, recordOutcomesHere);
            gt.PlayMe();
        }
    }

    //play all the games!
    public void GivePopulationLife() {
        for (PaulGame1 paulGame : population) {
            GameThread gt = new GameThread(paulGame);
            gt.PlayMe();
        }
    }

    public int getImprovementcounter() {
        return improvementcounter;
    }

    public void setImprovementcounter(int improvementcounter) {
        this.improvementcounter = improvementcounter;
    }

    public PaulGame[] getPopulation() {
        return population;
    }

    public Progress getProgressGUI() {
        return progressGUI;
    }

    public void setProgressGUI(Progress progressGUI) {
        this.progressGUI = progressGUI;
    }

    private PaulGame1[] CreatePopulationFromSeeds() {
        PaulGame1[] pgs = new PaulGame1[myGACO.getSeeds().size() * 
                myGACO.getGamesPerSeed()];
        for (int i = 0; i < myGACO.getSeeds().size(); i++) {
            for (int j = 0; j < myGACO.getGamesPerSeed(); j++) {
                System.out.println("creating NN");
                pgs[i * myGACO.getGamesPerSeed() + j] = new PaulGame1(
                        myGACO.getSeeds().get(i), this.myGACO, "" + i);
            }

        }
        return pgs;
    }

    public GAControllerObject getMyGACO() {
        return myGACO;
    }

    public void setMyGACO(GAControllerObject myGACO) {
        this.myGACO = myGACO;
    }

    private void PlayPopulation(PaulGame1[] pop) {
        GameThread[] gts= new GameThread[pop.length];
        
        for (int i = 0; i < pop.length; i++) {
            gts[i] = new GameThread(pop[i]);
            myGACO.getCurrentlyRunningThreads().add(gts[i]);
            gts[i].PlayMe();
        }
        for (int i = 0; i < gts.length; i++) {
            try {
     //           System.out.println("THREAD JOINED!!!!");              
                gts[i].getT().join();
                myGACO.getCurrentlyRunningThreads().remove((GameThread)gts[i]);
            } catch (InterruptedException ex) {
                Logger.getLogger(AIPlayer.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }

    private PaulGame1[] CreateNextPop() {
        System.out.println("numseedsinPop: " + numSeedsInPop);
        numSeedsInPop = numSeedsInPop * myGACO.getChooseTopXfromEachSeed();
        PaulGame1[] resultArray = new PaulGame1[numSeedsInPop * myGACO.
                getGamesPerSeed()];
        LinkedList<PaulGame1> resultList = new LinkedList<>();
        LinkedList<LinkedList<PaulGame1>> massiveList = getMassiveList();
        PaulGame1[] newseeds = HarvestOldPopForNewPopSeeds(massiveList);
        for (int i = 0; i < newseeds.length; i++) {
            for (int j = 0; j < myGACO.getGamesPerSeed(); j++) {
                resultArray[i * myGACO.getGamesPerSeed() + j] = new PaulGame1(
                        newseeds[i], newseeds[i].getID());
            }
        }
        System.out.println("THE SIZE CREATENEXTPOP IS RETURNING IS: " + 
                resultArray.length);
        return resultArray;
    }

    private LinkedList<LinkedList<PaulGame1>> getMassiveList() {
        LinkedList<LinkedList<PaulGame1>> superlist = new LinkedList<>();
        for (int i = 0; i < population.length; i++) {
            int index = groupWithThisIDExist(superlist, population[i].getID());
            if (index == -1) {
                System.out.println("making a new list with this idgroup!!: " + 
                        population[i].getID());
                LinkedList<PaulGame1> newlist = new LinkedList<>();
                newlist.add(population[i]);
                superlist.add(newlist);
            } else {
                superlist.get(index).add(population[i]);
            }

        }
        System.out.println("superlistsize: " + superlist.size());
        return superlist;

    }

    private int groupWithThisIDExist(LinkedList<LinkedList<PaulGame1>> 
            superlist, String id) {
        for (LinkedList<PaulGame1> linkedList : superlist) {
            if (linkedList.peek().getID().equalsIgnoreCase(id)) {
                return superlist.indexOf(linkedList);
            }
        }
        return -1;
    }

    private PaulGame1[] HarvestOldPopForNewPopSeeds(
            LinkedList<LinkedList<PaulGame1>> massiveList) {
        LinkedList<PaulGame1> newPopSeeds = new LinkedList<>();
 //       System.out.println("hereererer");

        for (LinkedList<PaulGame1> linkedList : massiveList) {
            LinkedList<PaulGame1> currTopForGroup = new LinkedList<>();
            int lowestBest = -1;
            currTopForGroup.add(new PaulGame1(lowestBest));
            //get the top x game scores of each id group.
            for (PaulGame1 pg1 : linkedList) {
                if (pg1.getScore() > lowestBest) {
                    PerformProperInsertion(pg1, currTopForGroup, 
                            myGACO.getChooseTopXfromEachSeed());
                    lowestBest = currTopForGroup.getLast().getScore();
                }
            }
//            set new id's.
            for (int i = 0; i < currTopForGroup.size(); i++) {

                currTopForGroup.get(i).setID(currTopForGroup.get(i).getID() 
                        + "" + i);
//                debugging sout to see if working correctly                
            }

            newPopSeeds.addAll(currTopForGroup);
            currTopForGroup = null;
        }
        Object[] needconversion = newPopSeeds.toArray();
        PaulGame1[] result = new PaulGame1[needconversion.length];
        for (int i = 0; i < needconversion.length; i++) {
            result[i] = (PaulGame1) needconversion[i];

        }
        return result;
    }

    public void PerformProperInsertion(PaulGame1 pg1, LinkedList<PaulGame1> 
            currTopForGroup, int desiredListSize) {

        int index = currTopForGroup.size();
        PaulGame1 pglow = currTopForGroup.getLast();
       
        if (pg1.getScore() > pglow.getScore()) {
            while (pg1.getScore() > pglow.getScore() && index >= 0) {
                pglow = (--index > -1) ? currTopForGroup.get(index) : pglow;
            }
            currTopForGroup.add(index + 1, pg1);
        }
        while (currTopForGroup.size() > desiredListSize) {
            currTopForGroup.removeLast();
        }
    }

    synchronized void NotifyUpdateOutput(String string) {
 //              System.out.println("supposedly appending!!" + string);
        myGACO.getProgressGUI().getTxtaOutput().append(string + "\n");
        myGACO.getProgressGUI().getTxtaOutput().setCaretPosition(myGACO.
                getProgressGUI().getTxtaOutput().getDocument().getLength());
    }

    @Override
    public void run() {
        PlayGameUsingGaco();
    }
}