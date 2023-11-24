package controllers

import models.Recipe

class RecipeAPI {
    private var recipes = ArrayList<Recipe>()

    fun add(recipe: Recipe): Boolean{
        return recipes.add(recipe)
    }

    fun listAllRecipes(): String {
        return if (recipes.isEmpty()) {
            "No recipes stored"
        }else{
            var listOfRecipes = ""
            for (i in recipes.indices){
                listOfRecipes += "${i}: ${recipes[i]} \n"
            }
            listOfRecipes
        }
    }

    fun numberOfRecipes(): Int {
        return recipes.size
    }

    fun findRecipe(index: Int): Recipe? {
        return if (isValidListIndex(index, recipes)){
            recipes[index]
        }else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}