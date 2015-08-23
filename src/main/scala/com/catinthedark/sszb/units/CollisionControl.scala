package com.catinthedark.sszb.units

import com.badlogic.gdx.math.{Intersector, Rectangle}
import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.entity.Creatures.Creature
import com.catinthedark.sszb.lib.{Deferred, Pipe, SimpleUnit}

import scala.collection.mutable.ListBuffer

abstract class CollisionControl(shared: Shared) extends SimpleUnit with Deferred {
  val onDie = new Pipe[ListBuffer[Creature]]

  override def run(delta: Float): Unit = {
    val playerRect = new Rectangle(shared.playerX, shared.playerZ, Const.Physics.playerWidth, Const.Physics.playerDepth)
    val toDie = shared.creatures.filter(c => {
      val creatureRect = new Rectangle(c.x, c.z, c.width, c.depth)
      Intersector.intersectRectangles(playerRect, creatureRect, new Rectangle())
    })

    onDie(toDie)
    shared.creatures --= toDie
    if (!toDie.isEmpty) {
      shared.isFalling = true
      defer(Const.Timing.fallTime, () => shared.isFalling = false)
    }
  }
}
