package models

data class Recipe(var recipeName: String,
                  var recipeCategory: String,
                  var ingredients: String,
                  var difficultyLevel: Int,
                  var servingSize: Int,
                  var recipeRating: Int,
                  var recipeInBook: Boolean) {

}