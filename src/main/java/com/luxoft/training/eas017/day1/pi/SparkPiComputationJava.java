package com.luxoft.training.eas017.day1.pi;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkPiComputationJava {

    public static void main(String[] args) {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf()
                .setAppName("Pi computaion")
                .setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        final int numberOfIterations = 1_000_000;

        // TODO: Calculate Pi using Spark

        final double piApproximation = 3.14;

        System.out.println("Pi is roughly " + piApproximation);

    }

}
