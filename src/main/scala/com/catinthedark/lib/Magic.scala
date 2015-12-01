package com.catinthedark.lib

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{Rectangle, Vector2}

/**
 * Created by over on 13.12.14.
 */
object Magic {

  class RichSpriteBatch(val batch: SpriteBatch) {
    val debugBatch = new ShapeRenderer
    debugBatch.setProjectionMatrix(batch.getProjectionMatrix)
    
    def managed(f: SpriteBatch => Unit): Unit = {
      batch.begin()
      f(batch)
      batch.end()
    }

    /**
      * Use this method to draw some entities with real-view and physical position. 
      * So in debug you can see debug rectangle for physical coordinates. 
      * @param t - texture for real view
      * @param viewPos - container for real view position
      * @param physicalPos - container for physical position
      */
    def richDraw(t: Texture, viewPos: Rectangle, physicalPos: Rectangle): Unit = {
      batch.draw(t, viewPos.x, viewPos.y)
      //TODO: if some kind of debug??
      //debugBatch.begin(ShapeType.Line)
      //debugBatch.rect(viewPos.x, viewPos.y, viewPos.width, viewPos.height)
      //debugBatch.end()
    }

    def drawCentered(tex: Texture, x: Float, y: Float,
                     centerX: Boolean = true, centerY: Boolean = true) =
      batch.draw(tex,
        if (centerX) x - tex.getWidth / 2 else x,
        if (centerY) y - tex.getHeight / 2 else y
      )
  }

  implicit def richifySpriteBatch(batch: SpriteBatch) = new RichSpriteBatch(batch)

  implicit def vector2ToTuple2(vec: Vector2): Tuple2[Float, Float] = (vec.x, vec.y)
  implicit def tuple2ToVector2(vec: Tuple2[Float, Float]): Vector2 = new Vector2(vec._1, vec._2)
}
