package tetris3;

import org.encog.ml.MLEncodable;
import org.encog.ml.MLMethod;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.persist.EncogDirectoryPersistence;

// Code generated by Encog v3.2.0
// Generation Date: Sun Feb 09 12:47:50 CST 2014
// Generated code may be used freely
// http://www.heatonresearch.com/encog

public class Values10000Iterations {
    public static final double[] WEIGHTS = {
        -0.0085904316,-1.4752817417,-0.1546541591,-0.0509777155,0.8929577169,0.117757817,0.3043782189,0.2671203425,0.4735464605,0.7966258861,
        0.5363256809,0.9900442869,0.3557175844,0.5529603077,28.5092779055,1.2376728082,-0.941752273,0.1274788559,-0.1405576772,-0.0094474672,
        -2.9616898079,-2.3399760464,0.011152202,0.4679246879,0.6382124989,0.75953842,0.5490979678,0.1751270814,0.6622462373,0.6501116929,
        -0.1447891245
    };

    public static MLMethod createNetwork() {
        MLMethodFactory methodFactory = new MLMethodFactory();
        MLMethod result = methodFactory.create("feedforward","8:B->TANH->3:B->TANH->1", 8, 1);
        ((MLEncodable)result).decodeFromArray(WEIGHTS);
        return result;
    }

    public static void main(String[] args) {

        MLMethod method = createNetwork();
        // Network and/or data is now loaded, you can add code to train, evaluate, etc.
    }
}