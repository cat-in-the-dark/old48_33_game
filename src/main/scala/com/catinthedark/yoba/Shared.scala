package com.catinthedark.yoba

import com.catinthedark.yoba.entity.Creatures
import com.catinthedark.yoba.entity.Creatures.Creature

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
             var palkaPos: Float = -1.0f,
             var shouldStartTimer: Boolean = false,
             var playerX: Float = 0f,
             var isFalling: Boolean = false,
             var playerZ: Float = 0.05f,
             var trash: mutable.ListBuffer[Creature],
             var username: String = ""
              ) {

  def reset() = {
    lvlTime = 0f
    lvlDistance = 0f
    lvl = 1
    speed = 0f
    cursorPosition = 0f
    creatures.clear()
    lastSpawnDistance = 0f
    palkaPos = -1.0f
    shouldStartTimer = false
    isFalling = false
    playerX = 0
    playerZ = 0.05f
    trash.clear()
    username = ""
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
    palkaPos = -1.0f
    shouldStartTimer = false
    isFalling = false
    playerX = 0
    playerZ = 0.05f
    trash.clear()
    username = ""

    preset()
  }

  def preset(): Unit = {
    trash += Creatures.createSign(this, 1.4f, 0.3f)
    trash += Creatures.createSign(this, -1.5f, 1.5f)
    trash += Creatures.createSign(this, -1.5f, 0.03f)
    trash += Creatures.createLamp(this, -1.25f, 1f)
    trash += Creatures.createLamp(this, 1f, 0.01f)
    trash += Creatures.createLamp(this, 1f, 0.7f)
    trash += Creatures.createLamp(this, 1f, 0.4f)
    trash += Creatures.createTree(this, 1.2f, 0.1f)
    trash += Creatures.createTree(this, 1.2f, 0.4f)
    trash += Creatures.createTree(this, 1.2f, 0.8f)
    trash += Creatures.createTree(this, 1.2f, 1.2f)
  }
}