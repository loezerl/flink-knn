package classifiers;

import com.yahoo.labs.samoa.instances.Instance;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * Created by loezerl-fworks on 21/08/17.
 */
public class Classifier {
    ExecutionEnvironment env;
    public Classifier(ExecutionEnvironment envs){env = envs;}
    public boolean test(Instance example) throws Exception{return true;}
    public void train(Instance example){}
}
