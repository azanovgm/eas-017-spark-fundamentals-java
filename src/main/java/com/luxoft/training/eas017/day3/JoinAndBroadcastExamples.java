package com.luxoft.training.eas017.day3;


import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;

public class JoinAndBroadcastExamples {

    public static String ADDRESSES_FILE = "src/main/resources/day3/addresses.txt";

    public static String RATINGS_FILE = "src/main/resources/day3/ratings.txt";

     public static void main(String[] args) throws InterruptedException {
         System.setErr(new NullPrintStream());
         final SparkConf conf = new SparkConf().setAppName("joins").setMaster("local[*]");
         final JavaSparkContext sc = new JavaSparkContext(conf);


         //TODO: read ADDRESSES_FILE and construct key-value RDD from it, we can be sure tat values are comma-separated
         JavaPairRDD<String, String> addresses =
            sc.textFile(ADDRESSES_FILE).mapToPair(s -> {
                String[] values = s.split(",");
                return new Tuple2<>(values[0], values[1]);
            });

         //TODO: read RATINGS_FILE and construct key-value RDD from it, we can be sure tat values are comma-separated
         JavaPairRDD<String, Double> ratings =
            sc.textFile(RATINGS_FILE).mapToPair(s -> {
                String[] values = s.split(",");
                return new Tuple2<>(values[0], Double.parseDouble(values[1]));
            });

         System.out.println("Addresses PairRDD:" + addresses.collect());
         System.out.println("Ratings PairRDD:" + ratings.collect());

         JavaPairRDD<String, Tuple2<String, Double>> join = addresses.join(ratings);
         System.out.println("addresses.join(ratings): " + join.collect());

         JavaPairRDD<String, Tuple2<String, Optional<Double>>> leftOuterJoin =  addresses.leftOuterJoin(ratings);
         System.out.println("addresses.leftOuterJoin(ratings): " + leftOuterJoin.collect());

         JavaPairRDD<String, String> subtractByKey = addresses.subtractByKey(ratings);
         System.out.println("addresses.subtractByKey(ratings): " + subtractByKey.collect());
         
         JavaPairRDD<String, Tuple2<Iterable<String>, Iterable<Double>>> cogroup = addresses.cogroup(ratings);
         System.out.println("addresses.cogroup(ratings): " + cogroup.collect());

         JavaPairRDD<String, Double> someMoreRatings = sc.parallelizePairs(
             Arrays.asList(
                 new Tuple2<>("Central cafe", 4.0),
                 new Tuple2<>("Tasty Diners", 4.5),
                 new Tuple2<>("Central cafe", 4.2),
                 new Tuple2<>("Tasty Diners", 4.3)
            ));

         //Broadcast variable example
         JavaPairRDD<String, Double> bigRatingRdd =
            ratings.union(someMoreRatings).union(ratings).union(ratings);

         Map<String, String> addressMap = addresses.collectAsMap();
         System.out.println("Our address list: " + addressMap);

         //Want to collect all ratings for the address
         Map<String, Iterable<Double>> allRatingsForAddress =
             bigRatingRdd
                .filter(kv -> addressMap.containsKey(kv._1))
                .mapToPair(kv -> new Tuple2<>(addressMap.get(kv._1), kv._2))
                .groupByKey()
                .collectAsMap();

         Broadcast<Map<String, String>> addressBc = sc.broadcast(addressMap);

         allRatingsForAddress =
            bigRatingRdd
                .filter(kv -> addressBc.value().containsKey(kv._1))
                .mapToPair(kv -> new Tuple2<>(addressBc.value().get(kv._1), kv._2))
                .groupByKey()
                .collectAsMap();

         System.out.println(allRatingsForAddress);

     }
}
