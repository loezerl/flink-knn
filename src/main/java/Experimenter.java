
import classifiers.Classifier;
import classifiers.KNN;
import evaluators.Evaluator;
import evaluators.Prequential;
import moa.core.InstanceExample;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.functions.FlatMapFunction;
//import weka.core.Instance;

import java.util.*;

import util.Similarity;

import moa.streams.ArffFileStream;
import com.yahoo.labs.samoa.instances.Instance;

/**
 * Created by loezerl-fworks on 21/08/17.
 */


public class Experimenter {

    public static void main(String args[]) throws Exception{
        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //descobrir class index da base de kyoto
        ArffFileStream file = new ArffFileStream("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff", -1);

        Classifier myClassifier = new KNN(7, 30, "euclidean", env);
        Evaluator myEvaluator = new Prequential(myClassifier, file);
        myEvaluator.run();

        System.out.println("dummy print");
    }

}


