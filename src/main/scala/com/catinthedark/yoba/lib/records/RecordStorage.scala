package com.catinthedark.yoba.lib.records

import java.io.{File, FileWriter}

import scala.io.Source

/**
 * Created by over on 23.08.15.
 */
abstract class RecordStorage[T <: Ordered[T]](val fname: String, maxSize: Int) extends Store[T] {
  def withFile[U](fname: String, f: Source => U) = {
    val file = new File(fname)
    file.createNewFile()

    val source = Source.fromFile(file)
    val res = f(source)
    source.close()
    res
  }

  private var records = withFile(fname, source => {
    source.getLines().map(str => fromData(str)).toList
  })

  def add(newRecord: T): Unit = {
    records = (newRecord :: records).sorted.distinct.take(maxSize)

    val writer = new FileWriter(fname, false)
    records.map(r => toData(r)).foreach(s => writer.write(s))
    writer.close()
  }

  def isNewRecord(record: T): Boolean =
    records.length < maxSize || records.exists(_ < record)


  def all = records
}
