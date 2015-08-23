package com.catinthedark.sszb.entity

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const

object Creatures {
  def create(shared: Shared, x: Float, z: Float): Creature = {
    new Mammy(x, z, Const.Difficulty.mammySpeed(shared.lvl), Const.Physics.mammyWidth, Const.Physics.mammyDepth)
  }

  def createSign(shared: Shared, x: Float, z: Float): Creature = {
    new Sign(x, z, 0f, Const.Physics.signWidth, Const.Physics.signDepth)
  }

  def createLamp(shared: Shared, x: Float, z: Float): Creature = {
    new Lamp(x, z, 0f, Const.Physics.lampWidth, Const.Physics.lampDepth)
  }

  def createTree(shared: Shared, x: Float, z: Float): Creature = {
    new Tree(x, z, 0f, Const.Physics.treeWidth, Const.Physics.treeDepth)
  }

  def createBush(shared: Shared, x: Float, z: Float): Creature = {
    new Bush(x, z, 0f, Const.Physics.bushWidth, Const.Physics.bushDepth)
  }

  sealed trait Creature extends Ordered[Creature] {
    var x: Float
    var z: Float
    var speed: Float
    var width: Float
    var depth: Float
    var deathAnimationStateTime: Float = 0f
    var fallSpeed: Float = 2f
    override def compare(that: Creature): Int = that.z.compareTo(z)
    var isDying: Boolean = false
  }

  case class Man(var x: Float,
                 var z: Float,
                 var speed: Float,
                 var width: Float = Const.Physics.playerWidth,
                 var depth: Float = Const.Physics.playerDepth)
    extends Creature

  case class Mammy(var x: Float,
                   var z: Float,
                   var speed: Float,
                   var width: Float,
                   var depth: Float)
    extends Creature

  case class Sign(var x: Float,
                  var z: Float,
                  var speed: Float,
                  var width: Float,
                  var depth: Float)
    extends Creature

  case class Lamp(var x: Float,
                  var z: Float,
                  var speed: Float,
                  var width: Float,
                  var depth: Float)
    extends Creature

  case class Tree(var x: Float,
                  var z: Float,
                  var speed: Float,
                  var width: Float,
                  var depth: Float)
    extends Creature

  case class Bush(var x: Float,
                  var z: Float,
                  var speed: Float,
                  var width: Float,
                  var depth: Float)
    extends Creature

}
