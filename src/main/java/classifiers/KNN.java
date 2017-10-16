package classifiers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yahoo.labs.samoa.instances.Instance;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.DataSetUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;
import scala.math.Ordering;
import util.Similarity;
import util.TupleSingleton;

import java.util.*;
import java.util.concurrent.TimeUnit;


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
         * Essa função recebe uma instancia.
         * O passo inicial é calcular a distancia entre a instancia do parametro e as instancias presentes na janela.
         * Após calcular a distancia entre os pontos, ela seleciona as K instancias mais próximas.
         * Com um vetor com as K instancias mais próximas, opta-se por realizar um voto majoritário entre as classes de cada instancia.
         * Assim, verifica-se se a classe mais votada é igual a classe da instancia parametro, retornando True ou False.
         * **/

        if(Window.size() == 0){
            return false;
        }

        //Cria um DataSet utilizando as instancias presentes na janela do KNN
        DataSet<Instance> Window_Instances = env.fromCollection(Window);


        example = example_;

        final Double targetclass = example_.classValue();

        //Calcula a distancia entre o exemplo de teste e as instancias presentes na janela
        DataSet<Tuple2<Instance, Double>> Instances_Distances = Window_Instances.flatMap(new EuclideanDistance());

        //Ordena o DataSet (Instancia, Distancia)
        Instances_Distances = Instances_Distances.sortPartition(1, Order.ASCENDING).setParallelism(1);

        //Seleciona somente os K vizinhos mais proximos
        DataSet<Tuple2<Instance, Double>> K_Distances = Instances_Distances.first(K);

        //Agrupa as classes selecionadas
        DataSet<Tuple2<Double, Integer>> Votes = K_Distances.flatMap(
                new FlatMapFunction<Tuple2<Instance, Double>, Tuple2<Double, Integer>>(){
                   @Override
                   public void flatMap(Tuple2<Instance, Double> instanceDoubleTuple2, Collector<Tuple2<Double, Integer>> collector) throws Exception {
                        collector.collect(new Tuple2<Double, Integer>(instanceDoubleTuple2.f0.classValue(), 1));
                   }
               }

        ).groupBy(0).sum(1);

        //Seleciona a classe com mais votos
        Votes = Votes.map(new MajorVoteMapFunction()).setParallelism(1);
        Votes.count();
        Tuple2<Double, Integer> choiceone = TupleSingleton.getInstance();
        if(targetclass.equals(choiceone.f0))
            return true;

        return false;
    }

    @Override
    public synchronized void train(Instance data){
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

    public class MajorVoteMapFunction extends RichMapFunction<Tuple2<Double, Integer>, Tuple2<Double, Integer>>{

        private Tuple2<Double, Integer> choice;

        @Override
        public void open(Configuration parameters) throws Exception{
            this.choice=new Tuple2<>(0.0, -2);
        }

        @Override
        public void close(){
            TupleSingleton.setInstance(this.choice);
        }

        @Override
        public Tuple2<Double, Integer> map(Tuple2<Double, Integer> doubleIntegerTuple2) throws Exception {
            if(doubleIntegerTuple2.f1 > this.choice.f1){
                this.choice.setFields(doubleIntegerTuple2.f0, doubleIntegerTuple2.f1);
            }
            return doubleIntegerTuple2;
        }
    }
}
