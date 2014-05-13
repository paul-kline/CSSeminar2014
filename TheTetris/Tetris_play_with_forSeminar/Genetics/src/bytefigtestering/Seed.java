/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bytefigtestering;


import NeuralThings.PaulNetworkFactory;
import NeuralThings.PaulNetworkFactory.NNCodes;
import TetrisHelpful.TetrisReader;
import TetrisHelpful.TetrisWriter;
import java.io.Serializable;

/**
 *
 * @author paul.kline
 */
public class Seed implements Serializable {

    private NNCodes nncode;
    private String WeightString;
    private int[] weights;
    private boolean UseWeights;
    private boolean UseNN;
    private boolean useRandomweights = false;

    public Seed(NNCodes nncodep, String strW) {
        String[] strArr = strW.split(",");
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = strArr[i].trim();
        }
        UseNN = !(nncodep == PaulNetworkFactory.NNCodes.NONE);
        System.out.println(strArr[0]);
        UseWeights = !"NONE".equals(strArr[0]);
        useRandomweights = "RANDOM".equals(strArr[0]);
        System.out.println("useRandomWeights: " + useRandomweights);
        System.out.println("should I use weights:" + UseWeights);
        nncode = nncodep;
        WeightString = strW;
        setWeights(strW);

    }

    private void setWeights(String str) {

        if (!UseWeights) {
//            for (int i = 0; i < weights.length; i++) {
//                weights[i]=0;                
//            }
            weights = null;
            return;
        }

        weights = new int[str.split(",").length - 1];

        if (useRandomweights) {
            for (int i = 0; i < weights.length; i++) {
                weights[i] = (int) (Math.random() * 100 - 50);

            }
            return;
        }
        TetrisReader tr = new TetrisReader();
        int[] ScoreAndweights = tr.SplitScoreStringToIntArray(str, ",");

        for (int i = 1; i < ScoreAndweights.length; i++) {
            weights[i - 1] = ScoreAndweights[i];
        }
    }

    @Override
    public String toString() {
        return nncode.toString() + "~~" + WeightString;
    }

    public boolean isUseRandomweights() {
        return useRandomweights;
    }

    public void setUseRandomweights(boolean useRandomweights) {
        this.useRandomweights = useRandomweights;
    }

    int[] getWeights() {
        return weights;
    }

    public boolean isUseWeights() {
        return UseWeights;
    }

    public void setUseWeights(boolean UseWeights) {
        this.UseWeights = UseWeights;
    }

    public boolean isUseNN() {
        return UseNN;
    }

    public void setUseNN(boolean UseNN) {
        this.UseNN = UseNN;
    }

    public NNCodes getNncode() {
        return nncode;
    }

    public void setNncode(NNCodes nncode) {
        this.nncode = nncode;
    }

    public String getWeightString() {
        return WeightString;
    }

    public void setWeightString(String WeightString) {
        this.WeightString = WeightString;
    }
}
