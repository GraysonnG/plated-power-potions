package com.blanktheevil.platedpowerpotions.potions

import basemod.BaseMod
import com.blanktheevil.platedpowerpotions.languagePack
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize
import com.megacrit.cardcrawl.powers.AbstractPower
import kotlin.random.Random

internal val PotionData<*>.strings: PowerStrings
    get() = languagePack.getPowerStrings(this.powerID)

internal fun <T: AbstractPower> PotionData<T>.getInstance(): T {
    return this.powerClass.getConstructor(
        AbstractCreature::class.java,
        Int::class.java,
    ).newInstance(AbstractDungeon.player, this.amount)
}

internal fun <T: AbstractPower> PotionData<T>.toPlatedPowerPotion() =
    PlatedPowerPotion(potionData = this)

internal fun getBottle(powerID: String): PotionSize {
    val rand = Random(powerID.encodeToByteArray().sum().toLong())
    val list = listOf(
        PotionSize.T,
        PotionSize.S,
        PotionSize.M,
        PotionSize.H,
        PotionSize.SPHERE,
        PotionSize.BOTTLE,
        PotionSize.JAR,
        PotionSize.BOLT
    )
    return list[rand.nextInt(list.size)]
}

internal fun PlatedPowerPotion<*>.addToBaseMod() {
    BaseMod.addPotion(
        PlatedPowerPotion::class.java,
        potionData.liquidColor.cpy(),
        potionData.hybridColor?.cpy(),
        potionData.primaryColor?.cpy(),
        ID,
        potionData.playerClass
    )
}