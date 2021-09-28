package com.luxoft.training.eas017.day2.pi;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.random;

public class SparkPiComputationJava {

    public static void main(String[] args) {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf()
                .setAppName("Pi computaion")
                .setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        final int numberOfIterations = 1_000_000;

        // TODO: Calculate Pi using Spark
        final List<Integer> iterations = Collections.nCopies(numberOfIterations, 0);
        final JavaRDD<Integer> iterationsRdd = sc.parallelize(iterations);

        // Driver must hold 'range' in memory. Let's generate range in parallel!
//        final int parallelRangeParts = 1_000;
//        List<Integer> initialRange = Collections.nCopies(parallelRangeParts, 0);
//        JavaRDD<Integer> iterationsRdd = sc.parallelize(initialRange).flatMap(i ->
//            Collections.nCopies(numberOfIterations/parallelRangeParts, 0).iterator()
//        );

        final int pointsInsideCircle = iterationsRdd
            .map(i -> {
                final double x = random() * 2 - 1;
                final double y = random() * 2 - 1;
                return x * x + y * y < 1 ? 1 : 0;
            }).reduce((sum1, sum2) -> sum1 + sum2);

        // The same can be achieved with
//            .filter(i -> {
//                final double x = random() * 2 - 1;
//                final double y = random() * 2 - 1;
//                return x * x + y * y < 1;
//            }).count();

       final double piApproximation = 4.0 * pointsInsideCircle / numberOfIterations;

       System.out.println("Pi is roughly " + piApproximation);

    }

}
