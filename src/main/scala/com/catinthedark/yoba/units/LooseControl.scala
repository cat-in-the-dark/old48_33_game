package com.catinthedark.yoba.units

import com.catinthedark.yoba.Shared
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.lib.SimpleUnit

/**
 * Used to remove from shared.creatures list object that leave view port
 * @param shared
 */
class LooseControl(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    val missed = shared.creatures.filter { c => 
      c.z < Const.Physics.minZViewPort || c.z > Const.Physics.maxZViewPort
    }
    
    val missedTrash = shared.trash.filter { t => 
      t.z < Const.Physics.minZViewPort || t.z > Const.Physics.maxZViewPort
    }
    
    shared.creatures --= missed
    shared.trash --= missedTrash
  }
}
