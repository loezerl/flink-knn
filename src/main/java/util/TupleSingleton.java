package util;

import org.apache.flink.api.java.tuple.Tuple2;

public class TupleSingleton {
    private static Tuple2<Double, Integer> instance;

    protected TupleSingleton(){}

    public static synchronized Tuple2<Double, Integer> getInstance(){
        if(instance == null){
            instance = new Tuple2<Double, Integer>();
        }
        return instance;
    }

    public static synchronized void setInstance(Tuple2<Double, Integer> obj){
        instance = obj;
    }
}
