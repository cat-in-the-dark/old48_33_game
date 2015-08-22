package com.catinthedark.sszb

/**
 * Created by over on 18.04.15.
 */
class Shared(var lvlTime: Float,
             var lvlDistance: Float,
             var speed: Float) {
  def reset() = {
    lvlTime = 0f
    lvlDistance = 0f
    speed = 0f
  }

  /**
   * Called in onActivate in DayState
   * @see DayState
   */
  def prepareGame() = {
    lvlTime = 0f
    speed = 0f
    lvlDistance = 0f
  }
}