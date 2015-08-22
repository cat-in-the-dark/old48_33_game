package com.catinthedark.sszb

import com.badlogic.gdx.{Input, Gdx}
import com.catinthedark.sszb.lib.{LocalDeferred, YieldUnit}
import com.catinthedark.sszb.units.View

/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  val view = new View(shared) with LocalDeferred
  override def toString = "Day"
  override def onActivate(): Unit = {
    view.onActivate()
  }
  override def onExit(): Unit = {
  }
  override def run(delta: Float): Option[Boolean] = {
    view.run(delta)
    if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
      Some(false)
    else
      None
  }
}
