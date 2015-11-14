package com.catinthedark.yoba.records

import scala.math.Ordering.FloatOrdering

/**
 * Created by over on 23.08.15.
 */
case class Record(time: Int, name: String, country: String) extends Ordered[Record] {
  val floatOrdering = new FloatOrdering {}
  override def compare(that: Record): Int =
    floatOrdering.compare(time, that.time)
}
