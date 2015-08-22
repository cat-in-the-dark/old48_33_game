package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.lib.SimpleUnit

/**
 * Used to remove from shared.creatures list object that leave view port
 * @param shared
 */
class LooseControl(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    val missed = shared.creatures.filter { c => 
      c.z < Const.Physics.minZViewPort || c.z > Const.Physics.maxZViewPort
    }
    
    if (missed.nonEmpty) println("Loose control")
    
    shared.creatures --= missed
  }
}
