package com.luxoft.training.eas017.day2;


import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class JoinExamples {

    public static String ADDRESSES_FILE = "src/main/resources/day2/addresses.txt";

    public static String RATINGS_FILE = "src/main/resources/day2/ratings.txt";

     public static void main(String[] args) throws InterruptedException {
         System.setErr(new NullPrintStream());
         final SparkConf conf = new SparkConf().setAppName("joins").setMaster("local[*]");
         final JavaSparkContext sc = new JavaSparkContext(conf);

         //TODO: read ADDRESSES_FILE and construct key-value RDD from it, we can be sure tat values are comma-separated
         JavaPairRDD<String, String> addresses = null;

         //TODO: read RATINGS_FILE and construct key-value RDD from it, we can be sure tat values are comma-separated
         JavaPairRDD<String, Double> ratings = null;

         System.out.println("Addresses PairRDD:" + addresses.collect());
         System.out.println("Ratings PairRDD:" + ratings.collect());

     }
}
