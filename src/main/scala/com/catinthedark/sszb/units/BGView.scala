package com.catinthedark.sszb.units

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.lib.Magic.richifySpriteBatch
import com.catinthedark.sszb.lib.{Layer, SimpleUnit}

/**
 * Created by kirill on 23.08.15.
 */
class BGView (val shared: Shared) extends SimpleUnit {
  
  val bgLayer = new Layer {
    val bgBatch = new SpriteBatch
    bgBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))

    override def render(delta: Float): Unit = {
      bgBatch.managed { self =>
        self.draw(Assets.Textures.bg, 0, 0)
      }
    }
  }
  
  override def run(delta: Float): Unit = {
    bgLayer.render(delta)
  }
}