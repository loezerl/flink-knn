package classifiers;

import com.yahoo.labs.samoa.instances.Instance;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import util.Similarity;

import java.util.*;

/**
 * Created by loezerl-fworks on 21/08/17.
 */
public class KNN extends Classifier {

    private int K;
    private int WindowSize;
    private String DistanceFunction;
    private List<Instance> Window;
    public static Instance example;

    public KNN(int kdistance, int wsize, String function, ExecutionEnvironment env){
        super(env);
        K = kdistance;
        WindowSize = wsize;
        if(function.equals("euclidean")){
            DistanceFunction = "euclidean";
        }
        else{
            System.out.println("Distancias disponiveis: euclidean");
            System.exit(1);
        }
        Window = new ArrayList<>(wsize);
    }

    @Override
    public boolean test(Instance example_) throws Exception{
        /**
         * Essa função vai receber uma instancia.
         * O passo inicial é calcular a distancia entre a instancia do parametro e as instancias presentes na janela.
         * Após calcular a distancia entre os pontos, é necessário selecionar as K instancias mais próximas.
         * Com um vetor com as K instancias mais próximas, opta-se por realizar um voto majoritário entre as classes de cada instancia.
         * Assim, verifica-se se a classe mais votada é igual a classe da instancia parametro, retornando True ou False.
         * **/

        //Cria um DataSet utilizando as instancias presentes no KNN
        if(Window.size() == 0){return false;}

        DataSet<Instance> W_instances = env.fromCollection(Window);

        example = example_;

        //Calcula a distancia entre o exemplo de teste e as instancias presentes na janela
        DataSet<Tuple2<Instance, Double>> distances = W_instances.flatMap(new EuclideanDistance());

        //Ordena as distancias
        distances = distances.sortPartition(1, Order.ASCENDING).setParallelism(1);

        //Pega os K vizinhos mais próximos
        List<Tuple2<Instance, Double>> K_neighbours = distances.first(K).collect();

        Map majorvote = new HashMap<Double, Integer>();

        for (Tuple2<Instance, Double> tuple : K_neighbours){
            if(majorvote.containsKey(tuple.f0.classValue())){
                Integer aux = (Integer)majorvote.get(tuple.f0.classValue());
                majorvote.put(tuple.f0.classValue(), aux + 1);
            }else{
                majorvote.put(tuple.f0.classValue(), 1);
            }
        }

        Integer bestclass_vote = -600;
        Double bestclass_label = -600.0;

        Iterator<Map.Entry<Double, Integer>> it = majorvote.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Double, Integer> pair = it.next();
            if(pair.getValue() > bestclass_vote){
                bestclass_label = pair.getKey();
                bestclass_vote = pair.getValue();
            }
        }

        Double targetclass = example.classValue();

        if(targetclass.equals(bestclass_label))
            return true;

        return false;
    }

    @Override
    public void train(Instance data){
        /**
         * Atente-se aqui em relação a exclusão mútua.
         * É provavel que as estruturas de array dos frameworks possuam mutex interno, mas é necessário verificar isso em cada framework.
         * */
        if (Window.size() < WindowSize) {
            Window.add(data);
        }
        else{
            Window.remove(0);
            Window.add(data);
        }
    }

    public static final class EuclideanDistance implements FlatMapFunction<Instance, Tuple2<Instance, Double>> {
        @Override
        public void flatMap(Instance value, Collector<Tuple2<Instance, Double>> out) {
            out.collect(new Tuple2<Instance, Double>(value, Similarity.EuclideanDistance(example, value)));
        }
    }
}
