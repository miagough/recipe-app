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

    fun listRecipesNotInBook(): String {
        return if (numberOfRecipesNotInBook()==0){
            "No Unsaved Recipes "
        }else{
            var listOfRecipesNotInBook=""
            for (recipe in recipes){
                if (!recipe.recipeInBook) {
                    listOfRecipesNotInBook += "${recipes.indexOf(recipe)}: $recipe /n"
                }
            }
            listOfRecipesNotInBook
        }
    }

    fun listRecipesInBook(): String {
        return if (numberOfRecipesInBook()==0){
            "No Recipes in Book"
        }else{
            var listOfRecipesInBook = ""
            for (recipe in recipes){
                if (recipe.recipeInBook){
                    listOfRecipesInBook += "${recipes.indexOf(recipe)}: $recipe /n"
                }
            }
            listOfRecipesInBook
        }
    }

    fun listRecipesBySpecifiedRating(rating: Int): String {
        return if (recipes.isEmpty())
        {
            "No recipes stored"
        }else{
            var listOfRecipes = ""
            for (i in recipes.indices){
                if (recipes [i].recipeRating == rating){
                    listOfRecipes +=
                        """$i: ${recipes[i]}
                        """.trimIndent()
                }
            }
            if (listOfRecipes.equals("")) {
                "No recipes with rating: $rating"
            }else{
                "${numberOfRecipesByRating(rating)} recipes with rating $rating: $listOfRecipes"
            }
        }
        }

    fun listRecipesBySpecifiedDifficultyLevel(difficultyLevel: Int): String {
        return if (recipes.isEmpty())
        {
            "No recipes stored"
        }else{
            var listOfRecipes = ""
            for (i in recipes.indices){
                if (recipes [i].difficultyLevel == difficultyLevel){
                    listOfRecipes +=
                        """$i: ${recipes[i]}
                        """.trimIndent()
                }
            }
            if (listOfRecipes.equals("")) {
                "No recipes with difficulty level: $difficultyLevel"
            }else{
                "${numberOfRecipesByDifficultyLevel(difficultyLevel)} recipes with difficulty level $difficultyLevel: $listOfRecipes"
            }
        }
    }

    fun numberOfRecipesByDifficultyLevel(difficultyLevel: Int): Int{
        var counter = 0
        for (recipe in recipes) {
            if (recipe.difficultyLevel == difficultyLevel){
                counter++
            }
        }
        return counter
    }

    fun numberOfRecipesByRating(rating: Int): Int{
        var counter = 0
        for (recipe in recipes) {
            if (recipe.recipeRating == rating){
                counter++
            }
        }
        return counter
    }
    fun numberOfRecipes(): Int {
        return recipes.size
    }

    fun numberOfRecipesInBook(): Int {
        var counter = 0
        for (recipe in recipes){
            if (recipe.recipeInBook){
                counter++
            }
        }
        return counter
    }

    fun numberOfRecipesNotInBook(): Int {
        var counter = 0
        for (recipe in recipes){
            if (!recipe.recipeInBook){
                counter++
            }
        }
        return counter
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

    fun deleteRecipe(indexToDelete: Int): Recipe? {
        return if (isValidListIndex(indexToDelete, recipes)){
            recipes.removeAt(indexToDelete)
        }else null
    }
}