package com.catinthedark.sszb

import com.badlogic.gdx.{Gdx, Input}
import com.catinthedark.sszb.common.Const.Distance
import com.catinthedark.sszb.lib.{Interval, LocalDeferred, SimpleUnit, YieldUnit}
import com.catinthedark.sszb.units.{AI, AIControl, Control, HudView, View}


/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  var units: Seq[SimpleUnit] = Seq(
    new Control(shared) with LocalDeferred with Interval {
      override val interval = 0.2f
    },
    new AI(shared) with LocalDeferred,
    new AIControl(shared),
    new HudView(shared),
    new View(shared) with LocalDeferred)

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
