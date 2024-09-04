package com.blanktheevil.platedpowerpotions.powers

import basemod.interfaces.CloneablePowerInterface
import com.badlogic.gdx.graphics.Texture
import com.blanktheevil.platedpowerpotions.languagePack
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.powers.AbstractPower

abstract class Power(
  owner: AbstractCreature,
  amount: Int,
  id: String,
  img: Texture,
  priority: Int,
  type: PowerType = PowerType.BUFF,
  isTurnBased: Boolean = false,
  isPostAction: Boolean = false,
  canGoNegative: Boolean = false,
  strings: PowerStrings = languagePack.getPowerStrings(id)
) : AbstractPower(), CloneablePowerInterface {
  constructor(owner: AbstractCreature, amount: Int, builder: Builder) : this(
    owner,
    amount,
    builder.id,
    builder.img,
    builder.priority,
    builder.type,
    builder.isTurnBased,
    builder.isPostAction,
    builder.canGoNegative,
    builder.strings
  )

  init {
    this.owner = owner
    this.amount = amount
    this.name = strings.NAME
    this.ID = id
    this.img = img
    this.type = type
    this.priority = priority
    this.isTurnBased = isTurnBased
    this.isPostActionPower = isPostAction
    this.canGoNegative = canGoNegative
  }

  abstract fun updateDesc()

  override fun updateDescription() {
    updateDesc()
  }

  class Builder(
    val id: String,
    val strings: PowerStrings = languagePack.getPowerStrings(id),
    var img: Texture = ImageMaster.WHITE_SQUARE_IMG,
    var priority: Int = 0,
    var type: PowerType = PowerType.BUFF,
    var isTurnBased: Boolean = false,
    var isPostAction: Boolean = false,
    var canGoNegative: Boolean = false
  ) {
    fun isTurnBased(): Builder = this.apply { isTurnBased = true }
    fun isPostAction(): Builder = this.apply { isPostAction = true }
  }
}