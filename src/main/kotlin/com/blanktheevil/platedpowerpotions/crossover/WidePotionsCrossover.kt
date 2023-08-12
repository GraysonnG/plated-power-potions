package com.blanktheevil.platedpowerpotions.crossover

import com.blanktheevil.platedpowerpotions.PlatedPowerPotions
import com.blanktheevil.platedpowerpotions.potions.WildPlatedPotion
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod
import com.evacipated.cardcrawl.modthespire.Loader

object WidePotionsCrossover {
  fun init() {
    PlatedPowerPotions.platedPotions.values.forEach {
      WidePotionsMod.whitelistSimplePotion(it.ID)
    }
    WidePotionsMod.whitelistSimplePotion(WildPlatedPotion.POTION_ID)
  }
}