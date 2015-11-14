package com.catinthedark.yoba.lib.records

/**
 * Created by over on 23.08.15.
 */
trait Store[T] {
  def toData(data: T): String
  def fromData(raw: String): T
}
