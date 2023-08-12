package com.blanktheevil.platedpowerpotions.vfx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.blanktheevil.platedpowerpotions.colorFromHSL
import com.blanktheevil.platedpowerpotions.vfxQueue
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import com.megacrit.cardcrawl.vfx.FastSmokeParticle
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect

class WildPotionEffect(
  private val speed: Float,
  private val start: Pair<Float, Float>,
  private val target: Pair<Float, Float>,
  private val onFinish: () -> Unit,
) : AbstractGameEffect() {
  private var vfxTimer: Float = 0.0f
  private var x: Float = 0f
  private var y: Float = 0f
  private val particleColor = colorFromHSL(Math.random().toFloat(), 1f, 1f)

  init {
    this.startingDuration = speed
    this.duration = this.startingDuration
    x = start.first
    y = start.second
  }

  override fun update() {
    x = Interpolation.fade.apply(target.first, start.first, duration / startingDuration)
    y = Interpolation.circleOut.apply(target.second, start.second, duration / startingDuration)
    vfxTimer -= Gdx.graphics.deltaTime

    if (vfxTimer < 0.0f) {
      vfxTimer = startingDuration / 50f
      vfxQueue.add(LightFlareParticleEffect(x, y, particleColor))
      vfxQueue.add(FastSmokeParticle(x, y))
    }

    duration -= Gdx.graphics.deltaTime
    if (duration < 0.0f) {
      isDone = true
      onFinish()
    }
  }

  override fun dispose() {

  }

  override fun render(sb: SpriteBatch) {

  }
}