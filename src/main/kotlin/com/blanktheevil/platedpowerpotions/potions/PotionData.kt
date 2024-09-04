package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize
import com.megacrit.cardcrawl.powers.AbstractPower

open class PotionData<T: AbstractPower>(
    val powerClass: Class<T>,
    val powerID: String,
    val amount: Int,
    val potency: Int = 5,
    val primaryColor: Color? = Color.GRAY.cpy(),
    val hybridColor: Color? = Color.CYAN.cpy(),
    val liquidColor: Color = primaryColor?.cpy()?.mul(Color.GRAY.cpy()) ?: Color.WHITE.cpy().also { it.a = 0f },
    val bottleShape: PotionSize = getBottle(powerID),
    val rarity: PotionRarity = PotionRarity.UNCOMMON,
    val playerClass: PlayerClass? = null,
) {
    companion object {
        fun addAllPotions(): Map<String, PlatedPowerPotion<*>> =
            listOf(
                PlatedStrengthPotion,
                PlatedDexterityPotion,
                PlatedFocusPotion,
                PlatedPlatedArmorPotion,
                PlatedThornsPotion,
                PlatedEnergizedPotion,
                PlatedRegenPotion,
            )
                .map { it.toPlatedPowerPotion() }
                .onEach { it.addToBaseMod() }
                .associateBy { it.ID }
    }
}