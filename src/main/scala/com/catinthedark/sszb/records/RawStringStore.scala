package com.catinthedark.sszb.records

import com.catinthedark.sszb.lib.records.Store

/**
 * Created by over on 23.08.15.
 */
trait RawStringStore extends Store[Record] {
  override def toData(data: Record): String = s"${data.time} ${data.name}\n"
  override def fromData(raw: String): Record = {
    val parsed = raw.split(" ");
    Record(parsed(0).toInt, parsed(1))
  }
}
