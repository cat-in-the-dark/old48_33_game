package com.catinthedark.sszb.common

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.{Rectangle, Vector2}

/**
 * Created by over on 03.01.15.
 */
object Const {

  object UI {
    val screenSize = new Vector2(1152, 768)
    val timerPos = new Vector2(900, 740)
    val distPos = new Vector2(900, 700)
    val sliderPos = new Vector2(476, 23)
    val pointerPos = new Vector2(466, 43)
  }

  object Physics {
    val blockSize = new Vector2(1f, 1f)
    val spawnPlaceZ = 100
    val RoadSpeedScale = 0.05f
    val maxZViewPort = 110
    val minZViewPort = -10
    val maxSpeed = 5.0f
  }

  object Timing {
    val levelTime = 5f
  }

  object Distance {
    val levelDistance = 42f
  }

  object Difficulty {
    val spawnDelta = 1

    /**
     * depends on distance, lvl and some random make decision to generate entities
     */
    def spawnRandom(lvl: Int, lvlDistance: Float, lastSpawnDistance: Float, seed: Int): (Boolean, Boolean, Boolean) = {
      if (lvlDistance - lastSpawnDistance > spawnDelta) {
        lvl match {
          case 1 =>
            if ((0 to 4).contains(seed)) (false, false, false)
            else if ((5 to 6).contains(seed)) (true, false, false)
            else if ((7 to 8).contains(seed)) (false, true, false)
            else (false, false, true)
          case 2 =>
            (true, false, true)
          case _ => (false, true, false)
        }
      } else {
        (false, false, false)
      }
    }

    def mammySpeed(lvl: Int): Float = {
      //TODO: depends on lvl calculate speed
      1
    }

    val seedDivider = 10
  }

  object Pedals {
    val leftPedalKey = Input.Keys.Z
    val rightPedalKey = Input.Keys.X
  }
}
