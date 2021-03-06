package com.catinthedark.yoba.units


import com.badlogic.gdx.{Gdx, Input, InputAdapter}
import com.catinthedark.lib.{Deferred, Pipe, SimpleUnit}
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.common.Const.Pedals
import com.catinthedark.yoba.{Assets, Shared}

/**
 * Created by over on 22.01.15.
 */
abstract class Control(shared: Shared) extends SimpleUnit with Deferred {

  val onPedaled = new Pipe[(Int)]

  override def onActivate(): Unit = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        keycode match {
          case Const.Pedals.leftPedalKey =>
            onPedaled(Pedals.leftPedalKey)
          case Const.Pedals.rightPedalKey =>
            onPedaled(Pedals.rightPedalKey)
          case _ =>

        }
        true
      }

      override def keyUp(keycode: Int): Boolean = {
        keycode match {
          case Input.Keys.LEFT =>
            leftAllowed = true
          case Input.Keys.RIGHT =>
            rightAllowed = true
          case _ =>

        }
        true
      }

      override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
        println(s"mouse click-> (btn: $button, x: $screenX, y: $screenY")
        true
      }
    })
  }

  override def onExit(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  var leftAllowed = true
  var rightAllowed = true

  override def run(delta: Float) = {

    if (!shared.isFalling) {
      if (rightAllowed && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        if (shared.speed != 0) {
          shared.playerX -= Const.Physics.playerXSpeed
        } else {
          shared.playerX -= Const.Physics.playerXSpeed / 5
        }
        if (shared.playerX < Const.Physics.roadRightBorderX) {
          rightAllowed = false
          Assets.Audios.border.play(Const.soundVolume)
          shared.speed /= 2
          shared.playerX += Const.Physics.playerBorderTeleportationX
        }
      }

      if (leftAllowed && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        if (shared.speed != 0) {
          shared.playerX += Const.Physics.playerXSpeed
        } else {
          shared.playerX += Const.Physics.playerXSpeed / 5
        }
        if (shared.playerX > Const.Physics.roadLeftBorderX) {
          leftAllowed = false
          Assets.Audios.border.play(Const.soundVolume)
          shared.speed /= 2
          shared.playerX -= Const.Physics.playerBorderTeleportationX
        }
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.L)) {
      Assets.Audios.bgm.setVolume(Assets.Audios.bgm.getVolume + 0.01f)
    }
  }
}
