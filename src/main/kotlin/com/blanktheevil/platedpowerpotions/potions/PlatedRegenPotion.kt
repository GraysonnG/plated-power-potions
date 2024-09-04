package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize
import com.megacrit.cardcrawl.powers.RegenPower

object PlatedRegenPotion: PotionData<RegenPower>(
    powerClass = RegenPower::class.java,
    powerID = RegenPower.POWER_ID,
    amount = 1,
    potency = 7,
    primaryColor = Color.WHITE,
    rarity = PotionRarity.RARE,
    bottleShape = PotionSize.HEART,
)