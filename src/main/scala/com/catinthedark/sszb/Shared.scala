package com.catinthedark.sszb

import com.catinthedark.sszb.entity.Creatures.Creature

import scala.collection.mutable

/**
 * Created by over on 18.04.15.
 */
class Shared(var lvlTime: Float,
             var lvlDistance: Float,
             var lvl: Int,
             var speed: Float,
             val creatures: mutable.ListBuffer[Creature],
             var lastSpawnDistance: Float = 0f) {

  def reset() = {
    lvl = 1
    lvlTime = 0f
    lvlDistance = 0f
    speed = 0f
    lastSpawnDistance = 0f
    creatures.clear()
  }

  /**
   * Called in onActivate in DayState
   * @see DayState
   */
  def prepareGame() = {
    lvlTime = 0f
    speed = 0f
    lvlDistance = 0f
    lastSpawnDistance = 0f
    creatures.clear()
  }
}