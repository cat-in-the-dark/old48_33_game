package com.catinthedark.sszb

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.{TextureFilter, TextureWrap}
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.{Gdx, utils}
import com.catinthedark.sszb.common.Const

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
    val XLetter = new Texture(Gdx.files.internal("textures/X.png"))
    val ZLetter = new Texture(Gdx.files.internal("textures/Z.png"))
    val palka = new Texture(Gdx.files.internal("textures/palka.png"))
    val speedometer = new Texture(Gdx.files.internal("textures/speedometer.png"))
    val speedPalka = new Texture(Gdx.files.internal("textures/speed_palka.png"))
    val bg = new Texture(Gdx.files.internal("textures/bg.png"))
    road.setWrap(TextureWrap.Repeat, TextureWrap.Repeat)
    road.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val manTexture = new Texture(Gdx.files.internal("textures/man.png"))
    manTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
    
    val tetka = new Texture(Gdx.files.internal("textures/mom.png"))
    tetka.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val manFrames = TextureRegion.split(
      manTexture, 120, 200)
    val tetkaFrames = TextureRegion.split(tetka, 120, 200)
  }

  object Animations {
    private def loopingAnimation(frames: Array[Array[TextureRegion]], delay: Float, frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(delay, array, Animation.PlayMode.LOOP)
    }

    val man = loopingAnimation(Textures.manFrames, 0.01f, (0, 0), (0, 1))
    val tetka = loopingAnimation(Textures.tetkaFrames, 0.3f, (0,0), (0, 1))
  }

  object Fonts {
    val mainGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"))

    val timerFontParam = new FreeTypeFontParameter()
    timerFontParam.size = 44
    val timerFrontFont = mainGenerator.generateFont(timerFontParam)
    timerFrontFont.setColor(167f / 255, 128f / 255, 183f / 255, 1)

    timerFontParam.size = 30
    val highScoreFont = mainGenerator.generateFont(timerFontParam)
    highScoreFont.setColor(167f / 255, 128f / 255, 183f / 255, 1)

    val otherFontParam = new FreeTypeFontParameter()
    otherFontParam.size = 30

    val greenFont = mainGenerator.generateFont(otherFontParam)
    greenFont.setColor(54f / 255, 131f / 255, 87f / 255, 1)
    val redFont = mainGenerator.generateFont(otherFontParam)
    redFont.setColor(255f / 255, 0f / 255, 0f / 255, 1)
  }


  object Audios {
    val roundEnd = Gdx.audio.newSound(Gdx.files.internal("sfx/hurt.wav"))
    val fall = Gdx.audio.newSound(Gdx.files.internal("sfx/fall.wav"))
    val pedal1 = Gdx.audio.newSound(Gdx.files.internal("sfx/pedal1.wav"))
    val pedal2 = Gdx.audio.newSound(Gdx.files.internal("sfx/pedal2.wav"))
    val bgm = Gdx.audio.newMusic(Gdx.files.internal("sfx/bgm.mp3"))
    bgm.setVolume(Const.musicVolume)
    bgm.setLooping(true)
  }

}
