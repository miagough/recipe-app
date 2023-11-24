package models

data class Recipe(val recipeName: String,
                  val recipeCategory: String,
                  val ingredients: String,
                  val difficultyLevel: Int,
                  val servingSize: Int,
                  val recipeRating: Int,
                  val recipeInBook: Boolean) {

}