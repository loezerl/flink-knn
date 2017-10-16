
import classifiers.Classifier;
import classifiers.KNN;
import evaluators.Evaluator;
import evaluators.Prequential;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.java.ExecutionEnvironment;
import moa.streams.ArffFileStream;

/**
 * Created by loezerl-fworks on 21/08/17.
 */


public class Experimenter {

    public static void main(String args[]) throws Exception{
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(4);
        String DIABETES_DATABASE = "/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff";
        String KYOTO_DATABASE = "/home/loezerl-fworks/Downloads/kyoto.arff";

        final ArffFileStream file = new ArffFileStream(KYOTO_DATABASE, -1);

        final Classifier myClassifier = new KNN(7, 1000, "euclidean", env);
        final Prequential myEvaluator = new Prequential(myClassifier, file);

//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                try {
//                    System.out.println("Running shutdownhook..");
//                    System.err.println("Confirms: " + myEvaluator.getConfirm());
//                    System.err.println("Miss: " + myEvaluator.getMiss());
//                    System.err.println("Instances: " + (myEvaluator.getMiss() + myEvaluator.getConfirm()));
//                }catch (Exception e){}
//            }
//        });
        long total = Runtime.getRuntime().totalMemory();
        myEvaluator.run(10000);
        long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.err.println("Confirms: " + myEvaluator.getConfirm());
        System.err.println("Miss: " + myEvaluator.getMiss());
        System.err.println("Instances: " + (myEvaluator.getMiss() + myEvaluator.getConfirm()));
        System.err.println("Memory: " + (used/1024.0)/1024.0);
    }

}


