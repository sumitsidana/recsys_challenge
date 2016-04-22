        import org.apache.spark.mllib.recommendation.ALS
        import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
        import org.apache.spark.mllib.recommendation.Rating
        import org.apache.spark.ml.feature.StringIndexer
        import org.apache.spark.sql.Row
        
        
        val time1 = java.lang.System.currentTimeMillis()
        
        //val sqlContext = new org.apache.spark.sql.SQLContext(sc)
        val df = sqlContext.read
    .format("com.databricks.spark.csv")
    .option("header", "true") // Use first line of all files as header
    .option("inferSchema", "true") // Automatically infer data types
    .option("delimiter", "\t")
    .load("/home/ama/sidana/recommendersystemchallenge/data/data/interactions.csv")
        val validInteractions = df.filter(df("interaction_type") === 1||df("interaction_type")===2||df("interaction_type")===3)
        val clickedoffers = validInteractions.select("user_id", "item_id", "interaction_type")
        val groupedOffers = clickedoffers.groupBy("user_id","item_id").count
        val ratings = groupedOffers.map{
    case Row(user_id, item_id, count) => Rating(user_id.asInstanceOf[Int].intValue, item_id.asInstanceOf[Int].intValue, count.asInstanceOf[Long].doubleValue)
}
        val rank = 100
        val numIterations = 20
        val model = ALS.train(ratings, rank, numIterations, 0.01)
        val time2 = java.lang.System.currentTimeMillis()
        val time = time2 - time1
        print(time)
        
        val dfItem = sqlContext.read
    .format("com.databricks.spark.csv")
    .option("header", "true") // Use first line of all files as header
    .option("inferSchema", "true") // Automatically infer data types
    .option("delimiter", "\t")
    .load("/home/ama/sidana/recommendersystemchallenge/data/data/items.csv")

    val validItems = dfItem.filter(dfItem("active_during_test")===1).select("id")
    val validitemsRDD = validItems.collect()
    
    
val target_users = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").load("/home/ama/sidana/recommendersystemchallenge/data/target_users.csv")
import java.nio.file.{ Files, Paths }
import scala.collection.mutable.ListBuffer
import java.io.PrintWriter
import util.control.Breaks._

val pw = new PrintWriter("/home/ama/sidana/recommendersystemchallenge/output/als_v2.txt");
val total = target_users.count - 1
val actualusers = target_users.collect()
val keys = model.userFeatures.keys.collect()
for(user <- 0 to total.toInt){
    val user_id = actualusers(user).getString(0)
    pw.print(user_id+"\t")
    breakable{
        if(!(keys.exists(_ == user_id.toInt))){
            pw.print("1053452,2268722,1007923,2778525,1244196,1729618,657183,581327,2791339,1386412,2663343,1805804,1078303,536047,1053542,2432923,278589,79531,1928254,2446769,1984327,1583705,1133414,2242152,1742926,1201171,2532610,784737,1233470,830073")
            pw.println()
            break
        }
    else{
    val topKRecs = model.recommendProducts(user_id.toInt,100)
     topKRecs.map(rating => (rating.product)).foreach(a=>if((validitemsRDD.exists(_ == a.toInt))){pw.print(a+",")})
    pw.println()
   }
    }
}
pw.close()


