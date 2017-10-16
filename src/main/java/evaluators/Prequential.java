package evaluators;

import classifiers.Classifier;
import com.yahoo.labs.samoa.instances.Instance;
import moa.streams.ArffFileStream;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.concurrent.TimeUnit;

/**
 * Created by loezerl-fworks on 21/08/17.
 */
public class Prequential extends Evaluator{
    private int confirm=0;
    private int miss=0;

    /**
     *
     * @param _classifier Objeto do tipo Classifier.
     * @param data Objeto do tipo ArrfFileStream
     */
    public Prequential(Classifier _classifier, ArffFileStream data){ super(_classifier, data); }

    @Override
    public void run(long milliseconds) throws Exception{
        /**
         * Essa função é responsável por gerenciar a etapa de treino e teste do classificador.
         * Será responsável por:
         * - Passar as instancias para o test e em seguida o treino.
         * - Contabilizar os hits confirms e hit miss.
         *
         * */
        long finishTime = System.currentTimeMillis() + milliseconds;
        while(data_source.hasMoreInstances() && (System.currentTimeMillis() <= finishTime)){
            Instance example = data_source.nextInstance().getData();
//            long startTime = System.nanoTime();
            if(mClassifier.test(example))
                confirm++;
            else
                miss++;
//            long estimatedTime = System.nanoTime() - startTime;
//            System.err.println("\n\n===========\nTest time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime) + "\n============");
            mClassifier.train(example);

        }

    }

    public int getConfirm(){return confirm;}
    public int getMiss(){return miss;}


}
