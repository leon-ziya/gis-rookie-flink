import cn.edu.gis.rookie.algorithm.util.dbscan.DbscanUtil

/**
 * @ClassName DbscanTest.java
 * @author leon
 * @createTime 2020年10月19日 16:31:00
 */
object DbscanTest {

  def main(args: Array[String]): Unit = {
    val dbscanUtil = new DbscanUtil
    val points: Array[Vector[Double]] = dbscanUtil.readSource("/home/jianglai/space/AIRS/clusterPoint/points.csv", 2)


  }

}
