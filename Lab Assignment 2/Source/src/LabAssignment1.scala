/**
  * Created by Ramgopal on 1/30/2017.
  */
import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm.WordListener
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.dmg.pmml.True



object LabAssignment1 {

  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\Users\\Ramgopal\\Documents\\Softwares\\winutils");
    val conf = new SparkConf().setAppName("LabAssignment1").setMaster("local")
    val context = new SparkContext(conf)

    //Opening the text file 'Orders.txt'
    val transactions = context.textFile("Orders.txt")

    //map transformation -- split the file contents with tab separation
    val newTransactionsPair = transactions.map{t =>
      val p = t.split("\t")
      (p(1).toString,1)
    }


    //reduceByKey transformation -- reduce the distinct customers count
    val op3= newTransactionsPair.reduceByKey((x,y) => x+y)

    //sortBy transformation -- to sort the RDD based on value
    val results = op3.sortBy(_._2, false)

    //SaveAsTextFile action -- to save the sorted rdd
    results.saveAsTextFile("Sorted_orders")

    //first action -- to retrieve the first element from RDD
    var z = results.first()

    //print the first element from RDD
    print("Customer with more number of purchases:"+z)

    //Stop the SparkContext
    context.stop()
  }
}