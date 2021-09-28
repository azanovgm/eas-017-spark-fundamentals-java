package com.luxoft.training.eas017.day2;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Collections;
import java.util.List;

public class WordCount {
    public static void main(String[] args) {

        final SparkConf conf = new SparkConf().setAppName("KV RDD operations").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> text = sc.textFile( "src/main/resources/day2/alice-in-wonderland.txt");

        //TODO
        //Lets count number of non empty lines
        long numberOfNonEmptyLines = 0;
        System.out.println("There are " + numberOfNonEmptyLines + " non empty lines");

        //TODO
        //Find what is the most frequent word length in text
        //To extract words, convert string to lowercase and
        //keep only alpha-numeric characters and whitespaces (Hint: you might want to use regex "[^a-z]")

        int mostFrequentWordLength = 0;

        System.out.println("Most frequent word length in text is " + mostFrequentWordLength);
        
        //TODO
        //Print all distinct words for the most frequent word length
        List<String> words = Collections.EMPTY_LIST;
        System.out.println("Print all distinct words for the most frequent word length: " + words);
        
    }
}
