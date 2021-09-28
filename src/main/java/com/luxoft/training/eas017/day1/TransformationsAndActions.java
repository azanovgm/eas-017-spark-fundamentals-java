package com.luxoft.training.eas017.day1;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TransformationsAndActions {

    public static void main(String[] args) throws InterruptedException {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf().setAppName("RDD operations").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> numbers = IntStream.range(0, 10).boxed().collect(Collectors.toList());

        JavaRDD<Integer> rdd = sc.parallelize(numbers, 2);

        List<Integer> localListOnDriver = rdd.collect();
        System.out.println("rdd contents: " + localListOnDriver);

        JavaRDD<Integer> evenNumbersRDD = rdd.filter(n -> n % 2 == 0);
        System.out.println("evenNumbersRDD contents:" + evenNumbersRDD.collect());

        //Some map example
        JavaRDD<Integer> mappedRdd = rdd.map(i -> i+100);
        System.out.println("mappedRdd contents:" + mappedRdd.collect());

        int minEvenNumber = rdd.min(new IntegerComparator());
        System.out.println("Min even number is:" + minEvenNumber);
        minEvenNumber = rdd.takeOrdered(1).get(0);
        System.out.println("Min even number is:" + minEvenNumber);

        JavaDoubleRDD squaredNumbersRDD = rdd.mapToDouble(n -> Math.pow(n, 2));
        System.out.println("squaredNumbersRDD contents: " + squaredNumbersRDD.collect());

        double maxSquare = squaredNumbersRDD.max();
        System.out.println("Max square is " + maxSquare);

        double sumOfSquares = squaredNumbersRDD.reduce((d1, d2) -> d1 + d2);
        System.out.println("Sum of squares is " + sumOfSquares);
        
        // Transformations are lazy
        JavaRDD<Double> errorRdd = rdd.map(n -> {
            if (n > 0){
                throw new Exception("oops");
            } else {
                return 1.;
            }
        }).filter(x -> x > 0);

        JavaRDD<Double> errorRdd2 = errorRdd.map(x -> x + 2);

        System.out.println("errorRdd2 is just an instruction: " + errorRdd2);

        // Nothing happens until action is triggered
        // errorRdd2.collect();

        //Thread.sleep(300_000);  //Let's take a look on Spark UI

        sc.stop();
   }

    static class IntegerComparator implements java.util.Comparator<Integer>, Serializable {
        @Override
        public int compare(Integer i1, Integer i2) {
            return i1.compareTo(i2);
        }
    }
}
