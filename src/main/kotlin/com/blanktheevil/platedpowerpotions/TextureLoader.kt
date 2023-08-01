package com.blanktheevil.platedpowerpotions

import com.badlogic.gdx.graphics.Texture
import org.apache.logging.log4j.LogManager

object TextureLoader {
  val textures = mutableMapOf<String, Texture>()
  private val logger = LogManager.getLogger(TextureLoader::class.java.name)

  fun getTexture(texturePath: String): Texture {
    return textures[texturePath] ?: try {
      loadTexture(texturePath)
    } catch (e: Exception) {
      logger.error("Could not find texture: $texturePath")
      Textures.missingTexture
    }
  }

  fun exists(texturePath: String): Boolean {
    return try {
      loadTexture(texturePath)
      true
    } catch (e: Exception) {
      logger.error("Could not find texture: $texturePath")
      e.printStackTrace()
      false
    }
  }

  private fun loadTexture(texturePath: String): Texture {
    return Texture(texturePath).also {
      it.setFilter(
        Texture.TextureFilter.Linear,
        Texture.TextureFilter.Linear
      )
      textures[texturePath] = it
    }
  }
}
