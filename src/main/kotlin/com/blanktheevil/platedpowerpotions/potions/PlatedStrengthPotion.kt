package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.powers.StrengthPower

object PlatedStrengthPotion: PotionData<StrengthPower>(
    StrengthPower::class.java,
    StrengthPower.POWER_ID,
    1,
    primaryColor = Color.CORAL,
)