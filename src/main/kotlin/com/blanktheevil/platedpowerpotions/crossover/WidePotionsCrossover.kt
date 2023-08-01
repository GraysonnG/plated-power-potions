package com.blanktheevil.platedpowerpotions.crossover

import com.blanktheevil.platedpowerpotions.PlatedPowerPotions
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod
import com.evacipated.cardcrawl.modthespire.Loader

object WidePotionsCrossover {
  fun init() {
    PlatedPowerPotions.platedPotions.values.forEach {
      WidePotionsMod.whitelistSimplePotion(it.ID)
    }
  }
}