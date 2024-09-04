package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.powers.DexterityPower

object PlatedDexterityPotion: PotionData<DexterityPower>(
    DexterityPower::class.java,
    DexterityPower.POWER_ID,
    1,
    primaryColor = Color.CHARTREUSE,
)