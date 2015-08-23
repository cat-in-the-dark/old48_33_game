package com.catinthedark.sszb.units

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.{Input, Gdx}
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.attributes.{DepthTestAttribute, BlendingAttribute, TextureAttribute, ColorAttribute}
import com.badlogic.gdx.graphics.g3d.model.Node
import com.badlogic.gdx.graphics.g3d.utils.{MeshPartBuilder, ModelBuilder}
import com.badlogic.gdx.graphics.g3d.{Model, Material, ModelBatch, ModelInstance}
import com.badlogic.gdx.graphics.{GL20, _}
import com.badlogic.gdx.math.Vector3
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.lib._

/**
 * Created by over on 02.01.15.
 */
abstract class RoadView(val shared: Shared) extends SimpleUnit with Deferred {
  var stateTime = 0f
  
  val roadLayer = new Layer {
    var diff = 0f
    val modelBuilder = new ModelBuilder()
    val modelBatch = new ModelBatch

    val aspectRatio = 1152.toFloat / 768.toFloat

    val cam = new PerspectiveCamera(160, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
    cam.position.set(0f, 1f, 0f)
    cam.lookAt(0, 0, 6f)
    cam.near = 0f
    cam.far = 100f
    cam.update()
    val material = new Material(TextureAttribute.createDiffuse(Assets.Textures.road))
    val manMaterial = new Material(TextureAttribute.createDiffuse(Assets.Textures.road))
    var animationDelta = 0.0f


    val dz = 0.2f

    override def render(delta: Float): Unit = {
      if (Gdx.input.isKeyPressed(Input.Keys.UP))
        shared.playerZ += 0.01f

      if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        shared.playerZ -= 0.01f

      diff += delta * shared.speed * Const.Physics.RoadSpeedScale

      modelBuilder.begin()
      //      val node = modelBuilder.node();
      //      node.translation.set(0, 0, 0);

      //      if (diff > 0.5f)
      //        diff = 0

      val meshBuilder = modelBuilder.part("road", GL20.GL_TRIANGLES,
        Usage.Position | Usage.Normal | Usage.TextureCoordinates,
        material)
      meshBuilder.setUVRange(0, 0.5f + diff, 1f, 0f + diff)
      meshBuilder.rect(
        new Vector3(3f, 0f, 0f),
        new Vector3(-3f, 0f, 0f),
        new Vector3(-3f, 0f, 1.7f),
        new Vector3(3f, 0f, 1.7f),
        new Vector3(0f, 1f, 0f))

      var manTexture: TextureRegion = null
      if (shared.isFalling) {
        animationDelta += delta
        manTexture = Assets.Animations.manFalling.getKeyFrame(animationDelta)
      } else {
        animationDelta = 0
        manTexture = if (shared.speed == 0)
          Assets.Textures.manFrames(0)(2)
        else if (shared.cursorPosition > 0.5f)
          Assets.Textures.manFrames(0)(0)
        else
          Assets.Textures.manFrames(0)(1)
      }

      val tetkaTexture = Assets.Animations.tetka.getKeyFrame(stateTime)
      val tetkaBuilder = modelBuilder.part("tetka", GL20.GL_TRIANGLES,
        Usage.Position | Usage.Normal | Usage.TextureCoordinates,
        new Material(
          TextureAttribute.createDiffuse(tetkaTexture),
          new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
          new DepthTestAttribute(GL20.GL_ALWAYS)))

      tetkaBuilder.setUVRange(0, 0, 1f, 1f)
      shared.creatures.sorted.foreach(c => {
        tetkaBuilder.rect(
          new Vector3(c.x + c.width, 0f, c.z),
          new Vector3(c.x, 0f, c.z),
          new Vector3(c.x, 1f, c.z + dz),
          new Vector3(c.x + c.width, 1f, c.z + dz),
          new Vector3(0f, 0f, 1f))
      })

      val manBuilder = modelBuilder.part("man", GL20.GL_TRIANGLES,
        Usage.Position | Usage.Normal | Usage.TextureCoordinates,
        new Material(TextureAttribute.createDiffuse(manTexture)
          , new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
          , new DepthTestAttribute(GL20.GL_ALWAYS)
        )
      )

      manBuilder.setUVRange(0, 0, 1f, 1f)
      manBuilder.rect(
        new Vector3(shared.playerX + 0.5f, 0f, shared.playerZ),
        new Vector3(shared.playerX, 0f, shared.playerZ),
        new Vector3(shared.playerX, 1f, shared.playerZ + dz),
        new Vector3(shared.playerX + 0.5f, 1f, shared.playerZ + dz),
        new Vector3(0f, 0f, 1f))
      
      val model = modelBuilder.end()

      modelBatch.begin(cam)
      modelBatch.render(new ModelInstance(model))
      modelBatch.end()
    }
  }

  override def onActivate(): Unit = {
    stateTime = 0f
  }

  override def onExit(): Unit = {
    stateTime = 0f
  }

  override def run(delta: Float) = {
    stateTime += delta
    roadLayer.render(delta)
  }
}
