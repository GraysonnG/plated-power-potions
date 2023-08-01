package com.blanktheevil.platedpowerpotions.potions

import basemod.BaseMod
import basemod.abstracts.CustomPotion
import com.blanktheevil.platedpowerpotions.PotionData
import com.blanktheevil.platedpowerpotions.languagePack
import com.blanktheevil.platedpowerpotions.makeID
import com.blanktheevil.platedpowerpotions.powers.PlatedPower
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.localization.UIStrings
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.rooms.AbstractRoom

class PlatedPowerPotion<T: AbstractPower>(
  val potionData: PotionData<T>,
  val powerID: String,
  val rarity: PotionRarity,
  val size: PotionSize,
  val color: PotionColor,
  private val potionPotency: Int = potionData.potency,
  private val strings: UIStrings = languagePack.getUIString("PlatedPowerPotion".makeID()),
  name: String = parseString(strings.TEXT[0], languagePack.getPowerStrings(powerID).NAME, 0),
  id: String = "Plated${powerID}Potion".makeID(),
) : CustomPotion(
  name,
  id,
  rarity,
  size,
  color,
) {

  init {
    isThrown = false
    description = parseString(
      strings.TEXT[1],
      potionData.strings.NAME,
      potionData.amount * getPotency()
    )

    tips.clear()
    tips.add(PowerTip(
      this.name,
      this.description,
    ))

    val powerStrings = languagePack.getUIString("PlatedPower".makeID())

    tips.add(PowerTip(
      parseString(powerStrings.TEXT[0], potionData.strings.NAME, 0),
      parseString(
        strings.TEXT[2] + powerStrings.TEXT[2],
        potionData.strings.NAME,
        potionData.amount
      )
    ))
  }

  fun addToBaseMod() {
    BaseMod.addPotion(
      PlatedPowerPotion::class.java,
      potionData.liquidColor.cpy(),
      potionData.hybridColor?.cpy(),
      potionData.primaryColor?.cpy(),
      ID,
    )
  }

  override fun use(player: AbstractCreature?) {
    val target = AbstractDungeon.player

    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
      addToBot(
        ApplyPowerAction(
          target,
          target,
          PlatedPower(potionData.getInstance(
            AbstractDungeon.player
          ), getPotency())
        )
      )
    }
  }

  override fun makeCopy(): AbstractPotion {
    return PlatedPowerPotion(
      potionData = potionData,
      powerID = potionData.powerID,
      potionPotency = potionPotency,
      rarity = rarity,
      size = size,
      color = color,
    )
  }

  override fun getPotency(p0: Int): Int {
    return potionPotency
  }

  companion object {
    private fun parseString(string: String, name: String, amount: Int): String {
      return string
        .replace("{name}", name)
        .replace("{amount}", amount.toString())
    }
  }
}