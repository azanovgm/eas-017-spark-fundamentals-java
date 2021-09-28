package com.luxoft.training.eas017.day3;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class KVTransformationsAndActions {

    public static void main(String[] args) throws InterruptedException {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf().setAppName("KV RDD operations").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        List<Tuple2<String, Integer>> localPairs = Arrays.asList(
             new Tuple2<>("Alice", 1),
             new Tuple2<>("Bob", 3),
             new Tuple2<>("John", 2),
             new Tuple2<>("Alice", 4),
             new Tuple2<>("John", 3)
        );

        JavaPairRDD<String, Integer> pairRDD = sc.parallelizePairs(localPairs);
        System.out.println("pairRDD: " + pairRDD.collect());

        JavaPairRDD<String, Integer> aliceRDD = pairRDD.filter(t -> t._1.equals("Alice"));
        System.out.println("aliceRDD: " + aliceRDD.collectAsMap());

        JavaPairRDD<String, Iterable<Integer>> grouped = pairRDD.groupByKey();
        System.out.println("grouped: " + grouped.collect());

        JavaPairRDD<String, Integer> pairSums = pairRDD.reduceByKey((x, y) -> x + y);
        System.out.println("pairRDD value sums: " + pairSums.collect());

        JavaPairRDD<String, Tuple2<Integer, Integer>> mappedValues = pairRDD.mapValues(i -> new Tuple2<>(i, 1));

        JavaPairRDD<String, Double> averageByKey = mappedValues
            .reduceByKey((x, y) -> new Tuple2<>(x._1 + y._1, x._2 + y._2))
            .mapToPair(t -> new Tuple2<>(t._1, t._2._1.doubleValue() / t._2._2));

        System.out.println("averageByKey: " + averageByKey.collect());
    }
}
