package com.catinthedark.yoba.units

import com.catinthedark.lib.SimpleUnit
import com.catinthedark.yoba.Shared
import com.catinthedark.yoba.entity.Creatures.Creature

class AIControl(shared: Shared) extends SimpleUnit {
 override def run(delta: Float) = {
   shared.creatures.foreach((i: Creature) => {
     val speed = i.speed - shared.speed
     i.z += speed * delta
   })
 }
}
