package com.luxoft.training.eas017.day3;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CachingAndRepartitioning {

    public static void main(String[] args) throws InterruptedException {
         System.setErr(new NullPrintStream());
         final SparkConf conf = new SparkConf().setAppName("AccumulatorsAndBroadcast").setMaster("local[*]");
         final JavaSparkContext sc = new JavaSparkContext(conf);

         JavaRDD<Integer> rdd = sc.parallelize(
            IntStream.range(0, 100).boxed().collect(Collectors.toList()), 5);

         rdd.repartition(1).collect();

         rdd.coalesce(1).collect();

         JavaRDD<Integer> rdd2 = rdd.filter(x -> x > 50).cache();

         rdd2.count();

         rdd2.map(x -> x + 100).collect();


         Thread.sleep(300_000);

    }

}
