package models

import utils.Utilities

data class Recipe(var recipeId: Int = 0,
                  var recipeName: String,
                  var recipeCategory: String,
                  var difficultyLevel: Int,
                  var servingSize: Int,
                  var recipeRating: Int,
                  var recipeInBook: Boolean = false,
                  var ingredients: MutableSet<Ingredient> = mutableSetOf()) {

    private var lastIngredientId = 0
    private fun getIngredientId() = lastIngredientId++

    fun addIngredient(ingredient: Ingredient) : Boolean {
        ingredient.ingredientId = getIngredientId()
        return ingredients.add(ingredient)
    }
    fun numberOfIngredients() = ingredients.size

    fun findOne(id: Int): Ingredient?{
        return ingredients.find{ ingredient -> ingredient.ingredientId == id }
    }

    fun delete(id: Int): Boolean {
        return ingredients.removeIf { ingredient -> ingredient.ingredientId == id}
    }

    fun update(id: Int, newIngredient : Ingredient): Boolean {
        val foundIngredient = findOne(id)

        //if the object exists, use the details passed in the newIngredient parameter to
        //update the found object in the Set
        if (foundIngredient != null){
            foundIngredient.ingredients = newIngredient.ingredients
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }

    fun listIngredients() =
        if (ingredients.isEmpty())  "\tNO INGREDIENTS ADDED"
        else  Utilities.formatSetString(ingredients)

    override fun toString(): String {
        val recipeInBook = if (recipeInBook) 'Y' else 'N'
        return "$recipeId: $recipeName, Category($recipeCategory), DifficultyLevel($difficultyLevel), ServingSize($servingSize), RecipeRating($recipeRating), RecipeBook($recipeInBook) \n${listIngredients()}"
    }

}