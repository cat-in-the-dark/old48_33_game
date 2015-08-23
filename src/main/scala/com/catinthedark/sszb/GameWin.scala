package com.catinthedark.sszb

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.sszb.lib.{Magic, KeyAwaitState, Stub}
import Magic._

/**
 * Created by over on 23.08.15.
 */
class GameWin(shared: Shared) extends Stub("GameWin") with KeyAwaitState {
  val batch = new SpriteBatch

  override def run(delay: Float): Option[Unit] = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)

    batch.managed(self => {
      Assets.Fonts.highScoreFont.draw(self, "HIGH SCORES", 350, 240)
      Assets.Fonts.highScoreFont.draw(self, "_" * 32, 280, 220)
      shared.records.all.zipWithIndex.foreach {
        case (value, index) =>
          val name = value.name
          Assets.Fonts.highScoreFont.draw(self, f"$name%15s ${value.time}%5s ", 300, 190 - 30 * index)
      }
    })
    super.run(delay)
  }
  override val keycode: Int = Input.Keys.ENTER
}
