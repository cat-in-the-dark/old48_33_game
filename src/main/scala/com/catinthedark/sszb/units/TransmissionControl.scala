package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.Pedals
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

  override def run(delta: Float): Unit = {
    currentPedalPosition += delta * scale * direction * shared.speed
    if (currentPedalPosition > 1 || currentPedalPosition < 0) {
      direction *= -1
    }
    shared.cursorPosition = currentPedalPosition * sliderWidth
//    println(String.format(s"$currentPedalPosition"))
  }
}
