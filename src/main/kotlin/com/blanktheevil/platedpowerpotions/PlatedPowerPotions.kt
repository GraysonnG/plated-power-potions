package com.blanktheevil.platedpowerpotions

import basemod.BaseMod
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Color
import com.blanktheevil.platedpowerpotions.crossover.WidePotionsCrossover
import com.blanktheevil.platedpowerpotions.potions.PlatedPowerPotion
import com.blanktheevil.platedpowerpotions.potions.PotionData
import com.blanktheevil.platedpowerpotions.potions.WildPlatedPotion
import com.evacipated.cardcrawl.modthespire.Loader
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.localization.UIStrings

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

    platedPotions = PotionData.addAllPotions()

    BaseMod.addPotion(
      WildPlatedPotion::class.java,
      Color.CORAL.cpy(),
      Color.CHARTREUSE.cpy(),
      Color.ROYAL.cpy(),
      WildPlatedPotion.POTION_ID,
    )

    if (Loader.isModLoaded(WIDE_POTION_MODID)) {
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
    internal const val MODID: String = "plated-power-potions"
    internal const val WIDE_POTION_MODID = "widepotions"

    internal var platedPotions: Map<String, PlatedPowerPotion<*>>
      = emptyMap()

    @Suppress("unused")
    @JvmStatic
    fun initialize() {
      BaseMod.subscribe(PlatedPowerPotions())
    }
  }
}