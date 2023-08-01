package com.blanktheevil.platedpowerpotions

import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize
import kotlin.random.Random

class PotionData<T>(
  private val powerClass: Class<T>,
  val powerID: String,
  val amount: Int,
  val potency: Int = 5,
  val primaryColor: Color? = Color.GRAY.cpy(),
  val hybridColor: Color? = Color.CYAN.cpy(),
  val liquidColor: Color = primaryColor?.cpy()?.mul(Color.GRAY.cpy()) ?: Color.WHITE.cpy().also { it.a = 0f },
  val bottleShape: PotionSize = getBottle(powerID),
  val rarity: PotionRarity = PotionRarity.UNCOMMON,
) {
  val strings = languagePack.getPowerStrings(powerID)

  fun getInstance(player: AbstractPlayer): T {
    return powerClass.getConstructor(
      AbstractCreature::class.java,
      Int::class.java
    ).newInstance(player, amount)
  }

  companion object {
    fun getBottle(powerID: String): PotionSize {
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
  }
}