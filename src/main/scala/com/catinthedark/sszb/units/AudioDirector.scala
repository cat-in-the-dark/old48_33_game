package com.catinthedark.sszb.units

import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.lib.SimpleUnit

/**
 * Created by over on 23.08.15.
 */
class AudioDirector(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    Assets.Audios.bgm.setVolume(Const.musicVolume * shared.speed / Const.Physics.maxSpeed)
  }
}
