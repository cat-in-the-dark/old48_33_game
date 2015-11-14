package com.catinthedark.yoba

import com.badlogic.gdx.{Gdx, Input}
import com.catinthedark.yoba.common.Const.Distance
import com.catinthedark.yoba.lib.{Interval, LocalDeferred, SimpleUnit, YieldUnit}
import com.catinthedark.yoba.units._

/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  val control = new Control(shared) with LocalDeferred
  val transmissionControl = new TransmissionControl(shared) with LocalDeferred
  control.onPedaled + (t => transmissionControl.onPedaled(t))
  val collision = new CollisionControl(shared) with LocalDeferred

//  collision.onDie + (deathNode => {
//    if (!deathNode.isEmpty) {
//      println("die")
//    }
//  })
  var units: Seq[SimpleUnit] = Seq(
    control,
    new AI(shared) with LocalDeferred,
    new AIControl(shared),
    new BGView(shared) with LocalDeferred,
    new View(shared) with LocalDeferred,
    new HudView(shared),
    transmissionControl,
    new LooseControl(shared),
    new TrashGenerator(shared),
    collision,
    new AudioDirector(shared))

  override def toString = "Day"

  override def onActivate(): Unit = {
    val time = shared.lvlTime
    val lvl = shared.lvl
    shared.prepareGame()
    shared.lvlTime = time
    shared.lvl = lvl
    units.foreach(_.onActivate())
    Assets.Audios.bgm.setVolume(0)
    Assets.Audios.bgm.play()
  }

  override def onExit(): Unit = {
    Assets.Audios.bgm.stop()
    units.foreach(_.onExit())
  }
  override def run(delta: Float): Option[Boolean] = {
    units.foreach(_.run(delta))

    if (shared.shouldStartTimer) {
      shared.lvlTime += delta
    }
    shared.lvlDistance += shared.speed * delta

    //println(s"DISTANCE ${shared.lvlDistance}")
    //println(s"Creatures ${shared.creatures}")

    if (shared.lvlDistance >= Distance.levelDistance) {
      Some(true)
    } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
      Some(false)
    else None
  }
}
