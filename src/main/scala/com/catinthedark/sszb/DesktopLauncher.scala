package com.catinthedark.sszb

import com.catinthedark.sszb.records.Record

import scala.collection.JavaConversions._

import java.net.{HttpURLConnection, URL}

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.fasterxml.jackson.databind.ObjectMapper

import scala.io.Source

/**
 * Created by over on 13.12.14.
 */
object DesktopLauncher {
  def main(args: Array[String]) {
    val conf = new LwjglApplicationConfiguration
    conf.title = "YoBA"
    conf.height = 768
    conf.width = 1152

    new LwjglApplication(new SaveMeDadUltra, conf)
  }
}
