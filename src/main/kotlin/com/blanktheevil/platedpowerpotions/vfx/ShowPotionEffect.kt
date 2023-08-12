package com.blanktheevil.platedpowerpotions.vfx

import basemod.ReflectionHacks
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.blanktheevil.platedpowerpotions.LightsOutObject
import com.blanktheevil.platedpowerpotions.vfxQueue
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.ScreenShake
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect

class ShowPotionEffect(
  potion: AbstractPotion,
  duration: Float = 0.5f,
): AbstractGameEffect(), LightsOutObject {
  private val potion: AbstractPotion = potion.makeCopy()
  private val start = potion.posX to potion.posY
  private val target = Settings.WIDTH.div(2f) to Settings.HEIGHT.div(4f).times(3f)
  private val fullDuration = duration
  private val speed = 360f
  private var angle = 0f
  private var intensity = 1f
  private var radius = 150f

  private var jitter = 0f

  init {
    this.duration = fullDuration
  }

  override fun update() {
    duration -= Gdx.graphics.deltaTime
    angle += Gdx.graphics.deltaTime * speed

    ReflectionHacks.setPrivate(potion, AbstractPotion::class.java, "angle", angle)

    jitter = Interpolation.linear.apply(10f.times(Settings.scale), 0f, duration / fullDuration)
    val xJitter = (Math.random() * jitter) - jitter.div(2f)
    val yJitter = (Math.random() * jitter) - jitter.div(2f)

    potion.posX = Interpolation.linear.apply(
      target.first,
      start.first,
      duration / fullDuration
    ) + xJitter.toFloat()
    potion.posY = Interpolation.circleIn.apply(
      target.second,
      start.second,
      duration / fullDuration
    ) + yJitter.toFloat()
    potion.scale = Interpolation.circleOut.apply(2f, 1f, duration / fullDuration)

    intensity = Interpolation.circleOut.apply(10f, 1f, duration / fullDuration)
    radius = Interpolation.circleOut.apply(500f, 150f, duration / fullDuration)

    if (duration < 0f) {
      isDone = true
      vfxQueue.add(
        SanctityEffect(potion.posX, potion.posY)
      )
      CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE")
      CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, true)
    }
  }

  override fun _lightsOutGetXYRI(): FloatArray {
    return floatArrayOf(
      potion.posX,
      potion.posY,
      radius,
      intensity
    )
  }

  override fun _lightsOutGetColor(): Array<Color> {
    return arrayOf(potion.liquidColor.cpy().also { it.a = 1f })
  }

  override fun render(sb: SpriteBatch) {
    potion.render(sb)
  }

  override fun dispose() {
  }
}