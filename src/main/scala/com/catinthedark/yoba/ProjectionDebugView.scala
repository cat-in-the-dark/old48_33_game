package com.catinthedark.yoba

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{Matrix4, Rectangle}
import com.catinthedark.lib.SimpleUnit
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.common.Const.UI

import scala.collection.mutable

class ProjectionDebugView(shared: Shared) extends SimpleUnit {
  val widthImagine = 2L
  val width = 300L
  val multiply = width / widthImagine
  
  
  val debugBatch = new ShapeRenderer
  debugBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
  
  override def run(delta: Float): Unit = {
    val rectangles = mutable.ListBuffer[Rectangle]()
    val player = new Rectangle(shared.playerX*multiply + width / 2L, shared.playerZ*multiply, Const.Physics.playerWidth*multiply, Const.Physics.playerDepth*multiply)
    debugBatch.begin(ShapeType.Filled)
    shared.creatures.foreach(c => {
      rectangles += new Rectangle(c.x * multiply + width/2l, c.z*multiply, c.width*multiply, c.depth*multiply)
    })
    rectangles += player
    rectangles.foreach(r => debugBatch.rect(r.x, r.y, r.width, r.height))
    debugBatch.end()
    
    val roadWidth = Const.Physics.roadRowWidth
    val roadDepth = Const.Physics.maxZViewPort 
    val roads = List(
      new Rectangle(0, 0, roadWidth * multiply, roadDepth* multiply),
      new Rectangle(roadWidth * multiply, 0, roadWidth * multiply, roadDepth* multiply),
      new Rectangle(roadWidth*2 * multiply, 0, roadWidth * multiply, roadDepth* multiply)
    )
    debugBatch.begin(ShapeType.Line)
    roads.foreach(road => debugBatch.rect(road.x, road.y, road.width, road.height))
    debugBatch.end()
  }
}
