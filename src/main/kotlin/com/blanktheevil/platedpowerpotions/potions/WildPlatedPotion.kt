package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.blanktheevil.platedpowerpotions.LightsOutObject
import com.blanktheevil.platedpowerpotions.actions.WildPotionAction
import com.blanktheevil.platedpowerpotions.languagePack
import com.blanktheevil.platedpowerpotions.makeID
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.localization.UIStrings
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.powers.*
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
), LightsOutObject {

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
      potion = this,
      start = Settings.WIDTH.div(2f) to Settings.HEIGHT.div(4f).times(3f),
      targets = List(getPotency()) {
        val target = creatures.random(random)
        val power = getPower(target)
        val isPlated = AbstractDungeon.miscRng.randomBoolean()
        Triple(target, power, isPlated)
      }
    ))
  }

  override fun getPotency(p: Int): Int {
    return 5
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
        StrengthPower(target, getPotency() / 2)
      }
      DexterityPower.POWER_ID -> {
        DexterityPower(target, getPotency() / 2)
      }
      WeakPower.POWER_ID -> {
        WeakPower(target, getPotency() / 2, false)
      }
      VulnerablePower.POWER_ID -> {
        VulnerablePower(target, getPotency() / 2, false)
      }
      RitualPower.POWER_ID -> {
        RitualPower(target, getPotency() / 2, isTargetPlayer)
      }
      PoisonPower.POWER_ID -> {
        PoisonPower(target, AbstractDungeon.player, getPotency() / 2)
      }
      BufferPower.POWER_ID -> {
        BufferPower(target, getPotency() / 2)
      }
      ThornsPower.POWER_ID -> {
        ThornsPower(target, getPotency() / 2)
      }
      else -> {
        TheBombPower(target, 2, getPotency() * 4)
      }
    }
  }

  override fun _lightsOutGetXYRI(): FloatArray {
    return floatArrayOf(
      this.posX,
      this.posY,
      100f,
      1f,
    )
  }

  override fun _lightsOutGetColor(): Array<Color> {
    return arrayOf(this.liquidColor.cpy())
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
      MetallicizePower.POWER_ID,
      RegenPower.POWER_ID,
      IntangiblePlayerPower.POWER_ID,
      // Custom Rage Power
    )
  }
}