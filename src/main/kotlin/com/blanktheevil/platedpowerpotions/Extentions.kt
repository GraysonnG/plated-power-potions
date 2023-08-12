package com.blanktheevil.platedpowerpotions

import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.LocalizedStrings

fun String.makeID(): String = "${PlatedPowerPotions.id}:$this"

val languagePack: LocalizedStrings get() = CardCrawlGame.languagePack
val vfxQueue = AbstractDungeon.effectsQueue