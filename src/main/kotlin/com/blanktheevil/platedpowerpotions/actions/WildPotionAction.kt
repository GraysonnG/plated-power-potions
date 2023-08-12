package com.blanktheevil.platedpowerpotions.actions

import basemod.ReflectionHacks
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.blanktheevil.platedpowerpotions.LightsOutObject
import com.blanktheevil.platedpowerpotions.vfx.ShowPotionEffect
import com.blanktheevil.platedpowerpotions.vfx.WildPotionEffect
import com.blanktheevil.platedpowerpotions.vfxQueue
import com.evacipated.cardcrawl.mod.widepotions.extensions.setPrivate
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ScreenShake
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect

class WildPotionAction(
  private val potion: AbstractPotion,
  private val start: Pair<Float, Float>,
  private val targets: List<Triple<AbstractCreature, AbstractPower, Boolean>>
) : AbstractGameAction() {

  private val speed = if (Settings.FAST_MODE) 0.2f else 0.5f

  override fun update() {
    this.isDone = true
    targets.forEach { (creature, power, _) ->
      addToTop(WildPotionApplyPowerAction(
        speed = speed,
        start = start,
        end = creature.hb.cX to creature.hb.cY,
        creature = creature,
        power = power
      ))
    }

    val showPotionEffectDuration = 1f

    addToTop(
      VFXAction(
        ShowPotionEffect(
          potion = potion,
          duration = showPotionEffectDuration,
        ),
        showPotionEffectDuration,
      )
    )
  }

  private class WildPotionApplyPowerAction(
    private val speed: Float,
    private val start: Pair<Float, Float>,
    private val end: Pair<Float, Float>,
    private val power: AbstractPower,
    private val creature: AbstractCreature,
  ) : AbstractGameAction() {
    override fun update() {
      this.isDone = true
      vfxQueue.add(
        WildPotionEffect(
          speed = speed,
          start = start,
          target = end,
          onFinish = {
            addToTop(ApplyPowerAction(
              creature,
              AbstractDungeon.player,
              power,
              power.amount
            ))
          }
        )
      )
    }
  }

  private class WaitAction(duration: Float) : AbstractGameAction() {
    init {
      this.duration = duration
      actionType = ActionType.WAIT
    }

    override fun update() {
      tickDuration()
    }
  }
}