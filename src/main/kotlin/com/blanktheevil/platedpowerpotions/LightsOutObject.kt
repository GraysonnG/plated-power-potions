package com.blanktheevil.platedpowerpotions

import com.badlogic.gdx.graphics.Color

interface LightsOutObject {
  fun _lightsOutGetXYRI(): FloatArray
  fun _lightsOutGetColor(): Array<Color>
}