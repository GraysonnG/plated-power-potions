package com.blanktheevil.platedpowerpotions.patches

import com.evacipated.cardcrawl.modthespire.Loader
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.powers.AbstractPower
import javassist.CtBehavior
import javassist.Modifier
import javassist.expr.ExprEditor
import javassist.expr.NewExpr
import org.clapper.util.classutil.*
import java.io.File
import java.net.URISyntaxException
import java.util.ArrayList

@Suppress("unused")
@SpirePatch(clz = AbstractPower::class, method = SpirePatch.CONSTRUCTOR)
object PlatedPowerSmartPatch {
  @Suppress("unused")
  @JvmStatic
  @SpireRawPatch
  fun platedPowerSmartPatch(ctBehavior: CtBehavior) {
    println("\nInfinite Spire: Smart Plated Power Patch")

    val foundClasses = findClasses()

    println("\t- Done Finding Classes...\n\t- Begin Patching...")
    var cInfo: ClassInfo? = null
    try {
      foundClasses.stream()
        .map {
          cInfo = it
          ctBehavior.declaringClass.classPool.get(it.className)
        }
        .forEach { ctClass ->
          ctClass.declaredMethods.asList().stream()
            .filter {
              it != null && (
                RemovePowerLocator().Locate(it).isNotEmpty() ||
                  ReducePowerLocator().Locate(it).isNotEmpty()
                )
            }
            .forEach {
              it.instrument(PlatedPowerInstrument())
            }
        }
    } catch (e: Exception) {
      println("\t- Failed to Patch Classes!")
      e.printStackTrace()
    }
    println("\t- Done Patching...")
  }

  private fun findClasses(): List<ClassInfo> {
    val filter = AndClassFilter(
      NotClassFilter(InterfaceOnlyClassFilter()),
      NotClassFilter(AbstractClassFilter()),
      ClassModifiersClassFilter(Modifier.PUBLIC),
      StsClassFilter(AbstractPower::class.java)
    )

    val finder = ClassFinder()
    finder.add(File(Loader.STS_JAR))
    Loader.MODINFOS.asList().stream()
      .filter { it.jarURL != null }
      .forEach {
        try {
          finder.add(File(it.jarURL.toURI()))
        } catch (e: URISyntaxException) {
          // do nothing
        }      }

    return ArrayList<ClassInfo>().also {
      finder.findClasses(it, filter)
    }
  }

  private class RemovePowerLocator : SpireInsertLocator() {
    override fun Locate(ctb: CtBehavior?): IntArray {
      val matcher = Matcher.NewExprMatcher(RemoveSpecificPowerAction::class.java)
      return try {
        LineFinder.findInOrder(ctb, matcher)
      } catch (e: Exception) {
        IntArray(0)
      }
    }
  }

  private class ReducePowerLocator : SpireInsertLocator() {
    override fun Locate(ctb: CtBehavior?): IntArray {
      val matcher = Matcher.NewExprMatcher(ReducePowerAction::class.java)
      return try {
        LineFinder.findInOrder(ctb, matcher)
      } catch (e: Exception) {
        IntArray(0)
      }
    }
  }

  private class StsClassFilter(private val clz: Class<*>) : ClassFilter {
    override fun accept(classInfo: ClassInfo?, classFinder: ClassFinder?): Boolean {
      return if (classInfo != null) {
        val superClasses = mutableMapOf<String, ClassInfo>()
        classFinder?.findAllSuperClasses(classInfo, superClasses)
        superClasses.containsKey(clz.name)
      } else {
        false
      }
    }
  }

  // Do not make this private
  class PlatedPowerInstrument : ExprEditor() {
    override fun edit(e: NewExpr) {
      when (e.className) {
        RemoveSpecificPowerAction::class.java.name ->
          getReplaceString(
            e.className,
            "(\$1,\$2,\"\")"
          )
        ReducePowerAction::class.java.name ->
          getReplaceString(
            e.className,
            "(\$1,\$2,\"\",0)"
          )
        else ->
          ""
      }.also {
        if (it.isNotEmpty()) e.replace(it)
      }
    }

    private fun getReplaceString(className: String, args: String): String =
      "{" +
        "if($spireFieldBooleanString) {" +
        "\$_=new $className$args;" +
        "} else {" +
        "\$_=\$proceed(\$\$);" +
        "}" +
        "}"

    private val spireFieldBooleanString: String =
      "${PlatedPowerInstrument::class.java.name}.spireFieldToBoolean(this)"

    companion object {
      @JvmStatic
      @Suppress("unused")
      fun spireFieldToBoolean(obj: AbstractPower): Boolean =
        PlatedPowerFieldPatch.isPlatedPower.get(obj) as Boolean
    }
  }
}