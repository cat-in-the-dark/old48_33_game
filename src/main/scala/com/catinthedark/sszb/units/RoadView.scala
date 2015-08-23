package com.catinthedark.sszb.units

import java.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.{Input, Gdx}
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.attributes.{DepthTestAttribute, BlendingAttribute, TextureAttribute, ColorAttribute}
import com.badlogic.gdx.graphics.g3d.model.Node
import com.badlogic.gdx.graphics.g3d.utils.{MeshPartBuilder, ModelBuilder}
import com.badlogic.gdx.graphics.g3d.{Model, Material, ModelBatch, ModelInstance}
import com.badlogic.gdx.graphics.{GL20, _}
import com.badlogic.gdx.math.Vector3
import com.catinthedark.sszb.Assets.MI
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.entity.Creatures
import com.catinthedark.sszb.entity.Creatures._
import com.catinthedark.sszb.entity.Creatures.{Bush, Tree, Lamp, Sign}
import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.lib._

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
 * Created by over on 02.01.15.
 */
abstract class RoadView(val shared: Shared) extends SimpleUnit with Deferred {
  var stateTime = 0f

  val roadLayer = new Layer {
    val rand = new Random

    var roadTexOffset = 0f
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
    val dzTrash = 0.15f
    val dwidth = 2.5f

    override def render(delta: Float): Unit = {
      //DRAW ROAD
      roadTexOffset += delta * shared.speed * Const.Physics.roadSpeedScale
      modelBuilder.begin()

      val meshBuilder = modelBuilder.part("road", GL20.GL_TRIANGLES,
        Usage.Position | Usage.Normal | Usage.TextureCoordinates,
        material)
      meshBuilder.setUVRange(0, 0.5f + roadTexOffset, 1f, 0f + roadTexOffset)
      meshBuilder.rect(
        new Vector3(3f, 0f, 0f),
        new Vector3(-3f, 0f, 0f),
        new Vector3(-3f, 0f, 1.7f),
        new Vector3(3f, 0f, 1.7f),
        new Vector3(0f, 1f, 0f))

      //DRAW ALL OBJECTS
      //1 - создадим общий список объектов для рендера
      val entities = shared.creatures ++ shared.trash ++
        List(Creatures.Man(shared.playerX, shared.playerZ, 0))

      //сопоставим каждой твари материал
      val entityWithMaterial = entities.map(e => {
        val material = e match {
          case m: Man =>
            if (shared.isFalling) {
              animationDelta += delta
              Assets.Animations.manFalling.getKeyFrame(animationDelta)
            } else {
              animationDelta = 0
              if (shared.speed == 0)
                MI.man3
              else if (shared.cursorPosition > 0.5f)
                MI.man1
              else
                MI.man2
            }
          case w: Mammy =>
            if (w.isDying) {
              Assets.Animations.tetkaFalling.getKeyFrame(w.deathAnimationStateTime)
            } else
              Assets.Animations.tetka.getKeyFrame(stateTime)
          case t: Tree =>
            MI.bush
          case b: Bush =>
            MI.bush
          case l: Lamp =>
            MI.lampLight
          case s: Sign =>
            MI.sign
        }

        (material, e)
      })
      //отсортировать по z-index
      val sorted = entityWithMaterial.sortBy(me => {
        val (_, e) = me
        e.z
      }).reverse

      //Рисовать!
      sorted.foreach(me => {
        val (materialId, entity) = me
        val builder = modelBuilder.part(s"part${rand.nextInt()}", GL20.GL_TRIANGLES,
          Usage.Position | Usage.Normal | Usage.TextureCoordinates,
          Assets.Textures.materials(materialId))
        builder.setUVRange(0, 0, 1f, 1f)

        entity match {
          case c: Mammy =>
            builder.rect(
              new Vector3(c.x + c.width * dwidth, 0f, c.z),
              new Vector3(c.x, 0f, c.z),
              new Vector3(c.x, 1f, c.z + dz),
              new Vector3(c.x + c.width * dwidth, 1f, c.z + dz),
              new Vector3(0f, 0f, 1f))

            if(c.isDying) {

              val babyBuilder = modelBuilder.part("baby", GL20.GL_TRIANGLES,
                Usage.Position | Usage.Normal | Usage.TextureCoordinates,
                new Material(
                  TextureAttribute.createDiffuse(Assets.Textures.baby),
                  new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
                  new DepthTestAttribute(GL20.GL_ALWAYS)))
              babyBuilder.setUVRange(0, 0, 1f, 1f)

              babyBuilder.rect(
                new Vector3(c.x + c.width * dwidth, c.deathAnimationStateTime * 3, c.z),
                new Vector3(c.x, c.deathAnimationStateTime * 3, c.z),
                new Vector3(c.x, 1f + c.deathAnimationStateTime * 3, c.z + dz),
                new Vector3(c.x + c.width * dwidth, 1f + c.deathAnimationStateTime * 3, c.z + dz),
                new Vector3(0f, 0f, 1f))

              c.deathAnimationStateTime += delta
              c.speed = 0
              c.x += delta * c.fallSpeed
              c.fallSpeed -= delta * 3
              if (c.fallSpeed <= 0) {
                c.fallSpeed = 0
              }
            }
          case m: Man =>
            builder.rect(
              new Vector3(m.x + 0.7f, 0f, m.z),
              new Vector3(m.x, 0f, m.z),
              new Vector3(m.x, 1f, m.z + dz),
              new Vector3(m.x + 0.7f, 1f, m.z + dz),
              new Vector3(0f, 0f, 1f))
          case _@t =>
            builder.rect(
              new Vector3(t.x + t.width, 0f, t.z),
              new Vector3(t.x, 0f, t.z),
              new Vector3(t.x, 1f, t.z + dzTrash),
              new Vector3(t.x + t.width, 1f, t.z + dzTrash),
              new Vector3(0f, 0f, 1f))
        }

      });

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
