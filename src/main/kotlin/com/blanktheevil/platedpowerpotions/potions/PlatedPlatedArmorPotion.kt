package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize
import com.megacrit.cardcrawl.powers.PlatedArmorPower

object PlatedPlatedArmorPotion: PotionData<PlatedArmorPower>(
    PlatedArmorPower::class.java,
    PlatedArmorPower.POWER_ID,
    2,
    potency = 4,
    primaryColor = null,
    liquidColor = Color.CYAN.cpy().mul(Color.DARK_GRAY.cpy()),
    bottleShape = PotionSize.ANVIL,
)