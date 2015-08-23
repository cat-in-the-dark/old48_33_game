package com.catinthedark.sszb.units

import com.badlogic.gdx.math.{Intersector, Rectangle}
import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.lib.{Deferred, SimpleUnit}

abstract class CollisionControl(shared: Shared) extends SimpleUnit with Deferred {
  override def run(delta: Float): Unit = {
    val playerRect = new Rectangle(shared.playerX, shared.playerZ, Const.Physics.playerWidth, Const.Physics.playerDepth)
    val toDie = shared.creatures.filter(c => {
      val creatureRect = new Rectangle(c.x, c.z, c.width, c.depth)
      Intersector.intersectRectangles(playerRect, creatureRect, new Rectangle())
    })
    
    if (toDie.nonEmpty) {
      println(s"BUM ${toDie.length} objects")
    }
  }
}
