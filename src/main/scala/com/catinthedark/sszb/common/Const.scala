package com.catinthedark.sszb.common

import com.badlogic.gdx.math.{Rectangle, Vector2}

/**
 * Created by over on 03.01.15.
 */
object Const {

  object UI {
    val screenSize = new Vector2(1152, 768)
    val timerPos = new Vector2(900, 740)
  }

  object Physics {
    val blockSize = new Vector2(1f, 1f)
    val spawnPlaceZ = 100
  }

  object Timing {
    val levelTime = 5f
  }

  object Distance {
    val levelDistance = 42f
  }
  
  object Difficulty {
    val spawnDelta = 10

    /**
     * depends on distance, lvl and some random make decision to generate entities
     */
    def spawnRandom(lvl: Int, lvlDistance: Float, lastSpawnDistance: Float, seed: Int): (Boolean, Boolean, Boolean) = {
      if (lvlDistance - lastSpawnDistance > spawnDelta) {
        lvl match {
          case _ => (true, true, true)
        }
      } else {
        (false, false, false)
      }
    }

    def mammySpeed(lvl: Int): Float = {
      //TODO: depends on lvl calculate speed
      10
    }

    val seedDivider = 10
  }
}
