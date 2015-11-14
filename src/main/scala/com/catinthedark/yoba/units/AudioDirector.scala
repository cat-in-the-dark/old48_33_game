package com.catinthedark.yoba.units

import com.catinthedark.lib.SimpleUnit
import com.catinthedark.yoba.common.Const
import com.catinthedark.yoba.{Assets, Shared}

/**
 * Created by over on 23.08.15.
 */
class AudioDirector(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    Assets.Audios.bgm.setVolume(Const.musicVolume * shared.speed / Const.Physics.maxSpeed)
  }
}
