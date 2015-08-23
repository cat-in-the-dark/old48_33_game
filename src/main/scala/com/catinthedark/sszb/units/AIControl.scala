package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.entity.Creatures.Creature
import com.catinthedark.sszb.lib.SimpleUnit

class AIControl(shared: Shared) extends SimpleUnit {
 override def run(delta: Float) = {
   shared.creatures.foreach((i: Creature) => {
     val speed = i.speed - shared.speed
     i.z += speed * delta
   })
 }
}
