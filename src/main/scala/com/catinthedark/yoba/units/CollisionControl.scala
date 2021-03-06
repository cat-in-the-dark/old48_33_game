package com.catinthedark.yoba.units

import com.badlogic.gdx.math.{Intersector, Rectangle}
import com.catinthedark.lib.{Deferred, Pipe, SimpleUnit}
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.entity.Creatures.Creature
import com.catinthedark.yoba.{Assets, Shared}

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

    if (toDie.nonEmpty) {
      if (!shared.isFalling)
        Assets.Audios.fall.play(Const.soundVolume)
      shared.isFalling = true
      defer(Const.Timing.fallTime, () => shared.isFalling = false)
    }
  }
}
