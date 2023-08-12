package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.blanktheevil.platedpowerpotions.actions.WildPotionAction
import com.blanktheevil.platedpowerpotions.languagePack
import com.blanktheevil.platedpowerpotions.makeID
import com.blanktheevil.platedpowerpotions.powers.PlatedPower
import com.blanktheevil.platedpowerpotions.vfx.WildPotionEffect
import com.blanktheevil.platedpowerpotions.vfxQueue
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.localization.UIStrings
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.BufferPower
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.PoisonPower
import com.megacrit.cardcrawl.powers.RitualPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.powers.TheBombPower
import com.megacrit.cardcrawl.powers.ThornsPower
import com.megacrit.cardcrawl.powers.VulnerablePower
import com.megacrit.cardcrawl.powers.WeakPower
import kotlin.random.Random


class WildPlatedPotion(
  strings: UIStrings = languagePack.getUIString(POTION_ID),
) : AbstractPotion(
  strings.TEXT[0],
  POTION_ID,
  PotionRarity.RARE,
  PotionSize.SPHERE,
  PotionEffect.RAINBOW,
  Color.WHITE.cpy(),
  null,
  null,
) {

  override fun initializeData() {
    val strings = CardCrawlGame.languagePack.getUIString(POTION_ID)
    this.description = strings.TEXT[1]

    tips.clear()

    tips.add(
      PowerTip(
        this.name,
        this.description,
      )
    )
  }

  override fun use(creature: AbstractCreature?) {
    val player = AbstractDungeon.player
    val monsters = AbstractDungeon.getMonsters().monsters.filter {
      !it.isDead && !it.isDying && !it.isDeadOrEscaped
    }

    val creatures = mutableListOf<AbstractCreature>(
      player,
    ).apply {
      this.addAll(monsters.toTypedArray())
    }

    val random = Random(AbstractDungeon.miscRng.counter)

    addToBot(WildPotionAction(
      start = hb.cX to hb.cY,
      targets = List(5) {
        val target = creatures.random(random)
        val power = getPower(target)
        val isPlated = AbstractDungeon.miscRng.randomBoolean()
        Triple(target, power, isPlated)
      }
    ))
  }

  override fun getPotency(p: Int): Int {
    return 2
  }

  override fun makeCopy(): AbstractPotion {
    return this::class.java.newInstance()
  }

  private fun getPower(target: AbstractCreature): AbstractPower {
    val rand = AbstractDungeon.miscRng.random(VALID_POWERS.size - 1)
    val powerID = VALID_POWERS[rand]
    val isTargetPlayer = target is AbstractPlayer

    return when (powerID) {
      StrengthPower.POWER_ID -> {
        StrengthPower(target, getPotency())
      }
      DexterityPower.POWER_ID -> {
        DexterityPower(target, getPotency())
      }
      WeakPower.POWER_ID -> {
        WeakPower(target, getPotency(), false)
      }
      VulnerablePower.POWER_ID -> {
        VulnerablePower(target, getPotency(), false)
      }
      RitualPower.POWER_ID -> {
        RitualPower(target, getPotency(), isTargetPlayer)
      }
      PoisonPower.POWER_ID -> {
        PoisonPower(target, AbstractDungeon.player, getPotency())
      }
      BufferPower.POWER_ID -> {
        BufferPower(target, getPotency())
      }
      ThornsPower.POWER_ID -> {
        ThornsPower(target, getPotency())
      }
      else -> {
        TheBombPower(target, 2, getPotency() * 4)
      }
    }
  }

  companion object {
    val POTION_ID = "WildPlatedPotion".makeID()
    val VALID_POWERS = listOf(
      StrengthPower.POWER_ID,
      DexterityPower.POWER_ID,
      WeakPower.POWER_ID,
      VulnerablePower.POWER_ID,
      RitualPower.POWER_ID,
      PoisonPower.POWER_ID,
      BufferPower.POWER_ID,
      ThornsPower.POWER_ID,
      TheBombPower.POWER_ID,
    )
  }
}