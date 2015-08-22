package com.catinthedark.sszb

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.{TextureFilter, TextureWrap}
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.{Gdx, utils}

/**
 * Created by over on 13.12.14.
 */
object Assets {

  object Textures {
    val logo = new Texture(Gdx.files.internal("textures/logo.png"))

    val t1 = new Texture(Gdx.files.internal("textures/menu.png"))
    val t2 = new Texture(Gdx.files.internal("textures/t1.png"))
    val t3 = new Texture(Gdx.files.internal("textures/t2.png"))
    val t4 = new Texture(Gdx.files.internal("textures/t3.png"))
    val gameOver = new Texture(Gdx.files.internal("textures/gameover.png"))
    val gameWin = new Texture(Gdx.files.internal("textures/gamewin.png"))
    val road = new Texture(Gdx.files.internal("textures/road.png"))
    val slider = new Texture(Gdx.files.internal("textures/slider.png"))
    val pointer = new Texture(Gdx.files.internal("textures/pointer.png"))
    road.setWrap(TextureWrap.Repeat, TextureWrap.Repeat)
    road.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val manTexture = new Texture(Gdx.files.internal("textures/man.png"))
    manTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val manFrames = TextureRegion.split(
      manTexture, 120, 200)
  }

  object Animations {
    private def loopingAnimation(frames: Array[Array[TextureRegion]], delay: Float, frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(delay, array, Animation.PlayMode.LOOP)
    }

    val man = loopingAnimation(Textures.manFrames, 0.01f, (0, 0), (0, 1))
  }

  object Fonts {
    val mainGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"))

    val timerFontParam = new FreeTypeFontParameter()
    timerFontParam.size = 44
    val timerFrontFont = mainGenerator.generateFont(timerFontParam)
    timerFrontFont.setColor(167f / 255, 128f / 255, 183f / 255, 1)

    val otherFontParam = new FreeTypeFontParameter()
    otherFontParam.size = 30

    val greenFont = mainGenerator.generateFont(otherFontParam)
    greenFont.setColor(54f / 255, 131f / 255, 87f / 255, 1)
    val redFont = mainGenerator.generateFont(otherFontParam)
    redFont.setColor(255f / 255, 0f / 255, 0f / 255, 1)
  }


  object Audios {
    val roundEnd = Gdx.audio.newSound(Gdx.files.internal("sfx/hurt.wav"))
  }

}
