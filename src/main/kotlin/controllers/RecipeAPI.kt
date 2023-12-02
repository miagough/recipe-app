package controllers

import models.Recipe
import persistence.Serializer

class RecipeAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var recipes = ArrayList<Recipe>()

    fun add(recipe: Recipe): Boolean{
        return recipes.add(recipe)
    }

    fun listAllRecipes(): String =
        if  (recipes.isEmpty()) "No recipes stored"
        else formatListString(recipes)


        fun listRecipesNotInBook(): String =
            if  (numberOfRecipesNotInBook() == 0)  "No unsaved recipes"
            else formatListString(recipes.filter { note -> !note.recipeInBook})


    fun listRecipesInBook(): String =
        if  (numberOfRecipesInBook() == 0) "No recipes in book"
        else formatListString(recipes.filter { recipe -> recipe.recipeInBook})


    fun listRecipesBySpecifiedRating(rating: Int): String =
        if (recipes.isEmpty()) "No recipes stored"
        else {
            val listOfRecipes = formatListString(recipes.filter{ recipe -> recipe.recipeRating == rating})
            if (listOfRecipes.equals("")) "No recipes with rating: $rating"
            else "${numberOfRecipesByRating(rating)} recipes with rating $rating: $listOfRecipes"
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
        return recipes.stream()
            .filter{recipe: Recipe -> recipe.recipeInBook}
            .count()
            .toInt()
    }

    fun numberOfRecipesNotInBook(): Int {
        return recipes.stream()
            .filter{recipe: Recipe -> !recipe.recipeInBook}
            .count()
            .toInt()
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
    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, recipes);
    }

    fun deleteRecipe(indexToDelete: Int): Recipe? {
        return if (isValidListIndex(indexToDelete, recipes)){
            recipes.removeAt(indexToDelete)
        }else null
    }

    fun updateRecipe(indexToUpdate: Int, recipe: Recipe?): Boolean {
        //find the recipe object by the index number
        val foundRecipe = findRecipe(indexToUpdate)

        //if the recipe exists, use the recipe details passed as parameters to update the found recipe in the ArrayList.
        if ((foundRecipe != null) && (recipe != null)) {
            foundRecipe.recipeName = recipe.recipeName
            foundRecipe.recipeCategory = recipe.recipeCategory
            foundRecipe.ingredients = recipe.ingredients
            foundRecipe.difficultyLevel = recipe.difficultyLevel
            foundRecipe.servingSize = recipe.servingSize
            foundRecipe.recipeRating = recipe.recipeRating
            return true
        }

        //if the recipe was not found, return false, indicating that the update was not successful
        return false
    }

    fun recipeInBook(indexToAdd: Int): Boolean {
        if (isValidIndex(indexToAdd)){
            val recipeToAdd = recipes[indexToAdd]
            if (!recipeToAdd.recipeInBook){
                recipeToAdd.recipeInBook = true
                return true
            }
        }
        return false
    }

    fun searchByTitle(searchString : String) =
        formatListString(
            recipes.filter { recipe -> recipe.recipeName.contains(searchString, ignoreCase = true) })

    private fun formatListString(recipesToFormat : List<Recipe>) : String =
        recipesToFormat
            .joinToString (separator = "\n") { recipe ->
                recipes.indexOf(recipe).toString() + ": " + recipe.toString() }

    @Throws(Exception::class)
    fun load() {
        recipes = serializer.read() as ArrayList<Recipe>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(recipes)
    }
}