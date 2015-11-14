package com.catinthedark.yoba.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.catinthedark.lib.{Deferred, LocalDeferred, SimpleUnit}
import com.catinthedark.yoba.Shared

/**
 * Created by over on 22.08.15.
 */
abstract class View(val shared: Shared) extends SimpleUnit with Deferred {
  val views = Seq(new HudView(shared),
    new BGView(shared),
    new RoadView(shared) with LocalDeferred)

  override def onActivate(): Unit = views.foreach(_.onActivate())
  override def run(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    //Gdx.gl.glEnable(GL20.GL_BLEND);

    views.foreach(_.run(delta))
  }
  override def onExit(): Unit = views.foreach(_.onExit())
}
