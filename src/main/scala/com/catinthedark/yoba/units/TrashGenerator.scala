package com.catinthedark.yoba.units

import com.catinthedark.lib.SimpleUnit
import com.catinthedark.yoba.Shared
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.entity.Creatures

import scala.util.Random

class TrashGenerator(shared: Shared) extends SimpleUnit {
  val rand = new Random()
  var lastSpawnDistanceSign = 0f
  var lastSpawnDistanceLamp = 0f
  var lastSpawnDistanceTree = 0f
  var lastSpawnDistanceBush = 0f
  
  val spawnDeltaSign = 1.3f
  val spawnDeltaLamp = 1.8f
  val spawnDeltaTree = 0.6f
  val spawnDeltaBush = 0.2f
  
  val signLeftPos = 1.4f
  val signRightPos = -1.5f
  
  val lampLeftPos = 1.0f
  val lampRightPos = -1.25f
  
  val treeLeftPos = 1.3f
  val treeRightPos = -1.4f
  
  val bushLeftPos = 2f
  val bushRightPos = -2.1f

  override def onExit(): Unit = {
    lastSpawnDistanceSign = 0f
    lastSpawnDistanceLamp = 0f
    lastSpawnDistanceTree = 0f
    lastSpawnDistanceBush = 0f
  }

  override def run(delta: Float): Unit = {
    if (shared.lvlDistance - lastSpawnDistanceSign > spawnDeltaSign) {
      lastSpawnDistanceSign = shared.lvlDistance
      rand.nextInt(4) match {
        case 0 =>
          shared.trash += Creatures.createSign(shared, signLeftPos, placeZRandom())
        case 1 =>
          shared.trash += Creatures.createSign(shared, signRightPos, placeZRandom())
        case _ =>
          shared.trash += Creatures.createSign(shared, signLeftPos, placeZRandom())
          shared.trash += Creatures.createSign(shared, signRightPos, placeZRandom())
      }
    }

    if (shared.lvlDistance - lastSpawnDistanceLamp > spawnDeltaLamp) {
      lastSpawnDistanceLamp = shared.lvlDistance
      rand.nextInt(4) match {
        case 0 =>
          shared.trash += Creatures.createLamp(shared, lampLeftPos, placeZRandom())
        case 1 =>
          shared.trash += Creatures.createLamp(shared, lampRightPos, placeZRandom())
        case _ =>
          shared.trash += Creatures.createLamp(shared, lampRightPos, placeZRandom())
          shared.trash += Creatures.createLamp(shared, lampLeftPos, placeZRandom())
      }
    }

    if (shared.lvlDistance - lastSpawnDistanceTree > spawnDeltaTree) {
      lastSpawnDistanceTree = shared.lvlDistance
      rand.nextInt(4) match {
        case 0 =>
          shared.trash += Creatures.createTree(shared, treeLeftPos + littleRandom(), placeZRandom())
        case 1 =>
          shared.trash += Creatures.createTree(shared, treeRightPos + littleRandom(), placeZRandom())
        case _ =>
          shared.trash += Creatures.createTree(shared, treeLeftPos + littleRandom(), placeZRandom())
          shared.trash += Creatures.createTree(shared, treeRightPos + littleRandom(), placeZRandom())
      }
    }

    if (shared.lvlDistance - lastSpawnDistanceBush > spawnDeltaBush) {
      lastSpawnDistanceBush = shared.lvlDistance
      rand.nextInt(4) match {
        case 0 =>
          shared.trash += Creatures.createBush(shared, bushLeftPos + littleRandom(), placeZRandom())
        case 1 =>
          shared.trash += Creatures.createBush(shared, bushRightPos + littleRandom(), placeZRandom())
        case _ =>
          shared.trash += Creatures.createBush(shared, bushLeftPos + littleRandom(), placeZRandom())
          shared.trash += Creatures.createBush(shared, bushRightPos + littleRandom(), placeZRandom())
      }
    }
    
    shared.trash.foreach(t => {
      t.z -= shared.speed * delta * Const.Physics.trashSpeedScale
    })
  }
  
  private def littleRandom(): Float = {
    (rand.nextInt(6) - 3) / 10f
  }
  
  private def placeZRandom() = {
    val z = (rand.nextInt(4) - 2) / 10f
    Const.Physics.spawnPlaceZ + z 
  }
}
