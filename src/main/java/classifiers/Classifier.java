package classifiers;

import com.yahoo.labs.samoa.instances.Instance;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by loezerl-fworks on 21/08/17.
 */
public class Classifier {
    /**
     * ExecutionEnviroment -> Variavel responsavel por utilizar as estruturas de dados do Apache Flink.
     */
    ExecutionEnvironment env;
    public Classifier(ExecutionEnvironment envs){env = envs;}
    public boolean test(Instance example) throws Exception{return true;}
    public synchronized void train(Instance example){}
}
