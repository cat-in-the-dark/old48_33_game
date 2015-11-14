package com.catinthedark.yoba

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{Gdx, Input, InputAdapter}
import com.catinthedark.lib.Magic._
import com.catinthedark.lib.{Magic, YieldUnit}

/**
 * Created by over on 23.08.15.
 */
class NewRecordState(val shared: Shared) extends YieldUnit[Unit] {
  override def toString: String = "NewRecordState"

  val batch = new SpriteBatch
  var username = "YoBA"
  var done = false
  var state = 0f

  override def onActivate(): Unit = {
    println("time:", shared.lvlTime.toInt)
    username = "YoBA"
    done = false
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
    state += delta
    val suffix = if (state - state.toInt < 0.5) "_" else ""

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    batch.managed { self: SpriteBatch =>
      //Assets.Fonts.timerFrontFont.draw(self, "NEW RECORD!", 300, 500)
      Assets.Fonts.timerFrontFont.draw(self, "ENTER YOUR NAME", 300, 400)
      Assets.Fonts.timerFrontFont.draw(self, s"${username}${suffix}", 300, 300)
    }

    if (done) {
      shared.username = username
      Some()
    }
    else None
  }

  override def onExit(): Unit = {
    println("username ->", username)
    Gdx.input.setInputProcessor(null)
  }
}
