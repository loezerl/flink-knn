
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

    public static void main(String args[]) throws Exception{
        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();


//        BufferedReader reader =
//                new BufferedReader(new FileReader("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff"));
//        System.out.println("Reader criado");
//        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
//        System.out.println("Arff Reader Criado");
//        Instances data = arff.getData();
//        if (data.classIndex() == -1) {
//            data.setClassIndex(data.numAttributes() - 1);
//            System.out.println(data.numAttributes() - 1);
//        }


        //descobrir class index da base de kyoto
        ArffFileStream filess = new ArffFileStream("/home/loezerl-fworks/Downloads/kyoto.arff", 15);
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

        for(Instance it: Teste){
            System.out.println(it);
        }


        DataSet<Instance> PlsDeCerto = env.fromCollection(Teste);
        System.out.println(PlsDeCerto.count());

//        for(Instance exm: data){
//            Teste.add(exm);
//        }
//        InstancesLoezer ILL = new InstancesLoezer(data);
//        List<String> kkk = new ArrayList<>();
//        kkk.add("KKK 1");
//        kkk.add("KKK 2");
//        kkk.add("KKK 3");

//        Instance ins1 = Teste.get(2);
//        Instance ins2 = Teste.get(3);
//        Instance ins3 = Teste.get(4);
//        System.out.println(Similarity.EuclideanDistance(ins1, ins2));
//
//        System.out.println(Teste.get(10));
//        Tuple2<Integer, Instance> vamosla = new Tuple2<>(1, ins1);
//
//        TypeInformation<Instance> info = TypeInformation.of(new TypeHint<Instance>(){});
//
//        Logger Log = Logger.getLogger(TypeExtractor.class.toString());
//
//        DataSet<InstancesLoezer> sss = env.fromElements(ILL);
//        System.out.println(sss.count());
////        DataSet<Tuple2<Instance, Double>> sss2 =
////                sss.flatMap(new EuD());


        System.out.println("dummy print");
//        List<Tuple2<Instance, Double>> hhh = sss2.collect();
//        DataSet<String> kks = env.fromCollection(kkk);
//        System.out.println(dataSimple.getBatchedDataSet(32).count());

    }

//    public static final class EuD implements FlatMapFunction<Instance, Tuple2<Instance, Double>>{
//        @Override
//        public void flatMap(Instance value, Collector<Tuple2<Instance, Double>> out) {
//            System.out.println(value);
//            out.collect(new Tuple2<Instance, Double>(value, Similarity.EuclideanDistance(value, value)));
//        }
//    }
}


