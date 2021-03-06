package com.catinthedark.yoba.units

import com.catinthedark.lib.{Deferred, SimpleUnit}
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.common.Const.{Pedals, Physics}
import com.catinthedark.yoba.{Assets, Shared}

/**
 * Created by kirill on 22.08.15.
 */
abstract class TransmissionControl(shared: Shared) extends SimpleUnit with Deferred {

  val leftPedalPosition = 0.0f
  val rightPedalPosition = 1.0f
  val centerPedalPosition = (rightPedalPosition - leftPedalPosition) / 2
  val scale: Float = 1.0f
  var currentPedalPosition: Float = leftPedalPosition
  var lastPedalPosition: Float = leftPedalPosition
  var lastPedal: Int = 0
  var direction: Int = 1
  var crossedCenter:Boolean = false

  def currentPedalKey(currentPosition: Float): Int = {
    if (currentPosition <= 0.5f) {
      Pedals.leftPedalKey
    } else {
      Pedals.rightPedalKey
    }
  }


  override def onActivate(): Unit = {
    super.onActivate()
    currentPedalPosition = leftPedalPosition
    lastPedalPosition = leftPedalPosition
    direction = 1
    lastPedal = 0
  }

  def onPedaled(key: Int): Unit = {
    if (key == currentPedalKey(currentPedalPosition)) {
      if (key != lastPedal || crossedCenter) {
        shared.speed += Math.abs(currentPedalPosition - centerPedalPosition)
        lastPedal = key
        shared.palkaPos = currentPedalPosition
        crossedCenter = false
        if (shared.speed < Physics.maxSpeed / 3) {
          Assets.Audios.pedal1.play(Const.soundVolume)
        } else {
          Assets.Audios.pedal2.play(Const.soundVolume)
        }
      }
    } else {
      shared.isFalling = true
      defer(Const.Timing.fallTime, () => shared.isFalling = false)
      Assets.Audios.fall.play(Const.soundVolume)
    }
    shared.shouldStartTimer = true
  }

  def speedToFriction(speed: Float): Float = {
    1 - Math.cos(speed / Physics.maxSpeed * Math.PI / 2).toFloat
  }

  override def run(delta: Float): Unit = {
    currentPedalPosition += delta * scale * direction * shared.speed
    if ((currentPedalPosition > centerPedalPosition && lastPedalPosition <= centerPedalPosition)
    || (currentPedalPosition < centerPedalPosition && lastPedalPosition >= centerPedalPosition)) {
      crossedCenter = true
    }
    lastPedalPosition = currentPedalPosition
    shared.speed -= speedToFriction(shared.speed) * delta

    if (shared.isFalling) {
      shared.speed -= 5* delta
    }
    if (shared.speed <= 0.3) {
      shared.speed = 0
      currentPedalPosition = leftPedalPosition
      direction = 1
      lastPedal = 0
      shared.palkaPos = leftPedalPosition
      currentPedalPosition = leftPedalPosition
    } else if (shared.speed >= Physics.maxSpeed) {
      shared.speed = Physics.maxSpeed
    }
    if (currentPedalPosition > 1) {
      direction *= -1
      currentPedalPosition = 1
    } else if (currentPedalPosition < 0) {
      direction *= -1
      currentPedalPosition = 0
    }
    shared.cursorPosition = currentPedalPosition
  }
}
