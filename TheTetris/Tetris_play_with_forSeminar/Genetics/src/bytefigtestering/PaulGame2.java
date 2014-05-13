/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bytefigtestering;

import bytefigtestering.PaulGame1;

/**
 *
 * @author Paul
 */
public class PaulGame2 extends PaulGame1 {

    double metaLinesClearedWeight = 1.0;

    PaulGame2(Seed seed,GAControllerObject gaco, String id) {
       super(seed,gaco,id);
       
        
    }

    public void setmetaLinesClearedWeight(double d) {
        metaLinesClearedWeight = d;
    }
    int oldLinesClearedWeight;
    boolean isSquaredDependence = false;
    public final static int lineCIndex = 2;

//    public PaulGame2(int[] weightsp) {
//        super(weightsp);
//        oldLinesClearedWeight = weightsp[lineCIndex];
//    }

    public void setLinesClearedWeight(int height) {
        myWeights[lineCIndex] = (int) (oldLinesClearedWeight * metaLinesClearedWeight * height
                * ((isSquaredDependence) ? height : 1.0));
    }

}
