package com.catinthedark.sszb.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.lib.{LocalDeferred, Deferred, SimpleUnit}

/**
 * Created by over on 22.08.15.
 */
abstract class View(val shared: Shared) extends SimpleUnit with Deferred {
  val views = Seq(new HudView(shared),
    new RoadView(shared) with LocalDeferred)

  override def onActivate(): Unit = views.foreach(_.onActivate())
  override def run(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)

    views.foreach(_.run(delta))
  }
  override def onExit(): Unit = views.foreach(_.onExit())
}
