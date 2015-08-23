package com.catinthedark.sszb

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{Gdx, Input, InputAdapter}
import com.catinthedark.sszb.lib.Magic._
import com.catinthedark.sszb.lib.{Stub, YieldUnit}
import com.catinthedark.sszb.records.Record

/**
 * Created by over on 23.08.15.
 */
class NewRecordState(val shared: Shared) extends Stub("NewRecordState") {

  val batch = new SpriteBatch
  var username = "YoBA"
  var done = false
  var state = 0f
  var needUsername = false

  override def onActivate(): Unit = {
    println("time:", shared.lvlTime.toInt)
    needUsername = shared.records.isNewRecord(Record(shared.lvlTime.toInt, "dummy"))

    Gdx.input.setInputProcessor(new InputAdapter {

      override def keyDown(keycode: Int): Boolean = {
        if (keycode == Input.Keys.BACKSPACE) {
          val newLength = username.length - 1
          username = username.substring(0, if (newLength > 0) newLength else 0)
        }

        if (keycode == Input.Keys.ENTER)
          if (username != "")
            done = true

        true
      }
      override def keyTyped(character: Char): Boolean = {
        if (character >= 'A' && character <= 'Z' ||
          character >= 'a' && character <= 'z' ||
          character >= '0' && character <= '9')
          username += character
        println(username)
        true
      }
    })
  }
  override def run(delta: Float): Option[Unit] = {
    if (!needUsername)
      return Some()

    state += delta
    val suffix = if (state - state.toInt < 0.5) "_" else ""

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    batch.managed { self: SpriteBatch =>
      Assets.Fonts.timerFrontFont.draw(self, "NEW RECORD!", 300, 500)
      Assets.Fonts.timerFrontFont.draw(self, "ENTER YOUR NAME", 300, 400)
      Assets.Fonts.timerFrontFont.draw(self, s"${username}${suffix}", 300, 300)
    }

    if (done) {
      shared.records.add(Record(shared.lvlTime.toInt, username))
      Some()
    } else None
  }

  override def onExit(): Unit = {
    println("records ->", shared.records.all)
    done = false
    username = "YoBA"
    Gdx.input.setInputProcessor(null)
  }
}
