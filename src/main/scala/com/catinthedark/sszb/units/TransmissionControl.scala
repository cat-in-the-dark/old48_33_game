package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.{Physics, Pedals}
import com.catinthedark.sszb.lib.{Deferred, SimpleUnit}

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
        crossedCenter = false
      }
    } else {
      shared.speed = 0
      lastPedal = 0
      currentPedalPosition = leftPedalPosition
    }
    shared.shouldStartTimer = true
    shared.palkaPos = currentPedalPosition
    println(s"$key")
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

    if (shared.speed <= 0.3) {
      shared.speed = 0
      currentPedalPosition = leftPedalPosition
      direction = 1
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
    val a = shared.speed
//    println(s"$a")
  }
}
