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

  sealed trait Creature extends Ordered[Creature] {
    var x: Float
    var z: Float
    var speed: Float
    var width: Float
    var depth: Float
    
    override def compare(that: Creature): Int = that.z.compareTo(z)
  }

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

}
