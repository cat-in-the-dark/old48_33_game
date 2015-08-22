package com.catinthedark.sszb.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.lib.Magic._
import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.lib.{Layer, Deferred, SimpleUnit}

class HudView(shared: Shared) extends SimpleUnit {
  val timerHudLayer = new Layer {
    val timerHudBatch = new SpriteBatch
    timerHudBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
    
    override def render(delta: Float): Unit = {
      timerHudBatch.managed { self =>
        Assets.Fonts.timerFrontFont.draw(self, s"time:${shared.lvlTime.toLong}", UI.timerPos.x, UI.timerPos.y)
      }
    }
    
    def dispose() = {
      timerHudBatch.dispose()
    }
  }
  
  override def run(delta: Float) = {
    timerHudLayer.render(delta)
  }
}
