package com.luxoft.training.eas017.day3;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class WordCount {
    public static void main(String[] args) {

        final SparkConf conf = new SparkConf().setAppName("KV RDD operations").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> text = sc.textFile( "src/main/resources/day2/alice-in-wonderland.txt");

        //TODO
        //Lets count number of non empty lines
        long numberOfNonEmptyLines = text.filter(line -> !line.isEmpty()).count();
        System.out.println("There are " + numberOfNonEmptyLines + " non empty lines");

        //TODO
        //Find what is the most frequent word length in text
        //To extract words, convert string to lowercase and
        //keep only alpha-numeric characters and whitespaces (Hint: you might want to use regex "[^a-z]")

        int mostFrequentWordLength = text.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .map(word -> word.toLowerCase().replaceAll("[^a-z]", ""))
                .keyBy(word -> word.length())
                .aggregateByKey(0, (count, word) -> count + 1, (count1, count2) -> count1 + count2)
                .mapToPair(tuple -> tuple.swap())
                .sortByKey(false)
                .values()
                .first();

        System.out.println("Most frequent word length in text is " + mostFrequentWordLength);
        
        //TODO
        //Print all distinct words for the most frequent word length
        List<String> words = text.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .map(word -> word.toLowerCase().replaceAll("[^a-z]", ""))
                .filter(word -> word.length() == mostFrequentWordLength)
                .distinct()
                .collect();
        System.out.println("Print all distinct words for the most frequent word length: " + words);
        
    }
}
