package com.blanktheevil.platedpowerpotions.patches

import com.blanktheevil.platedpowerpotions.PlatedPowerPotions
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.helpers.PotionHelper
import com.megacrit.cardcrawl.potions.AbstractPotion

@SpirePatch2(
  clz = PotionHelper::class,
  method = "getPotion"
)
object PlatedPowerBaseModPatch {
  @JvmStatic
  @SpirePrefixPatch
  fun platedPowerGetPotionPatch(
    name: String
  ): SpireReturn<AbstractPotion> {
    return if (PlatedPowerPotions.platedPotions.containsKey(name)) {
      SpireReturn.Return(
        PlatedPowerPotions.platedPotions[name]?.makeCopy()
      )
    } else {
      SpireReturn.Continue()
    }
  }
}