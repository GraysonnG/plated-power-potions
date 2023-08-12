package com.blanktheevil.platedpowerpotions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.LocalizedStrings

fun String.makeID(): String = "${PlatedPowerPotions.id}:$this"

val languagePack: LocalizedStrings get() = CardCrawlGame.languagePack
val vfxQueue = AbstractDungeon.effectsQueue

fun colorFromHSL(hue: Float, sat: Float, light: Float): Color {
  return java.awt.Color.getHSBColor(hue, sat, light).let {
    Color(
      it.red.div(255f),
      it.green.div(255f),
      it.blue.div(255f),
      1f)
  }
}