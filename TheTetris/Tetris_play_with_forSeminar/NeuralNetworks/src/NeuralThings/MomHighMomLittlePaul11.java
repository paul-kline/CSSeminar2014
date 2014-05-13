/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuralThings;

import java.awt.Point;
import org.encog.ml.MLEncodable;
import org.encog.ml.MLMethod;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.persist.EncogDirectoryPersistence;

// Code generated by Encog v3.2.0
// Generation Date: Thu Mar 20 17:00:05 CDT 2014
// Generated code may be used freely
// http://www.heatonresearch.com/encog
public class MomHighMomLittlePaul11 extends PaulNetwork {

    public static final double[] WEIGHTS = {
        0.7531663649, 0.0958229615, 0.9158947351, 0.7801635704, 0.2764010049, 0.5823408097, 0.6157726848, 0.4355417289, -4.4669186323, 1.2399299623,
        0.5031936667, 1.0859398843, 0.4335392968, 0.5933569365, -4.0483800158, 0.6733730579, 0.1453489756, 0.7770826236, 0.812673275, 0.6402850283,
        -0.9468550329, 0.5180124191, 0.2502119474, 0.6163047689, 0.4239620606, -1.5070050247, 0.7722429551, -0.8200472039, -1.3171998402, -0.4294450543,
        0.792171922, 0.2098790359, -4.2241164914, 0.9914531448, 0.2385101383, 0.3863026535, -0.0574494022, 0.8996492425, 0.6873168372, -4.330751096,
        0.6444577535, 0.2987335236, 0.2526863964, 0.7107271828, -1.1631344804, 0.6390533975, 0.552910849, -0.7975302667, -0.8409568328, 0.1295260215,
        0.9457747004, 0.965966399, 1.8936970535, 0.5768904515, -0.9243600174, 0.0609392112, -0.4752518892, 0.8150039474, -0.7618442375, 0.0859471702,
        0.9182966262, 1.3080797541, 0.6603278891, 0.4537962599, -0.5807613701, 0.6927354941, 0.5987628525, 0.3119736241, -2.4286824456, -1.7628964779,
        -0.8604959044, 8.4692778812, 1.5868762436, 6.6577662785, -5.1101244286, 8.2971326298, 0.3544625813, 28.7306439733, 0.4612353596, -1.2661085747,
        7.9847605594, -25.4534645777, 2.2799924277, 0.6544326257, -0.4573338528, -1.5310349106, 0.4980834306, 0.3695720382, 2.3064460712, 5.0728535434,
        2.1734931123, 10.360102399, 21.0756989183, 12.9751425023, 14.0751524865, 9.7642009403, 17.1200841491, 0.1942281173, 34.2996216796, 1.1041071022,
        -1.469224202, 4.7172309664, -29.0278237646, 2.2438086831, 1.0258543288, 3.7178616055, 2.5240790304, -0.3539177694, -3.660831862, -0.1917953372,
        -2.1899747037, 5.6796203501, 0.9546163948, 3.1687700688, -3.7480359391, 1.1602080755, -0.7952811067, -3.7961911651, 0.8374917661, 13.2290717872,
        5.0425604503, 2.6777202933, 11.4263962698, -1.5695485693, 1.6637925955, 0.2721611027, 0.6219125969, 3.610401565, 0.0962367219, 1.1352356808,
        -1.9319107848, -1.2731491833, 1.0232749382, -0.4559381065, 0.3254124221, -0.079039546, -0.2558243728, -1.1160891609, 14.9038949279, -0.6811961011,
        11.7205262548, 9.926871753, 1.3710783439, 22.3184546645, -12.4615080846, 47.7416111754, -27.8074052293, -11.1250967606, 43.0072801135, -120.3425370978,
        -13.3781144514, 39.7979092933, 0.4333760508, 1.0207339041, 0.0153275935, 1.7082557827, 0.8282446516, -0.1309649558, 0.6196651068, 46.6246784041,
        8.1515057385, -25.9300633636, 35.1093475161, 31.7004909296, 7.0781845876, 2.2423340736, 1.3060644093, 1.1174218618, -0.9049570513, 0.4941798842,
        -0.6485330542, 0.9276767669, 1.2836035707, 85.7656267104, 34.868745582, -39.1440188903, 46.0944291259, 20.066161137, 12.6666364101, 2.1537193004,
        9.2871508658, 1.2191021339, 30.4110328979, 0.9355259835, 0.25706063, 9.5040136439, -26.2422830497, 7.2076017475, 75.5958559136, -2.6597286827,
        -51.0823982609, 7.2785884919, -4.8744222939, -8.1354858533, 6.2692278462, -2.1128454534, -1.4110760725, 0.1013863865, 0.1942618243, -0.2229636007,
        0.9959314464, -11.8392717963, -1.5874340064, 1.5837998165, 20.2792680283, 33.9614282247, 12.8254356665, -2.4762725468, 1.5810059637, 1.8469022354,
        -2.3596255081, 2.918787198, 1.756762052, 3.549103437, -0.627220755, -0.7553616412, -3.066344731, 6.7560111349, 9.7128464481, 17.8951464952,
        17.9272882627, 0.8266580534, 0.3710211052, 2.8476332, 8.2327363771, 7.1828792919, 1.3114579348, 7.0349761778, 0.3340950669, 21.2308571455,
        -1.8554898518, 4.5449809605, 7.4903625923, 0.7112165214, 16.7289941488, 2.1194172991, 5.8290268167, 1.8654285513, -6.90100178, 33.3826785743,
        2.1634518734, 7.3407856791, -4.5675163245, 1.1451744155, 1.7012844834, 0.1360864823, 2.3980595669, -0.6745451086, -0.5354218247, 0.4521215344,
        0.9677810832, 34.2486855352, 130.3800556481, 0.66621921, 262.2041393425, -1.4335584266, -22.0127932653, 4.3858309473, 2.7921046401, 2.146834452,
        -4.9684648785, -4.7798734633, 1.366797628, 1.1460550612, 1.1384804966, 3.1314525824, 2.5628644701, -1.5227239007, -2.4271482363, 5.9429528797,
        -0.347725443, 52.0658886958, 70.703530483, 24.3029723786, -60.6362219936, -14.2015326575, -28.6077616811, -7.6751261727, 11.8360250858, 2.1390814261,
        8.1653843167, -18.0031750204, -5.9631217802, -3.8378201549, -3.4926296673, 8.4888615831, 0.7839007656, 30.3294759932, 0.2329615724, 0.9796584573,
        10.0678162081, -25.9752496807, 0.9161259903, 0.1182957241, -0.0040578458, 1.7461569253, 0.3227229719, 0.365139233, -0.9653848561, 17.5158441647,
        14.351228576, -6.1361289155, 4.7508691489, 8.5624676582, -14.7094123263, 2.6647063005, 11.1497167082, 140.9000481296, -11.0168007973, -59.0242612632,
        5.4435200874, -25.5226067307, -19.2517008468, -22.8523177203, 89.8514055739, 61.7012514944, -58.7319446856, -28.533854823, 51.9252801971, -23.4880350043,
        72.5105701762, 0.9311074017, 43.6446454749, 70.946227454, -3.296582237, 11.354463935, -1.0871384482, 65.2237596257, 20.6323951424, -2.6125964454,
        -26.3025303788, 23.2543629638, 48.1715288594, -4.0368951827, 22.7136369088, -24.8623811069, 5.3501569542, -12.3544613271, -1.6905546259, 2.7026869971,
        -31.877040433, -0.1671238525, 1.6807610015, 0.0389883427, 0.7293494772, 1.6584028439, 0.4527442182, 1.2247125354, 8.1695866665, 194.4055921068,
        -3.5269177313, -15.432908331, 89.424465696, -22.8111512529, -2.5927166489, 8.4814649633, 0.6716960117, 30.3649754297, 0.8693955148, -0.0126654447,
        9.5817210983, -26.6368609861, 10.1039283448, -17.6908367862, -6.7157591081, -59.5214467706, 18.8053458529, 101.1753308706, 0.5696571955, 6.7023127318,
        8.1996579671, 0.3158759368, 15.0657096617, 0.8533852926, 1.3367731925, 3.146152834, 4.0152454205, -1.7531768874, -0.4269723356, 2.3446915571,
        1.6372232286, -0.065173571, -0.1001369786, 15.560777076, -2.0816304681, -4.0027632189, 6.533709354, -0.2564560205, -3.6541901008, -1.1221585905,
        9.2004169638, 0.7998284184, 31.2767993617, 0.6349118556, 0.9512763141, 10.502819277, -26.7995023493, 0.3079717877, 1.5148566193, 0.2323451817,
        4.2217921029, 0.7702024767, 2.302131415, 0.7717065036, -16.6224698262, 5.816858451, 1.2692443416, 15.1956443709, -6.4896677931, -9.1164887276,
        -2.5261929907, 8.5091434509, 1.7302914307, 29.2810190408, 1.0919236883, 1.4869908854, 10.9161790584, -24.5269846375, -3.0051345967, 0.8064329597,
        -2.5907536969, 0.4932504479, 2.7576195628, 3.2883555788, -1.1326353043, 3.4155472859, 3.6636057175, -0.9076504284, 14.0414299806, -6.3761140605,
        7.2391462588, -7.9311875943, 0.4275699614, 0.411786397, -0.3679192743, 1.5053517373, -1.2301644549, 0.8046754739, -0.1227508133, -2.435710472,
        2.552983927, 7.2025811942, 7.6280009297, 6.7100553052, -5.146078025, -1.5935706957, 1.4419481129, -0.0851351025, 2.3754832498, 2.5937022992,
        -0.993621714, 0.4663595453, 0.9069356072, 8.5572443735, 2.0807195428, 30.0331200959, 0.683989645, 1.5024641804, 11.2108107213, -25.628344905,
        4.7036527458, -3.0534548139, 1.3121910771, 4.9913422699, 0.0984732868, 3.9247965306, -0.8957410592, 2.7875961071, -1.4491845278, 0.5648611391,
        3.6529602128, 3.7344671129, 7.5532549652, -0.0623222058, 0.3707997376, -0.1363865697, 0.1307763792, 0.6685217393, 1.0641895831, 2.6485854104,
        0.8080774967, 8.4933694822, 1.7743295974, 29.0518526797, 1.1897588961, 0.7474962367, 10.2508886898, -24.8501747306, 0.1926810055, 1.470459324,
        -4.3068626424, 1.6879835211, 0.6762142979, 2.3164582419, -0.1041267574, -13.4532679195, -28.0157555656, -15.2062310721, 42.2360066412, -6.1480765668,
        -9.1268600282, -5.0576007295, -14.784208187, 7.3665786414, 5.0572166442, 18.3902399849, 6.066518936, 16.8155676795, -9.1215835177, 0.5224110824,
        1.1640718009, 1.2669878823, 11.2999477461, -8.2449491008, -0.2848534731, 4.2483825217, 1.0050148911, 15.4031145465, -28.1005532065, 3.3311289134,
        -20.993379458, 3.0807994083, 3.9618362554, -39.6121259548, 105.0183634242, 27.1402530117, 40.6152284116, -22.5880872784, -6.8504267428, -45.4961427989,
        -48.1926485204, 149.8558056525, -11.8874969828, -17.45885159, 92.0308737248, 17.7046335023, -0.461890433
    };

 

   
    @Override
    public MLMethod createNetwork() {
        MLMethodFactory methodFactory = new MLMethodFactory();
        MLMethod result = methodFactory.create("feedforward", "6:B->TANH->67:B->TANH->1", 6, 1);
        ((MLEncodable) result).decodeFromArray(WEIGHTS);
        return result;
    }
    
       
    static Object[][] helpMeNormalize = {
        {"name", "isclass", "iscomplete", "isint", "isreal", "amax", "amin", "mean", "sdev", "source"},
        {"field:1", 0, 1, 1, 1, 28, 0, 2.5559796438, 3.4466650355, ""},
        {"field:2", 0, 1, 1, 1, 3, 0, 0.3823155216, 0.5042619702, ""},
        {"field:3", 0, 1, 1, 1, 3, 0, 0.1688931298, 0.4288861202, ""},
        {"field:4", 0, 1, 1, 1, 76, 6, 51.4699427481, 17.1256410848, ""},
        {"field:5", 0, 1, 1, 1, 8, 0, 1.4702608142, 1.2998604576, ""},
        {"field:6", 0, 1, 1, 1, 4, 0, 0.8201335878, 1.1979341771, ""},
        {"field:7", 0, 1, 1, 1, 1, -1, 0, 1, ""}
    };

    
       private static Point[] getAminAmaxPointArray(int fieldSize) {
        Point[] result= new Point[fieldSize];
    //    System.out.println("helpmenorsize: "+helpMeNormalize.length);
        
        //minus 1 because we don't care about the one, that's the output. we want
        //inputs.
        for (int i = 1; i < helpMeNormalize.length-1; i++) {
            int amax=0;
            int amin=0;
            amax=(Integer) helpMeNormalize[i][5];
//            System.out.println("amax is: "+amax);
            amin=(Integer) helpMeNormalize[i][6];
//            System.out.println("amin is: "+amin);
            result[i-1]=new Point(amin,amax);
            
        }
        return result;
    }
    public static double[] NORMALIZE(double[] inputs) {
       Point[] AminAMax = getAminAmaxPointArray(inputs.length);
       double[] result = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
           result[i]=norm(AminAMax[i], inputs[i]);         
        }
        return result;
   

    }
     private static double norm(Point point, double d) {
       double min= point.x;
       double max= point.y;
       
         if (d<min) {
             return min;
         }
         if (d>max) {
             return max;
         }
         double diff=max-min;
         
         double nMax=1;
         double nMin=-1;
         
         double NormalizedDiff= nMax -nMin;
         
         // ((d-min)/diff) = x / NormalizedDiff;
         double x=(((d-min)*NormalizedDiff)/diff);
         return nMin+x;
    }

}
