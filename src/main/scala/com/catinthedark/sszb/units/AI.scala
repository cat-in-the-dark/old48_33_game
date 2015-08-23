package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.Creatures
import com.catinthedark.sszb.lib.{Deferred, SimpleUnit}

import scala.util.Random

abstract class AI(shared: Shared) extends SimpleUnit with Deferred {
  val rand = new Random()
  
  override def run(delta: Float): Unit = {
    val seed = rand.nextInt() % Difficulty.seedDivider
    val (needX1, needX2, needX3) = Difficulty.spawnRandom(shared.lvl, shared.lvlDistance, shared.lastSpawnDistance, seed)
    
    if (needX1) {
      val c = Creatures.create(shared, Const.Physics.roadRowPos(-1), Const.Physics.spawnPlaceZ)
      shared.creatures += c
      shared.lastSpawnDistance = shared.lvlDistance
    }
    
    if (needX2) {
      val c = Creatures.create(shared, Const.Physics.roadRowPos(0), Const.Physics.spawnPlaceZ)
      shared.creatures += c
      shared.lastSpawnDistance = shared.lvlDistance
    }
    
    if (needX3) {
      val c = Creatures.create(shared, Const.Physics.roadRowPos(1), Const.Physics.spawnPlaceZ)
      shared.creatures += c
      shared.lastSpawnDistance = shared.lvlDistance
    }
  }
}
