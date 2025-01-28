import com.johnsnowlabs.nlp.annotator.{
  Tokenizer,
  WordEmbeddingsModel,
  SentenceDetector,
  NerCrfModel
}
import com.johnsnowlabs.nlp.annotators.pos.perceptron.PerceptronModel
import com.johnsnowlabs.nlp.{DocumentAssembler, SparkNLP}
import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.functions._



object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkNLP.start()

    val textDF = spark.read.parquet("data/spark_nlp_dataset.parquet")
    textDF.show(false)

    val documentAssembler =
      new DocumentAssembler()
        .setInputCol("text")
        .setOutputCol("document")
        .setCleanupMode("shrink")

    val tokenizer =
      new Tokenizer().setInputCols(Array("document")).setOutputCol("tokens")

    val embeddings =
      WordEmbeddingsModel
        .pretrained("glove_100d", "en")
        .setInputCols(Array("document", "tokens"))
        .setOutputCol("embeddings")

    val posTagger = PerceptronModel
      .pretrained()
      .setInputCols("document", "tokens")
      .setOutputCol("pos")

    val nerTagger = NerCrfModel
      .pretrained()
      .setInputCols("document", "tokens", "embeddings", "pos")
      .setOutputCol("ner")

    val pipeline = new Pipeline().setStages(
      Array(
        documentAssembler,
        tokenizer,
        embeddings,
        posTagger,
        nerTagger
      )
    )
    val model = pipeline.fit(textDF)

    val processedDF = model.transform(textDF)

    val extracted = processedDF.selectExpr(
      "explode(pos.result) as pos",
      "explode(ner.result) as ner"
    )

    extracted.show(false)

    import spark.implicits._

    val posNerCounts =
      extracted.groupBy("pos", "ner").count().orderBy($"count".desc)

    posNerCounts.show(false)

    val posTotals = posNerCounts
      .groupBy("pos")
      .agg(sum("count").alias("total_count"))

    val posNerProportions = posNerCounts
      .join(posTotals, "pos")
      .withColumn("proportion", col("count") / col("total_count"))
      .orderBy($"proportion".desc)

    posNerProportions.show(false)

    spark.stop()
  }
}
