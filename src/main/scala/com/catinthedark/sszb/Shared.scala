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
             var cursorPosition: Float,
             val creatures: mutable.ListBuffer[Creature],
             var lastSpawnDistance: Float = 0f,
             var shouldStartTimer: Boolean = false,
             var playerX: Float = 0f
              ) {

  def reset() = {
    lvlTime = 0f
    lvlDistance = 0f
    lvl = 1
    speed = 0f
    cursorPosition = 0f
    creatures.clear()
    lastSpawnDistance = 0f
    shouldStartTimer = false
    playerX = 0
  }

  /**
   * Called in onActivate in DayState
   * @see DayState
   */
  def prepareGame() = {
    lvlTime = 0f
    lvlDistance = 0f
    speed = 0f
    cursorPosition = 0f
    creatures.clear()
    lastSpawnDistance = 0f
    shouldStartTimer = false
    playerX = 0
  }
}