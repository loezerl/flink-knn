
import moa.core.InstanceExample;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.functions.FlatMapFunction;
//import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.logging.Logger;

import util.Similarity;
import util.InstancesLoezer;

import classifiers.KNN;

import moa.streams.ArffFileStream;
import com.yahoo.labs.samoa.instances.Instance;

/**
 * Created by loezerl-fworks on 21/08/17.
 */


public class Experimenter {

    public static Instance example;

    public static void main(String args[]) throws Exception{
        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //descobrir class index da base de kyoto
        ArffFileStream filess = new ArffFileStream("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff", -1);
//
//        filess.getHeader().numOutputAttributes();

        InstanceExample olar = filess.nextInstance();
        List<Instance> Teste = new ArrayList<>();
        int cont = 0;
        while(filess.hasMoreInstances() && (cont < 10)){
            olar = filess.nextInstance();
            Teste.add(olar.getData());
            cont++;
        }

        DataSet<Instance> PlsDeCerto = env.fromCollection(Teste);
        System.out.println(PlsDeCerto.count());

        example = Teste.get(5);

        DataSet<Tuple2<Instance, Double>> sss2 =
                PlsDeCerto.flatMap(new EuD());

        for(Tuple2<Instance, Double> t: sss2.collect()){

            System.out.println(t.f0 + " " + t.f1);
        }

        System.out.println("dummy print");
    }

    public static final class EuD implements FlatMapFunction<Instance, Tuple2<Instance, Double>>{
        @Override
        public void flatMap(Instance value, Collector<Tuple2<Instance, Double>> out) {
            out.collect(new Tuple2<Instance, Double>(value, Similarity.EuclideanDistance(example, value)));
        }
    }
}


