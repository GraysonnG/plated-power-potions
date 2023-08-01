package com.blanktheevil.platedpowerpotions

import com.badlogic.gdx.graphics.Texture

object Textures {
  val powers = TextureGetter("powers")
  val missingTexture by lazy { Texture(missingTexturePath) }
  var missingTexturePath: String = ""
}

class TextureGetter(private val folder: String) {
  fun get(texture: String): Texture = TextureLoader.getTexture(getString(texture, true))
  fun getString(texture: String, ignoreValidation: Boolean = false): String {
    val finalString = getString(folder, texture)
    val validated = if (!ignoreValidation) TextureLoader.exists(finalString) else true

    return when {
      validated -> finalString
      else -> Textures.missingTexturePath
    }
  }

  companion object {
    private fun getString(folder: String, texture: String) = PlatedPowerPotions.createPath("$folder/") + texture
  }
}