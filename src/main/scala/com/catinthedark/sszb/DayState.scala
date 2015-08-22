package com.catinthedark.sszb

import com.badlogic.gdx.{Gdx, Input}
import com.catinthedark.sszb.common.Const.Distance
import com.catinthedark.sszb.lib.{Interval, LocalDeferred, SimpleUnit, YieldUnit}
import com.catinthedark.sszb.units.{AI, AIControl, Control, HudView, TransmissionControl, View}

/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  val control = new Control(shared) with LocalDeferred with Interval {
    override val interval = 0.2f
  }
  val transmissionControl = new TransmissionControl(shared) with LocalDeferred
  control.onPedaled + (t => transmissionControl.onPedaled(t))
  var units: Seq[SimpleUnit] = Seq(
    control,
    new AI(shared) with LocalDeferred,
    new AIControl(shared),
    new View(shared) with LocalDeferred,
    new HudView(shared),
    transmissionControl)
  override def toString = "Day"

  override def onActivate(): Unit = {
    shared.prepareGame()
    units.foreach(_.onActivate())
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
    else None
  }
}
