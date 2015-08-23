package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.entity.Creatures
import com.catinthedark.sszb.lib.SimpleUnit

import scala.util.Random

class TrashGenerator(shared: Shared) extends SimpleUnit {
  val rand = new Random()
  var lastSpawnDistanceSign = 0f
  var lastSpawnDistanceLamp = 0f
  var lastSpawnDistanceTree = 0f
  
  val spawnDeltaSign = 1.8f
  val spawnDeltaLamp = 2.0f
  val spawnDeltaTree = 1.3f
  
  override def run(delta: Float): Unit = {
    if (shared.lvlDistance - lastSpawnDistanceSign > spawnDeltaSign) {
      lastSpawnDistanceSign = shared.lvlDistance
      val seed = rand.nextInt()
      if (seed % 2 == 1) {
        shared.trash += Creatures.createSign(shared, 1.4f, Const.Physics.spawnPlaceZ)
      } else {
        shared.trash += Creatures.createSign(shared, -1.5f, Const.Physics.spawnPlaceZ)
      } 
    }

    if (shared.lvlDistance - lastSpawnDistanceLamp > spawnDeltaLamp) {
      lastSpawnDistanceLamp = shared.lvlDistance
      val seed = rand.nextInt()
      if (seed % 2 == 1) {
        shared.trash += Creatures.createLamp(shared, 1.0f, Const.Physics.spawnPlaceZ)
      } else {
        shared.trash += Creatures.createLamp(shared, -1.25f, Const.Physics.spawnPlaceZ)
      }
    }

    if (shared.lvlDistance - lastSpawnDistanceTree > spawnDeltaTree) {
      lastSpawnDistanceTree = shared.lvlDistance
      val seed = rand.nextInt()
      if (seed % 2 == 1) {
        shared.trash += Creatures.createTree(shared, 1.3f, Const.Physics.spawnPlaceZ)
      } else {
        shared.trash += Creatures.createTree(shared, -1.4f, Const.Physics.spawnPlaceZ)
      }
    }
    
    shared.trash.foreach(t => {
      t.z -= shared.speed * delta
    })
  }
}
