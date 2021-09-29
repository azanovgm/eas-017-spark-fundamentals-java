package com.luxoft.training.eas017.day3;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class KVTransformationsAndActions {

    public static void main(String[] args) throws InterruptedException {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf().setAppName("KV RDD operations").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);


    }
}
