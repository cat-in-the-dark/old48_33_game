package com.catinthedark.sszb

/**
 * Created by over on 18.04.15.
 */
class Shared(var lvlTime: Float,
             var lvlDistance: Float) {
  def reset() = {
    lvlTime = 0f
    lvlDistance= 0f
  }
}
