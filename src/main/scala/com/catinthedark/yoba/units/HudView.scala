package com.catinthedark.yoba.units

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.catinthedark.lib.Magic._
import com.catinthedark.lib.{Layer, Magic, SimpleUnit}
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.common.Const.{Physics, UI}
import com.catinthedark.yoba.{Assets, Shared}

class HudView(shared: Shared) extends SimpleUnit {
  val sliderWidth: Int = 200
  val rotationOnMinSpeed = 225f
  val rotationOnMaxSpeed = -45f
  val timerHudLayer = new Layer {
    val timerHudBatch = new SpriteBatch
    timerHudBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
    
    override def render(delta: Float): Unit = {
      timerHudBatch.managed { self =>
        Assets.Fonts.timerFrontFont.draw(self, s"time:${shared.lvlTime.toLong}", UI.timerPos.x, UI.timerPos.y)
        Assets.Fonts.redFont.draw(self, s"dist:${shared.lvlDistance.toLong}/${Const.Distance.levelDistance.toLong}", UI.distPos.x, UI.distPos.y)
        self.draw(Assets.Textures.slider, UI.sliderPos.x, UI.sliderPos.y)
        self.draw(Assets.Textures.pointer, UI.pointerPos.x + shared.cursorPosition * sliderWidth, UI.pointerPos.y)
        if (shared.cursorPosition > 0.5) {
          self.draw(Assets.Textures.XLetter, UI.XLetterPos.x, UI.XLetterPos.y)
        } else {
          self.draw(Assets.Textures.ZLetter, UI.ZLetterPos.x, UI.ZLetterPos.y)
        }
        if (shared.palkaPos > -1) {
          self.draw(Assets.Textures.palka, UI.palkaPos.x + shared.palkaPos * sliderWidth, UI.palkaPos.y)
        }
        self.draw(Assets.Textures.speedometer, UI.speedometerPos.x, UI.speedometerPos.y)
        val rotation = (1 - shared.speed / Physics.maxSpeed) * (rotationOnMinSpeed - rotationOnMaxSpeed) + rotationOnMaxSpeed
        self.draw(Assets.Textures.speedPalka,
          UI.speedPalkaPos.x, UI.speedPalkaPos.y,
          0, Assets.Textures.speedPalka.getHeight / 2,
          Assets.Textures.speedPalka.getWidth, Assets.Textures.speedPalka.getHeight,
          1, 1,
          rotation,
          0, 0,
          Assets.Textures.speedPalka.getWidth, Assets.Textures.speedPalka.getHeight,
          false, false)
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
