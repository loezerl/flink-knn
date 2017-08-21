import eu.amidst.core.datastream.DataInstance;
import eu.amidst.flinklink.core.data.DataFlink;
import eu.amidst.flinklink.core.io.DataFlinkLoader;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.tuple.Tuple2;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import classifiers.KNN;

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
//        }
//
//        List<Instance> Teste = new ArrayList<>();
//
//        for(Instance exm: data){
//            Teste.add(exm);
//        }
//
//        List<String> kkk = new ArrayList<>();
//        kkk.add("KKK 1");
//        kkk.add("KKK 2");
//        kkk.add("KKK 3");
//
//        Instance ins1 = Teste.get(2);
//        Instance ins2 = Teste.get(3);
//        Instance ins3 = Teste.get(4);
//
//        System.out.println(Teste.get(10));
//        Tuple2<Integer, Instance> vamosla = new Tuple2<>(1, ins1);
//
//        TypeInformation<Instance> info = TypeInformation.of(new TypeHint<Instance>(){});
//
//        DataSet<Tuple2<Integer, Instance>> sss = env.fromElements(vamosla);

        DataFlink<DataInstance> dataSimple =
                DataFlinkLoader.open(env, "/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff", false);

        List<DataInstance> aham = dataSimple.getDataSet().collect();

//        for(DataInstance ex: aham){
//            System.out.println(ex.);
//        }

        System.out.println(dataSimple.getAttributes());

//        DataSet<String> kks = env.fromCollection(kkk);
//        System.out.println(dataSimple.getBatchedDataSet(32).count());

    }
}
