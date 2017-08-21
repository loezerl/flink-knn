package classifiers;

import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loezerl-fworks on 21/08/17.
 */
public class KNN extends Classifier {

    private int K;
    private int WindowSize;
    private String DistanceFunction;
    private List<Instance> Window;

    public KNN(int kdistance, int wsize, String function){
        K = kdistance;
        WindowSize = wsize;
        if(function == "euclidean"){
            DistanceFunction = "euclidean";
        }
        else{
            System.out.println("Distancias disponiveis: euclidean");
            System.exit(1);
        }
        Window = new ArrayList<>(wsize);
    }

    @Override
    public boolean test(Instance example){return true;}

    @Override
    public void train(Instance example){}
}
