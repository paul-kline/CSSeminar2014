/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bytefigtestering;

import GUIs.GATrainingSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.percederberg.tetris.Main;


/**
 *
 * @author paul.kline
 */
public class Master {
    public static ByteFigTestering bft;
    
     public static void main(String[] args) {
        
        
        
        
        
        
        //Score: 9083268:   {-2,-11,-48,0,14,32,50,45};
        //
        //WA to try sometime {-3.20, -7.78, 44.03, 3.24, 13.16,31.70,47.43,44.95}
//        String input = JOptionPane.showInputDialog(null, "'t': test | 'r': real\n"
//                + "'m': mutate | 'c': clone"
//                + "\n 'l': linesMod", "tm").toLowerCase();
//        System.out.println(input);
        GAControllerObject gaco = new GAControllerObject();
         
         bft = new ByteFigTestering();
         System.out.println("make it here");
        GATrainingSettings gatrainingsettings = new GATrainingSettings(gaco);
        bft.setMyGATrainingS(gatrainingsettings);
        gatrainingsettings.setVisible(true);
        try {
            while (!gaco.isShouldStartTesting()) {
                Thread.sleep(3);
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
         System.out.println("said make new");
        bft.SetControllerObjectIdeas(gaco);
        bft.Begin("nulltext", gaco);
    }
}
