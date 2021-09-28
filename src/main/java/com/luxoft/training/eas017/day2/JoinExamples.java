package com.luxoft.training.eas017.day2;


import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import scala.Tuple2;

public class JoinExamples {

    public static String ADDRESSES_FILE = "src/main/resources/day2/addresses.txt";

    public static String RATINGS_FILE = "src/main/resources/day2/ratings.txt";

     public static void main(String[] args) throws InterruptedException {
         System.setErr(new NullPrintStream());
         final SparkConf conf = new SparkConf().setAppName("KV RDD operations").setMaster("local[*]");
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

         JavaPairRDD<String, Tuple2<String, Double>> join =  addresses.join(ratings);
         System.out.println("addresses.join(ratings): " + join.collect());

         JavaPairRDD<String, Tuple2<String, Optional<Double>>> leftOuterJoin =  addresses.leftOuterJoin(ratings);
         System.out.println("addresses.leftOuterJoin(ratings): " + leftOuterJoin.collect());

     }
}
