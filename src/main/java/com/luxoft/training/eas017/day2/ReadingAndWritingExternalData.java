package com.luxoft.training.eas017.day2;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class ReadingAndWritingExternalData {

    public static void main(String[] args)  {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf().setAppName("AggregateIllustrated").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> textFile = null;

        textFile.foreach(s -> System.out.println(s));

        //TODO: using a single RDD with all rows calculate the number of individual row elements ignoring header

    }

}
