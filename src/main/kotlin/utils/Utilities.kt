package utils

import models.Ingredient
import models.Recipe

object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(recipesToFormat: List<Recipe>): String =
        recipesToFormat
            .joinToString(separator = "\n") { recipe -> "$recipe" }

    @JvmStatic
    fun formatSetString(ingredientsToFormat: Set<Ingredient>): String =
        ingredientsToFormat
            .joinToString(separator = "\n") { ingredients -> "\t$ingredients" }

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}


