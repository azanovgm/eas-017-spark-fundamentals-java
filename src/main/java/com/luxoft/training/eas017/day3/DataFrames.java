package com.luxoft.training.eas017.day3;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.sql.SparkSession;

public class DataFrames {

    public static void main(String[] args) {
        System.setErr(new NullPrintStream());
        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("DataFrames")
                .getOrCreate();
        

    }
}
