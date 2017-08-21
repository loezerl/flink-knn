import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by loezerl-fworks on 21/08/17.
 */


public class Experimenter {

    public static void main(String args[]) throws Exception{
        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();


        BufferedReader reader =
                new BufferedReader(new FileReader("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff"));
        System.out.println("Reader criado");
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        System.out.println("Arff Reader Criado");
        Instances data = arff.getData();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        


        System.out.println("Teste");
    }
}
