package com.catinthedark.yoba.units

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.catinthedark.yoba.{Assets, Shared}
import com.catinthedark.yoba.common.Const.UI
import com.catinthedark.yoba.lib.Magic.richifySpriteBatch
import com.catinthedark.yoba.lib.{Layer, SimpleUnit}

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