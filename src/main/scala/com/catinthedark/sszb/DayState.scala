package com.catinthedark.sszb

import com.badlogic.gdx.{Input, Gdx}
import com.catinthedark.sszb.common.Const.Distance
import com.catinthedark.sszb.lib.{Interval, LocalDeferred, YieldUnit}
import com.catinthedark.sszb.units.Control

/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  override def toString = "Day"
  val control = new Control(shared) with LocalDeferred with Interval {
    override val interval = 0.2f
  }
  override def onActivate(): Unit = {
    control.onActivate()
  }
  override def onExit(): Unit = {

  }
  override def run(delta: Float): Option[Boolean] = {
    shared.lvlTime += delta
    
    if (shared.lvlDistance >= Distance.levelDistance) {
      Some(true)
    } else if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
      Some(false)
    else
      None
  }
}
