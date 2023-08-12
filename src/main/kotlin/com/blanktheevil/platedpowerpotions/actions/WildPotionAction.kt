package com.blanktheevil.platedpowerpotions.actions

import com.blanktheevil.platedpowerpotions.vfx.WildPotionEffect
import com.blanktheevil.platedpowerpotions.vfxQueue
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class WildPotionAction(
  private val start: Pair<Float, Float>,
  private val targets: List<Triple<AbstractCreature, AbstractPower, Boolean>>
) : AbstractGameAction() {

  private val speed = if (Settings.FAST_MODE) 0.2f else 0.5f

  override fun update() {
    this.isDone = true
    targets.forEach { (creature, power, isPlated) ->
      addToTop(object: AbstractGameAction() {
        override fun update() {
          this.isDone = true
          println(creature.id)
          vfxQueue.add(
            WildPotionEffect(
              speed = speed,
              start = start,
              target = creature,
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
      })
      addToTop(WaitAction(speed))
    }
  }

  class WaitAction(duration: Float) : AbstractGameAction() {
    init {
      this.duration = duration
      actionType = ActionType.WAIT
    }

    override fun update() {
      tickDuration()
    }
  }
}