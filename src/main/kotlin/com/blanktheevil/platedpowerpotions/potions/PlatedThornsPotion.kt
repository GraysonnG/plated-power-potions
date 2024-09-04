package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.powers.ThornsPower

object PlatedThornsPotion: PotionData<ThornsPower>(
    ThornsPower::class.java,
    ThornsPower.POWER_ID,
    1,
    primaryColor = Color.LIGHT_GRAY,
)