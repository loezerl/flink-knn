
import classifiers.Classifier;
import classifiers.KNN;
import evaluators.Evaluator;
import evaluators.Prequential;
import org.apache.flink.api.java.ExecutionEnvironment;
import moa.streams.ArffFileStream;

/**
 * Created by loezerl-fworks on 21/08/17.
 */


public class Experimenter {

    public static void main(String args[]) throws Exception{
        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(10);

        String DIABETES_DATABASE = "/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff";
        String KYOTO_DATABASE = "/home/loezerl-fworks/Downloads/kyoto.arff";

        //descobrir class index da base de kyoto
        ArffFileStream file = new ArffFileStream(KYOTO_DATABASE, -1);

        Classifier myClassifier = new KNN(7, 30, "euclidean", env);
        Evaluator myEvaluator = new Prequential(myClassifier, file);
        myEvaluator.run();
    }

}


