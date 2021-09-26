package com.luxoft.training.eas017.day1;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;

public class TransformationsAndActions {

    public static void main(String[] args) throws InterruptedException {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf().setAppName("RDD operations").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);



        sc.stop();
   }

    static class IntegerComparator implements java.util.Comparator<Integer>, Serializable {
        @Override
        public int compare(Integer i1, Integer i2) {
            return i1.compareTo(i2);
        }
    }
}
