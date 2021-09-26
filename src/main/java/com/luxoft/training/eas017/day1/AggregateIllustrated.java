package com.luxoft.training.eas017.day1;


import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AggregateIllustrated {

    static int sum(int x1, int x2){ return x1+x2; }
    static int mult(int x1, int x2){ return x1*x2; }

    public static void main(String[] args)  {
        System.setErr(new NullPrintStream());
        final SparkConf conf = new SparkConf().setAppName("AggregateIllustrated").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd1 = sc.parallelize(Arrays.asList(5, 6, 7), 1);
        JavaRDD<Integer> rdd2 = sc.parallelize(Arrays.asList(8, 9), 1);
        JavaRDD<Integer> rdd = rdd1.union(rdd2);

        System.out.println("Number of partitions: " + rdd.getNumPartitions());

        List<Integer> partitionSizes =
            rdd.mapPartitions((iter) -> {
                List<Integer> list = new ArrayList<>();
                iter.forEachRemaining(list::add);
                List<Integer> listOfSize = Collections.singletonList(list.size());
                return listOfSize.iterator();
            }).collect();

        System.out.println("Partion sizes: " + partitionSizes.toString());

        int aggregateResult =
            rdd.aggregate(1, AggregateIllustrated::mult, AggregateIllustrated::sum);
        System.out.println("Aggregate result: " + aggregateResult);

        sc.stop();
    }
}
