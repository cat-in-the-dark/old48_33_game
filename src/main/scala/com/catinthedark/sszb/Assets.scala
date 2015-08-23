package com.catinthedark.sszb

import java.util

import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.graphics.Texture.{TextureFilter, TextureWrap}
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.{DepthTestAttribute, BlendingAttribute, TextureAttribute}
import com.badlogic.gdx.{Gdx, utils}
import com.catinthedark.sszb.common.Const

/**
 * Created by over on 13.12.14.
 */
object Assets {

  object MI extends Enumeration {
    val man1, man2, man3,
    mf1, mf2, mf3,
    tetka1, tetka2,
    tf1, tf2, tf3,
    baby,
    tree, bush, lampLight, sign = Value
  }

  object Textures {
    val logo = new Texture(Gdx.files.internal("textures/logo.png"))

    val menu = new Texture(Gdx.files.internal("textures/menu.png"))
    val t1 = new Texture(Gdx.files.internal("textures/t1.png"))
    val t2 = new Texture(Gdx.files.internal("textures/t2.png"))
    val t3 = new Texture(Gdx.files.internal("textures/t3.png"))
    val level1 = new Texture(Gdx.files.internal("textures/level1.png"))
    val level2 = new Texture(Gdx.files.internal("textures/level2.png"))
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
    val baby = new Texture(Gdx.files.internal("textures/baby.png"))
    road.setWrap(TextureWrap.Repeat, TextureWrap.Repeat)
    road.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val manTexture = new Texture(Gdx.files.internal("textures/man.png"))
    manTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val tetka = new Texture(Gdx.files.internal("textures/mom.png"))
    tetka.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val manFrames = TextureRegion.split(
      manTexture, 120, 200)
    val tetkaFrames = TextureRegion.split(tetka, 120, 200)

    val manFallTexture = new Texture(Gdx.files.internal("textures/man_fall.png"))
    manFallTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
    val manFallFrames = TextureRegion.split(
      manFallTexture, 200, 200)

    val tetkaFallTexture = new Texture(Gdx.files.internal("textures/mom_fall.png"))
    tetkaFallTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val tetkaFallFrames = TextureRegion.split(
      tetkaFallTexture, 200, 200)

    val tree = new Texture(Gdx.files.internal("textures/tree.png"))
    tree.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val bush = new Texture(Gdx.files.internal("textures/bush.png"))
    bush.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val lampRight = new Texture(Gdx.files.internal("textures/lamp_post.png"))
    lampRight.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val sign = new Texture(Gdx.files.internal("textures/sign.png"))
    sign.setFilter(TextureFilter.Linear, TextureFilter.Linear)


    def materialTex(tex: Texture) = new Material(
      TextureAttribute.createDiffuse(tex),
      new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
      new DepthTestAttribute(GL20.GL_ALWAYS));

    def material(tex: TextureRegion) = new Material(
      TextureAttribute.createDiffuse(tex),
      new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
      new DepthTestAttribute(GL20.GL_ALWAYS));

    val materials = Map(
      MI.man1 -> material(manFrames(0)(0)),
      MI.man2 -> material(manFrames(0)(1)),
      MI.man3 -> material(manFrames(0)(2)),

      MI.mf1 -> material(manFallFrames(0)(0)),
      MI.mf2 -> material(manFallFrames(0)(1)),
      MI.mf3 -> material(manFallFrames(0)(2)),

      MI.tetka1 -> material(tetkaFrames(0)(0)),
      MI.tetka2 -> material(tetkaFrames(0)(1)),

      MI.tf1 -> material(tetkaFallFrames(0)(0)),
      MI.tf2 -> material(tetkaFallFrames(0)(1)),
      MI.tf3 -> material(tetkaFallFrames(0)(2)),

      MI.baby -> materialTex(baby),

      MI.bush -> materialTex(bush),
      MI.lampLight -> materialTex(lampRight),
      MI.sign -> materialTex(sign)
    )

  }

  object Animations {
    private def loopingAnimation(frames: Array[Array[TextureRegion]], delay: Float, frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(delay, array, Animation.PlayMode.LOOP)
    }

    private def normalAnimation(frames: Array[Array[TextureRegion]], delay: Float, frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(delay, array, Animation.PlayMode.NORMAL)
    }

    class MyAnimation(delay: Float, loop: Boolean, materials: MI.Value*) {
      def getKeyFrame(time: Float): MI.Value = {
        if (materials.length == 1) return materials(0)

        var frameNumber: Int = (time / delay).toInt
        frameNumber =
          if (loop) frameNumber % materials.length
          else
          if (frameNumber < materials.length - 1) frameNumber else materials.length - 1

        materials(frameNumber)
      }

    }

    val man = new MyAnimation(0.01f, true, MI.man1, MI.man2)
    val tetka = new MyAnimation(0.3f, true, MI.tetka1, MI.tetka2)
    val manFalling = new MyAnimation(0.1f, false, MI.mf1, MI.mf2, MI.mf3)
    val tetkaFalling = new MyAnimation(0.1f, false, MI.tf1, MI.tf2, MI.tf3)
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
    otherFontParam.size = 44

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



    val border = Gdx.audio.newSound(Gdx.files.internal("sfx/border.wav"))

    val bgm = Gdx.audio.newMusic(Gdx.files.internal("sfx/bgm.mp3"))
    bgm.setVolume(Const.musicVolume)
    bgm.setLooping(true)

  }

}
