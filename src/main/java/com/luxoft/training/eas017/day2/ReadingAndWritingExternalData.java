package com.luxoft.training.eas017.day2;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class ReadingAndWritingExternalData {

    public static void main(String[] args)  {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf().setAppName("ReadWrite").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        // We can read a single text file
        JavaRDD<String> textFile = sc.textFile("src/main/resources/day1/file01.csv");

        // We can read multiple files
        textFile = sc.textFile("src/main/resources/day2/file01.csv,src/main/resources/day1/file02.csv");

        // We can read a file using wildcard
        textFile = sc.textFile("src/main/resources/day2/file*.csv");

        textFile.foreach(s -> System.out.println(s));

        //TODO: using a single RDD with all rows calculate the number of individual row elements ignoring header
        String header = textFile.first();
        long numberOfRowElements = textFile
            .filter(s -> !s.equals(header))
            .flatMap(s -> Arrays.stream(s.split(",")).iterator())
            .count();
        System.out.println("Number of all row elements: " + numberOfRowElements);

        JavaRDD<String> jsons = textFile
            .filter(s -> !s.equals(header))
            .map(s ->{
                String[] values = s.split(",");
                return String.format("{\"a\": %s,\"b\": %s,\"c\": %s}",
                        values[0], values[1], values[2]);
            });
        jsons.saveAsTextFile("src/main/resources/day2/out.json");

        System.out.println(textFile.getNumPartitions());
    }

}
