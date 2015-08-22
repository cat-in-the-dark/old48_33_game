package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.lib.{Deferred, SimpleUnit}

abstract class CollisionControl(shared: Shared) extends SimpleUnit with Deferred {
  override def run(delta: Float): Unit = {
    val toDie = shared.creatures.filter(c => {
      false
    })
  }
}
