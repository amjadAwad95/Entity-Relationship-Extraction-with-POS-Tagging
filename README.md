# Spark NLP Exercise

## Overview
This project demonstrates the use of [Spark NLP](https://github.com/JohnSnowLabs/spark-nlp) to extract relationships between named entities and their Part of Speech (POS) tags. The objective is to process a given dataset using Spark and apply various NLP techniques through a Spark ML pipeline.

## Task Description
- Load the dataset using Spark.
- Create a Spark ML pipeline incorporating the following annotators (using pre-trained English models):
  - `DocumentAssembler`
  - `Tokenizer`
  - `WordEmbeddingsModel` (GloVe embeddings)
  - `PerceptronModel` (POS tagging)
  - `NerCrfModel` (Named Entity Recognition)
- Transform the data and display only the `POS` and `NER` columns, showing only the `result` attribute of these annotations.
- Analyze the extracted `NER` and `POS` attributes to identify potential relationships between named entities and their parts of speech.

## Installation
Ensure you have Spark NLP installed in your environment. You can add it to your Scala project using:

```scala
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.5.3"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.5.3"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.5.3"
libraryDependencies += "com.johnsnowlabs.nlp" %% "spark-nlp-silicon" % "5.5.0"
```

Additionally, ensure you have Apache Spark configured.

## Basic Imports
To get started, import the necessary modules in Scala:

```scala
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
```

## Documentation & Resources
- Spark NLP Annotators: [John Snow Labs Documentation](https://nlp.johnsnowlabs.com/docs/en/annotators)
- Example Notebooks: [Spark NLP Workshop](https://github.com/JohnSnowLabs/spark-nlp-workshop)

## Running the Project
1. Load the dataset into a Spark DataFrame.
2. Define and configure the Spark NLP pipeline.
3. Transform the dataset using the pipeline.
4. Extract and analyze the `NER` and `POS` attributes.
5. Print the relevant results and insights.

