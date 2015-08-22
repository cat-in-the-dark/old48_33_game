package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.entity.Creatures.Creature
import com.catinthedark.sszb.lib.SimpleUnit

class AIControl(shared: Shared) extends SimpleUnit {
 override def run(delta: Float) = {
   shared.creatures.foreach((i: Creature) => {
     i.z += i.speed * delta - shared.speed
   })
 }
}
