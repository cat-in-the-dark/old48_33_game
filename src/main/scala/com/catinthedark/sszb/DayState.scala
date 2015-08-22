package com.catinthedark.sszb

import com.badlogic.gdx.{Input, Gdx}
import com.catinthedark.sszb.common.Const.Distance
import com.catinthedark.sszb.lib.{SimpleUnit, Interval, LocalDeferred, YieldUnit}
import com.catinthedark.sszb.units.{AI, AIControl, Control, HudView}

/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  var units: Seq[SimpleUnit] = Seq()
  
  override def toString = "Day"
  val control = new Control(shared) with LocalDeferred with Interval {
    override val interval = 0.2f
  }
  override def onActivate(): Unit = {
    shared.prepareGame()
    control.onActivate()
    val ai = new AI(shared) with LocalDeferred
    val aiControl = new AIControl(shared)
    val hud = new HudView(shared)
    
    units = Seq(control, ai, aiControl, hud)
  }
  override def onExit(): Unit = {
    units.foreach(_.onExit())
  }
  override def run(delta: Float): Option[Boolean] = {
    units.foreach(_.run(delta))
    shared.lvlTime += delta
    
    if (shared.lvlDistance >= Distance.levelDistance) {
      Some(true)
    } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
      Some(false)
    else
      None
  }
}
