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
    val ZLetterPos = new Vector2(420, 3)
    val XLetterPos = new Vector2(682, 3)
  }

  object Physics {
    val blockSize = new Vector2(1f, 1f)
    val spawnPlaceZ = 100
    val RoadSpeedScale = 0.05f
    val playerXSpeed = 0.06f
    val maxZViewPort = 110
    val minZViewPort = -10
    val maxSpeed = 5.0f
    val mammyWidth = 0.3f // TODO: wtf?
    val mammyDepth = 0.08f // TODO: wtf?
    val playerWidth = 0.3f
    val playerDepth = 0.08f
    val roadRowWidth = 0.3f // TODO: wtf??? calculate width from real size

    def roadRowPos(roadRow: Int): Float = roadRowWidth * roadRow + roadRowWidth / 2f
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
            if ((0 to 3).contains(seed)) (false, false, false)
            else if ((4 to 5).contains(seed)) (true, false, false)
            else if ((6 to 9).contains(seed)) (false, true, false)
            else (false, false, true)
          case 2 =>
            if ((0 to 1).contains(seed)) (false, false, false)
            else if ((2 to 3).contains(seed)) (true, false, false)
            else if ((4 to 5).contains(seed)) (false, true, false)
            else if ((6 to 7).contains(seed)) (false, true, false)
            else if ((6 to 7).contains(seed)) (true, true, false)
            else (false, true, true)
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
