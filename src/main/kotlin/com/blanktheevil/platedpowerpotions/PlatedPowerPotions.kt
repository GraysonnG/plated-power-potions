package com.blanktheevil.platedpowerpotions

import basemod.BaseMod
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Color
import com.blanktheevil.platedpowerpotions.crossover.WidePotionsCrossover
import com.blanktheevil.platedpowerpotions.potions.PlatedPowerPotion
import com.evacipated.cardcrawl.modthespire.Loader
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.localization.UIStrings
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.powers.*

@SpireInitializer
class PlatedPowerPotions : PostInitializeSubscriber {
  override fun receivePostInitialize() {
    BaseMod.loadCustomStringsFile(
      UIStrings::class.java,
      makePath(
        Settings.language,
        "UIStrings"
      )
    )

    platedPotions = listOf(
      PotionData(
        StrengthPower::class.java,
        StrengthPower.POWER_ID,
        1,
        primaryColor = Color.CORAL,
      ),
      PotionData(
        DexterityPower::class.java,
        DexterityPower.POWER_ID,
        1,
        primaryColor = Color.CHARTREUSE,
      ),
      PotionData(
        FocusPower::class.java,
        FocusPower.POWER_ID,
        1,
        primaryColor = Color.ROYAL,
      ),
      PotionData(
        PlatedArmorPower::class.java,
        PlatedArmorPower.POWER_ID,
        2,
        potency = 4,
        primaryColor = null,
        liquidColor = Color.CYAN.cpy().mul(Color.DARK_GRAY.cpy()),
        bottleShape = AbstractPotion.PotionSize.ANVIL,
      ),
      PotionData(
        ArtifactPower::class.java,
        ArtifactPower.POWER_ID,
        1,
        primaryColor = Color.YELLOW,
      ),
      PotionData(
        ThornsPower::class.java,
        ThornsPower.POWER_ID,
        1,
        primaryColor = Color.LIGHT_GRAY,
      ),
      PotionData(
        EnergizedPower::class.java,
        EnergizedPower.POWER_ID,
        1,
        2,
        primaryColor = Color.YELLOW,
        bottleShape = AbstractPotion.PotionSize.BOLT
      ),
      PotionData(
        RegenPower::class.java,
        RegenPower.POWER_ID,
        1,
        potency = 7,
        primaryColor = Color.WHITE,
        rarity = AbstractPotion.PotionRarity.RARE,
        bottleShape = AbstractPotion.PotionSize.HEART,
      )
    )
      .map {
        PlatedPowerPotion(
          potionData = it,
          powerID = it.powerID,
          rarity = it.rarity,
          size = it.bottleShape,
          color = AbstractPotion.PotionColor.ATTACK,
        )
      }
      .associateBy { it.ID }
      .also {
        it.values.forEach { potion ->
          potion.addToBaseMod()
        }
      }

    if (Loader.isModLoaded("widepotions")) {
      WidePotionsCrossover.init()
    }
  }

  private fun makePath(language: Settings.GameLanguage, fileName: String): String {
    var langFolder = "platedpowerpotions/local/"
    when (language) {
      Settings.GameLanguage.ENG -> langFolder += "eng"
      else -> langFolder += "eng"
    }

    return "$langFolder/$fileName.json"
  }

  companion object {
    internal var id: String = "plated-power-potions"

    internal var platedPotions: Map<String, PlatedPowerPotion<*>>
      = emptyMap()

    @Suppress("unused")
    @JvmStatic
    fun initialize() {
      BaseMod.subscribe(PlatedPowerPotions())
    }

    fun createPath(restOfPath: String): String {
      return "$id/images/$restOfPath"
    }
  }
}