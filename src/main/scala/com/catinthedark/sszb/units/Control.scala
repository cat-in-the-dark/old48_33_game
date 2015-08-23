package com.catinthedark.sszb.units


import com.badlogic.gdx.{Gdx, Input, InputAdapter}
import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.Pedals
import com.catinthedark.sszb.lib._
import com.catinthedark.sszb.common.Const

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

      override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
        println(s"mouse click-> (btn: $button, x: $screenX, y: $screenY")
        true
      }
    })
  }

  override def onExit(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def run(delta: Float) = {
    if (Gdx.input.isKeyPressed(Input.Keys.L))
      println("L pressed")

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
      shared.playerX -= Const.Physics.playerXSpeed

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
      shared.playerX += Const.Physics.playerXSpeed
  }
}
