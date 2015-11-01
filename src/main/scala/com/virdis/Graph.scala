package com.virdis

import scala.collection.immutable.HashMap

/**
 * Created by sandeep on 11/1/15.
 */

class Graph {

  /*
         To store the graph's state we use a hashmap where

         key -> Node and value -> Set of Adjacent edges

         Manipulating the internal state of the graph ( add/remove ) nodes is effectively O(1)
   */

  var adjacencyStore = HashMap.empty[String, Set[String]]

  /*
      The only way to manipulate internal state of this graph is by calling processData method.
   */

  def processData(data: SecondFeature) = {

    if (data.hashtags.size > 1) {

      val combinationsOfTags: Iterator[Seq[String]] = data.hashtags.combinations(2) //creates all possible combinations of length 2

      combinationsOfTags.foreach {
        tags =>
          val from = tags.head
          val to   = tags.tail.head
          addEdge(from, to)
          addEdge(to, from)
      }
    }

  }


  /*
      add data if it does not exist
      update existing Set with new data
   */

  private[this] def addEdge(node1: String, node2: String) = {
    if (adjacencyStore.contains(node1)){
      adjacencyStore = adjacencyStore + ((node1, adjacencyStore(node1) + node2))
    } else {
      adjacencyStore = adjacencyStore + ((node1, Set(node2)))
    }
  }

  def averageDegree: BigDecimal = {
    if (adjacencyStore.isEmpty) 0.00
    else {
      var sumOfDegrees: Double = 0.00
      val keysIter: Iterator[String] = adjacencyStore.keysIterator

      while (keysIter.hasNext) {
        sumOfDegrees = sumOfDegrees + adjacencyStore(keysIter.next()).size
      }
      BigDecimal( sumOfDegrees / adjacencyStore.size ).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    }
  }
}
