package com.catinthedark.sszb.units

import com.badlogic.gdx.math.{Intersector, Rectangle}
import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.entity.Creatures.Creature
import com.catinthedark.sszb.lib.{Deferred, Pipe, SimpleUnit}

import scala.collection.mutable.ListBuffer

abstract class CollisionControl(shared: Shared) extends SimpleUnit with Deferred {
  val onDie = new Pipe[ListBuffer[Creature]]

  override def run(delta: Float): Unit = {
    val playerRect = new Rectangle(shared.playerX, shared.playerZ, Const.Physics.playerWidth * 0.8f, Const.Physics.playerDepth)
    val toDie = shared.creatures.filter(c => {
      val creatureRect = new Rectangle(c.x+0.2f, c.z, c.width * 0.7f, c.depth)
      Intersector.intersectRectangles(playerRect, creatureRect, new Rectangle())
    })

    onDie(toDie)

    toDie.foreach(creature => {
      creature.isDying = true
      defer(Const.Timing.fallTime, () => shared.creatures -= creature)
    })

    //shared.creatures --= toDie

    if (!toDie.isEmpty) {
      if (!shared.isFalling)
        Assets.Audios.fall.play(Const.soundVolume)
      shared.isFalling = true
      defer(Const.Timing.fallTime, () => shared.isFalling = false)
    }
  }
}
