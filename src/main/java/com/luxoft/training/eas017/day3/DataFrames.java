package com.luxoft.training.eas017.day3;

import org.apache.commons.io.output.NullPrintStream;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import java.util.Arrays;
import java.util.List;

import static org.apache.spark.sql.functions.*;

public class DataFrames {

    public static void main(String[] args) {
        System.setErr(new NullPrintStream());
        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("DataFrames")
                .getOrCreate();

        List<Row> rows = Arrays.asList(
            RowFactory.create(1, "A"),
            RowFactory.create(2, "B"),
            RowFactory.create(3, "C")
        );

        StructType schema = new StructType()
            .add("c1", DataTypes.IntegerType)
            .add("c2", DataTypes.StringType);
        
        Dataset<Row> df = spark.createDataFrame(rows, schema);
        df.show();
        df.printSchema();
        
        df.select("c1");

        // Writing sql over dataframe
        df.createOrReplaceTempView("df");
        spark.sql("select c1 from df").show();

        
        df.where(col("c1").$greater$eq(lit(2))).show();

        df.union(df).groupBy(col("c2")).count().show();

    }
}
