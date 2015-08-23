package com.catinthedark.sszb.common

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.catinthedark.sszb.Shared

/**
 * Created by over on 03.01.15.
 */
object Const {
  val soundVolume: Float = 0.5f//0.8f
  val musicVolume: Float = 0.8f//0.2f


  object UI {
    val screenSize = new Vector2(1152, 768)
    val timerPos = new Vector2(900, 740)
    val distPos = new Vector2(900, 700)
    val sliderPos = new Vector2(476, 23)
    val pointerPos = new Vector2(466, 43)
    val palkaPos = new Vector2(474, 20)
    val ZLetterPos = new Vector2(420, 3)
    val XLetterPos = new Vector2(682, 3)
    val speedometerPos = new Vector2(950, 3)
    val speedPalkaPos = new Vector2(1008, 61)
  }

  object Physics {
    val lampWidth: Float = 0.7f
    val lampDepth: Float = 0.1f

    val signWidth: Float = 0.6f
    val signDepth: Float = 0.1f
    
    val treeWidth: Float = 0.6f
    val treeDepth: Float = 0.1f

    val bushWidth: Float = 0.8f
    val bushDepth: Float = 0.1f

    val blockSize = new Vector2(1f, 1f)
    val spawnPlaceZ = 1.7f
    val roadSpeedScale = 0.05f
    var trashSpeedScale = roadSpeedScale *  3.0f
    val playerXSpeed = 0.06f
    val maxZViewPort = 2.0f
    val minZViewPort = -0.3f
    val maxSpeed = 5.0f
    val mammyWidth = 0.3f // TODO: wtf?
    val mammyDepth = 0.08f // TODO: wtf?
    val playerWidth = 0.7f
    val playerDepth = 0.08f
    val roadRowWidth = 0.6f // TODO: wtf??? calculate width from real size

    val roadLeftBorderX = 0.8f
    val roadRightBorderX = -1.4f
    val playerBorderTeleportationX = 0.2f

    def roadRowPos(roadRow: Int): Float = -roadRowWidth * roadRow - roadRowWidth / 2f
  }

  object Timing {
    val levelTime = 5f
    val fallTime = 0.85f
  }

  object Distance {
    val levelDistance = 50f
  }

  object Difficulty {
    val spawnDelta = 1
    val fallTimeout = 2.0f

    /**
     * depends on distance, lvl and some random make decision to generate entities
     */
    def spawnRandom(lvl: Int, lvlDistance: Float, lastSpawnDistance: Float, seed: Int): (Boolean, Boolean, Boolean) = {
      if (lvlDistance - lastSpawnDistance > spawnDelta) {
        lvl match {
          case 1 =>
            if ((0 to 1).contains(seed)) (false, false, false)
            else if ((2 to 3).contains(seed)) (true, false, false)
            else if ((4 to 5).contains(seed)) (false, true, false)
            else (false, false, true)
          case 2 =>
            if ((0 to 1).contains(seed)) (false, false, false)
            else if ((2 to 3).contains(seed)) (true, false, false)
            else if ((4 to 5).contains(seed)) (false, true, false)
            else if ((6 to 7).contains(seed)) (false, false, true)
            else if ((8 to 9).contains(seed)) (true, true, false)
            else if ((10 to 11).contains(seed)) (false, true, true)
            else (true, false, true)
          case _ => (false, true, false)
        }
      } else {
        (false, false, false)
      }
    }

    def mammySpeed(lvl: Int): Float = {
      //TODO: depends on lvl calculate speed
      0.15f
    }

    def seedDivider(lvl: Int) = {
      lvl match {
        case 1 => 8
        case 2 => 14
        case _ => 20
      }
    }
  }

  object Pedals {
    val leftPedalKey = Input.Keys.Z
    val rightPedalKey = Input.Keys.X
  }

}
