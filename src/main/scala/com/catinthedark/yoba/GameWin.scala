package com.catinthedark.yoba

import java.net.{HttpURLConnection, URL}

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{Gdx, Input}
import com.catinthedark.lib.Magic._
import com.catinthedark.lib.{KeyAwaitState, Magic, Stub}
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.records.Record
import com.fasterxml.jackson.databind.ObjectMapper

import scala.collection.JavaConversions._
import scala.io.Source

/**
 * Created by over on 23.08.15.
 */
class GameWin(shared: Shared) extends Stub("GameWin") with KeyAwaitState {
  val batch = new SpriteBatch
  var records: List[Record] = List()


  override def onActivate(): Unit = {
    records = try {
      val postConn = new URL(Const.Scores.addScoreUrl).openConnection().asInstanceOf[HttpURLConnection]
      postConn.setRequestProperty(Const.Scores.authHeader, Const.Scores.authSecret)
      postConn.setRequestProperty("Content-Type", "application/json")
      postConn.setRequestMethod("POST")
      postConn.setDoOutput(true)
      val out = postConn.getOutputStream
      val data = s"""{"name": "${shared.username}", "time": ${shared.lvlTime.toInt}}"""
      println(data)
      out.write(data.getBytes("UTF-8"))
      out.close()
      println("respCode:", postConn.getResponseCode)
      postConn.disconnect()

      val getConn = new URL(Const.Scores.getScoresUrl).openConnection().asInstanceOf[HttpURLConnection]
      postConn.setRequestProperty("Accept", "application/json")
      if (getConn.getResponseCode != 200) {
        getConn.disconnect()
        List()
      } else {
        val source = Source.fromInputStream(getConn.getInputStream)
        val mapper = new ObjectMapper
        val tree = mapper.readTree(source.mkString)
        getConn.disconnect()
        source.close()

        tree.elements().map(node => {
          Record(node.get("Time").asInt(), node.get("Name").asText(), node.get("Country").asText())
        }).toList
      }
    } catch {
      case e: Exception =>
        println(e)
        List()
    }

    println(records)
    super.onActivate()
  }
  override def run(delay: Float): Option[Unit] = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)

    batch.managed(self => {
      Assets.Fonts.highScoreFont.draw(self, "HIGH SCORES", 300, 650)
      Assets.Fonts.highScoreFont.draw(self, "_" * 32, 280, 630)

      records.zipWithIndex.foreach {
        case (value, index) =>
          val name = value.name
          Assets.Fonts.highScoreFont.draw(self, f"$name%15s ${value.time}%5s ${value.country}%5s ", 250, 600 - 30 * index)
      }
    })
    super.run(delay)
  }
  override val keycode: Int = Input.Keys.ENTER
}
