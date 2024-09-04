package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize
import com.megacrit.cardcrawl.powers.EnergizedPower

object PlatedEnergizedPotion: PotionData<EnergizedPower>(
    EnergizedPower::class.java,
    EnergizedPower.POWER_ID,
    1,
    2,
    primaryColor = Color.YELLOW,
    bottleShape = PotionSize.BOLT
)