package com.blanktheevil.platedpowerpotions.potions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass
import com.megacrit.cardcrawl.powers.FocusPower

object PlatedFocusPotion: PotionData<FocusPower>(
    FocusPower::class.java,
    FocusPower.POWER_ID,
    1,
    primaryColor = Color.ROYAL,
    playerClass = PlayerClass.DEFECT,
)