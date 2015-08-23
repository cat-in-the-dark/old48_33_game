package com.catinthedark.sszb

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.{Game, Gdx, Input}
import com.catinthedark.sszb.lib._
import com.catinthedark.sszb.lib.records.RecordStorage
import com.catinthedark.sszb.records.{RawStringStore, Record}

import scala.collection.mutable
import scala.util.Random

/**
 * Created by over on 13.12.14.
 */
class SaveMeDadUltra extends Game {
  val rm = new RouteMachine()

  def keyAwait(name: String, tex: Texture, key: Int = Input.Keys.ENTER) =
    new Stub(name) with TextureState with KeyAwaitState {
      val texture: Texture = tex
      val keycode: Int = key
    }

  def delayed(name: String, tex: Texture, _delay: Float) =
    new Stub(name) with TextureState with DelayState {
      val texture: Texture = tex
      val delay: Float = _delay
    }

  val rand = new Random()

  override def create() = {
    val recordStorage = new RecordStorage[Record]("records.txt", 5) with RawStringStore

    println("records ->", recordStorage.all)

    val logo = delayed("Logo", Assets.Textures.logo, 1.0f)
    val t1 = keyAwait("Tutorial1", Assets.Textures.t1)
    val t2 = keyAwait("Tutorial2", Assets.Textures.t2)
    val t3 = keyAwait("Tutorial3", Assets.Textures.t3)
    val t4 = keyAwait("Tutorial4", Assets.Textures.t4)
    val t5 = keyAwait("Tutorial4", Assets.Textures.t2)

    val shared: Shared = new Shared(recordStorage, 0f, 0f, 1, 0f, 0f, mutable.ListBuffer(), trash = mutable.ListBuffer())

    val dayOne = new DayState(shared)
    val dayTwo = new DayState(shared)
    val newRecord = new NewRecordState(shared)
    val gameWin = new GameWin(shared)

    rm.addRoute(logo, anyway => dayOne)
    rm.addRoute(t1, anyway => t2)
    rm.addRoute(t2, anyway => t3)
    rm.addRoute(t3, anyway => t4)
    rm.addRoute(t4, anyway => dayOne)
    rm.addRoute(dayOne, res => {
      res match {
        case true =>
          shared.lvl = 2
          t5
        case false =>
          shared.reset()
          logo
      }
    })
    rm.addRoute(t5, anyway => dayTwo)

    rm.addRoute(dayTwo, res => {
      res match {
        case true =>
          newRecord
        case false =>
          shared.reset()
          logo
      }
    })
    rm.addRoute(newRecord, anyway => gameWin)
    rm.addRoute(gameWin, anyway => {
      shared.reset()
      t1
    })

    rm.start(logo)
  }

  override def render() = {
    rm.run(Gdx.graphics.getDeltaTime)
    //println(Const.Ints.i)
    //import com.catinthedark.savemedad.common.Const.Strings.str
    //println(str)
  }
}
