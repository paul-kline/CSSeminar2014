/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrispaul2;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author paul.kline
 */
public class ABetterPlacementDecider extends PlacementDecider {

    LinkedList<Cenario> TopChoices;

    boolean IstopChoicesSet=false;
    public LinkedList<Cenario> getTopChoices() {
        return TopChoices;
    }

    public LinkedList<Cenario> SetTopXChoices(int x) {
        TopChoices = new LinkedList<Cenario>();
        //Integer

        fig.rotateToOrientation(1);
        int cenariosPresent = 0;
        nextfig.rotateToOrientation(1);

        best = new Cenario(board, fig, 0, nextfig, 0, weights);// w1, w2, w3, w4, w5, w6, w7, w8);
        best.staticvalue = -999999999;
        TopChoices.add(best);
        //nextfig.rotateToOrientation(1);
        for (int a = 1; a <= fig.getMaxOrientation(); a++) {
            fig.rotateToOrientation(a);
            for (int b = 1; b <= nextfig.getMaxOrientation(); b++) {
                nextfig.rotateToOrientation(b);
                if (sout) {
                    System.out.println("piece b is this wide I think: " + nextfig.getpiecewidth());
                }
                for (int i = 0; i <= board[0].length - fig.getpiecewidth(); i++) {
                    for (int j = 0; j <= board[0].length - nextfig.getpiecewidth(); j++) {
                        cenario = new Cenario(board, fig, i, nextfig, j, weights);// w1, w2, w3, w4, w5, w6, w7, w8);                        
                        if (cenario.staticvalue > TopChoices.getLast().staticvalue) {
//                            if (topx.size() < x) {
//                                topx.add(cenario);
//                            } else {


                            int index = TopChoices.size();
                            Cenario cen = TopChoices.getLast();
                            System.out.println("Index of getLast: " + TopChoices.indexOf(TopChoices.getLast()));
                            if (cenario.staticvalue > cen.staticvalue) {
                                while (cenario.staticvalue > cen.staticvalue && index >= 0) {
                                    cen = (--index > -1) ? TopChoices.get(index) : cen;
                                }
                                TopChoices.add(index + 1, cenario);
                            }
                            while (TopChoices.size() > x) {
                                TopChoices.removeLast();
                            }
//                            }
                        }
                    }
                }
            }
        }
        System.out.println("");
        leftplace += getaxis(best.myfig);
        if (sout) {
            //        System.out.println("This is how many combos I tried: " + counter);
        }
        IstopChoicesSet=true;
        return TopChoices;

    }

}
