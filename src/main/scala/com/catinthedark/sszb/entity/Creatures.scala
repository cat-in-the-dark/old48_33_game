package com.catinthedark.sszb.entity

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const

object Creatures {
  def create(shared: Shared, x: Float, z: Float): Creature = {
    new Mammy(x, z, Const.Difficulty.mammySpeed(shared.lvl), Const.Physics.mammyWidth, Const.Physics.mammyDepth)
  }
  
  sealed trait Creature {
    var x: Float
    var z: Float
    var speed: Float
    var width: Float // X
    var depth: Float // Z
  }

  case class Mammy(var x: Float, 
                   var z: Float, 
                   var speed: Float,
                   var width: Float,
                   var depth: Float) 
    extends Creature
}
