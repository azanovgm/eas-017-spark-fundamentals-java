package com.luxoft.training.eas017.day1;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class SparkHelloWorldS3 {

   public static void main(String[] args) {
       final String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
       final String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

       String outputFolder;
       if (args.length == 1) {
           outputFolder = args[0];
       } else {
           throw new IllegalArgumentException("You must provide the name of the output folder");
       }

       final SparkConf conf = new SparkConf()
               .setAppName("Hello World!")
               .setMaster("local[*]");
       final JavaSparkContext sc = new JavaSparkContext(conf);
       sc.hadoopConfiguration().set("fs.s3a.endpoint", "s3.amazonaws.com");
       sc.hadoopConfiguration().set("fs.s3a.access.key", accessKey);
       sc.hadoopConfiguration().set("fs.s3a.secret.key", secretKey);

       final List<String> localCollection = Arrays.asList("Hello", "World!");

       final JavaRDD<String> distributedCollection = sc.parallelize(localCollection, 2);

       distributedCollection
           .saveAsTextFile("s3a://eas017/hello_world_job_output/" + outputFolder);

       sc.stop();

   }

}
