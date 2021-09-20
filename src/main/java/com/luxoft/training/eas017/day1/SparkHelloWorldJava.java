package com.luxoft.training.eas017.day1;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class SparkHelloWorldJava {

   public static void main(String[] args) {
       final SparkConf conf = new SparkConf()
               .setAppName("Hello World!")
               .setMaster("local[*]");

       final JavaSparkContext sc = new JavaSparkContext(conf);

       final List<String> localCollection = Arrays.asList("Hello", "World!");

       final JavaRDD<String> distributedCollection = sc.parallelize(localCollection, 2);

       distributedCollection.foreach(s -> System.out.println(s));

       sc.stop();

   }

}
