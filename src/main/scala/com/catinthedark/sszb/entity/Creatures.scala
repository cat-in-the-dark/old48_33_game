package com.catinthedark.sszb.entity

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const

object Creatures {
  def create(shared: Shared, roadNumber: Int, z: Float): Creature = {
    new Mammy(roadNumber, z, Const.Difficulty.mammySpeed(shared.lvl))
  }
  
  sealed trait Creature {
    var roadNumber: Int
    var z: Float
    var speed: Float
  }

  case class Mammy(var roadNumber: Int, 
                   var z: Float, 
                   var speed: Float) 
    extends Creature
}
