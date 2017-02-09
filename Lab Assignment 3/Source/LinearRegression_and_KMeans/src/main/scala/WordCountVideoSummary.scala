import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm.WordListener
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.dmg.pmml.True

/**
  * Created by Ramgopal on 2/8/2017.
  */
object WordCountVideoSummary {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\Users\\Ramgopal\\Documents\\Softwares\\winutils");
    val conf = new SparkConf().setAppName("WordCountVideoSummary").setMaster("local")
    val context = new SparkContext(conf)
    val transactions = context.textFile("summary.txt")
    val wc=transactions.flatMap(line=>{line.split("\n")}).map(word=>(word,1)).cache()
    val output=wc.reduceByKey(_+_)
    val results = output.sortBy(_._2, false)
    results.saveAsTextFile("Sorted_text")

    //Stop the SparkContext
    context.stop()
  }
}
