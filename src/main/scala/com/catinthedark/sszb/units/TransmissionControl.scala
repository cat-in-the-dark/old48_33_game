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
  val sliderWidth: Int = 200
  var currentPedalPosition: Float = leftPedalPosition
  var currentPedal: Int = Pedals.leftPedalKey
  var direction: Int = 1

  def isTimeToPedalLeft(currentPosition: Float): Int = {
    if (currentPosition <= 0.5f) {
      Pedals.leftPedalKey
    } else {
      Pedals.rightPedalKey
    }
  }


  override def onActivate(): Unit = {
    super.onActivate()
    currentPedalPosition = leftPedalPosition
  }

  def onPedaled(key: Int): Unit = {
    currentPedal = key
    
    if (key == isTimeToPedalLeft(currentPedalPosition)) {
      shared.speed += Math.abs(currentPedalPosition - centerPedalPosition)
    } else {
      shared.speed = 0
      currentPedal = Pedals.leftPedalKey
      currentPedalPosition = leftPedalPosition
    }
    
    shared.shouldStartTimer = true
    println(s"$currentPedal")
  }

  def speedToFriction(speed: Float): Float = {
    1 - Math.cos(speed / Physics.maxSpeed * Math.PI / 2).toFloat
  }

  override def run(delta: Float): Unit = {
    currentPedalPosition += delta * scale * direction * shared.speed

    shared.speed -= speedToFriction(shared.speed) * delta

    if (shared.speed <= 0.0001) {
      shared.speed = 0
      currentPedalPosition = leftPedalPosition
      direction = 1
    } else if (shared.speed >= 10.0f) {
      shared.speed = 10.0f
    }
    if (currentPedalPosition > 1) {
      direction *= -1
      currentPedalPosition = 1
    } else if (currentPedalPosition < 0) {
      direction *= -1
      currentPedalPosition = 0
    }
    shared.cursorPosition = currentPedalPosition * sliderWidth
    val a = shared.speed
//    println(s"$a")
  }
}
