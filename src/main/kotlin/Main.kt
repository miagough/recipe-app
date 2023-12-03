import controllers.RecipeAPI
import models.Ingredient
import models.Recipe
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}

// private val recipeAPI = RecipeAPI(XMLSerializer(File("recipe.xml")))
private val recipeAPI = RecipeAPI(JSONSerializer(File("recipe.json")))

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        """
         > -----------------------------------------
         > |              RECIPE APP               |
         > -----------------------------------------
         > | RECIPE MENU                           |
         > |   1) Add a recipe                     |
         > |   2) List all recipes                 |
         > |   3) Update a recipe                  |
         > |   4) Delete a recipe                  |
         > |   5) Recipe Book                      |
         > -----------------------------------------
         > | INGREDIENTS MENU                      |
         > |   6) Add ingredients to a recipe      |
         > |   7) Update ingredients in a recipe   |
         > |   8) Delete ingredients from a recipe |
         > -----------------------------------------
         > |   9) Search Recipes                   |
         > |  10) Search Ingredients               |
         > -----------------------------------------
         > |  20) Save Recipes                     |
         > |  21) Load Recipes                     |
         > |   0) Exit                             |
         > -----------------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addRecipe()
            2 -> listRecipes()
            3 -> updateRecipe()
            4 -> deleteRecipe()
            5 -> recipeInBook()
            6 -> addIngredientsToRecipe()
            7 -> updateIngredientsInRecipe()
            8 -> deleteIngredients()
            9 -> searchRecipes()
            10 -> searchIngredients()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addRecipe() {
    // logger.info { "addRecipe() function invoked" }
    val recipeName = readNextLine("Enter recipe name: ")
    val recipeCategory = readNextLine("Enter a category for your recipe: ")
    val difficultyLevel = readNextInt("Enter a difficulty level (1-easy, 2, 3, 4, 5-very difficult): ")
    val servingSize = readNextInt("Enter the serving size: ")
    val recipeRating = readNextInt("Rate this dish (1-not good, 2, 3, 4, 5-excellent): ")
    val isAdded = recipeAPI.add(Recipe(recipeName = recipeName, recipeCategory = recipeCategory, difficultyLevel = difficultyLevel, servingSize = servingSize, recipeRating = recipeRating))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listRecipes() {
    if (recipeAPI.numberOfRecipes() > 0) {
        val option = readNextInt(
            """
                  > -----------------------------------------
                  > |   1) View ALL recipes                 |
                  > |   2) View UNSTORED recipes            |
                  > |   3) View RECIPE BOOK                 |
                  > |   4) View recipes by DIFFICULTY LEVEL |
                  > |   5) View recipes by RATING           |
                  > -----------------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllRecipes()
            2 -> listRecipesNotInBook()
            3 -> listRecipesInBook()
            4 -> listRecipesBySpecifiedDifficultyLevel()
            5 -> listRecipesBySpecifiedRating()

            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No recipes stored")
    }
}
fun listAllRecipes() {
    println(recipeAPI.listAllRecipes())
}

fun listRecipesNotInBook() {
    println(recipeAPI.listRecipesNotInBook())
}

fun listRecipesInBook() {
    println(recipeAPI.listRecipesInBook())
}

fun listRecipesBySpecifiedRating() {
    println(recipeAPI.listRecipesBySpecifiedRating(readNextInt("Which rating would you like?: ")))
}

fun listRecipesBySpecifiedDifficultyLevel() {
    println(recipeAPI.listRecipesBySpecifiedDifficultyLevel(readNextInt("Which difficulty level would you like?: ")))
}

fun updateRecipe() {
    // logger.info { "updateRecipe() function invoked" }
    listRecipes()
    if (recipeAPI.numberOfRecipes() > 0) {
        // only ask the user to choose the recipe if recipes exist
        val id = readNextInt("Enter the id of the recipe to update: ")
        if (recipeAPI.findRecipe(id) != null) {
            val recipeName = readNextLine("Enter a title for the recipe: ")
            val recipeCategory = readNextLine("Enter a category for the recipe: ")
            val difficultyLevel = readNextInt("Enter the difficulty level (1-5): ")
            val servingSize = readNextInt("Enter the servingSize: ")
            val recipeRating = readNextInt("Enter the recipe rating(1-5): ")

            // pass the index of the recipe and the new recipe details to recipeAPI for updating and check for success.
            if (recipeAPI.updateRecipe(id, Recipe(0, recipeName, recipeCategory, difficultyLevel, servingSize, recipeRating, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no recipes for this index number")
        }
    }
}

fun deleteRecipe() {
    // logger.info { "deleteRecipe() function invoked" }
    listRecipes()
    if (recipeAPI.numberOfRecipes() > 0) {
        // only ask the user to choose the recipe to delete if recipes exist
        val indexToDelete = readNextInt("Enter the index of the recipe to delete: ")
        // pass the index of the recipe to RecipeAPI for deleting and check for success.
        val recipeToDelete = recipeAPI.deleteRecipe(indexToDelete)
        if (recipeToDelete != null) {
            println("Delete Successful! Deleted Recipe: ${recipeToDelete.recipeName}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun recipeInBook() {
    listRecipesInBook()
    if (recipeAPI.numberOfRecipesInBook() > 0) {
        // only ask the user to choose the recipe to add to book if unstored recipes exist
        val indexToAdd = readNextInt("Enter the index of the recipe to add to Recipe Book: ")
        // pass the index of the recipe to recipeAPI for adding to book and check for success.
        if (recipeAPI.recipeInBook(indexToAdd)) {
            println("Recipe Successfully added to Recipe Book!")
        } else {
            println("Recipe was NOT successfully added to Recipe Book")
        }
    }
}

private fun addIngredientsToRecipe() {
    val recipe: Recipe? = askUserToChooseRecipeNotInBook()
    if (recipe != null) {
        if (recipe.addIngredient(Ingredient(ingredients = readNextLine("\t Ingredients: ")))) {
            println("Add Successful!")
        } else {
            println("Add NOT Successful")
        }
    }
}

fun updateIngredientsInRecipe() {
    val recipe: Recipe? = askUserToChooseRecipeNotInBook()
    if (recipe != null) {
        val ingredient: Ingredient? = askUserToChooseIngredient(recipe)
        if (ingredient != null) {
            val newIngredients = readNextLine("Enter new ingredients: ")
            if (recipe.update(ingredient.ingredientId, Ingredient(ingredients = newIngredients))) {
                println("Ingredients updated")
            } else {
                println("Ingredients NOT updated")
            }
        } else {
            println("Invalid Ingredient Id")
        }
    }
}

fun deleteIngredients() {
    val recipe: Recipe? = askUserToChooseRecipeNotInBook()
    if (recipe != null) {
        val ingredient: Ingredient? = askUserToChooseIngredient(recipe)
        if (ingredient != null) {
            val isDeleted = recipe.delete(ingredient.ingredientId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

fun searchRecipes() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = recipeAPI.searchByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No recipes found")
    } else {
        println(searchResults)
    }
}

fun searchIngredients() {
    val searchIngredients = readNextLine("Enter the ingredients to search by: ")
    val searchResults = recipeAPI.searchIngredients(searchIngredients)
    if (searchResults.isEmpty()) {
        println("No ingredients found")
    } else {
        println(searchResults)
    }
}

private fun askUserToChooseRecipeNotInBook(): Recipe? {
    listRecipesNotInBook()
    if (recipeAPI.numberOfRecipesNotInBook() > 0) {
        val recipe = recipeAPI.findRecipe(readNextInt("\nEnter the id of the recipe: "))
        if (recipe != null) {
            if (recipe.recipeInBook) {
                println("recipe is NOT unstored, it is in Recipe Book")
            } else {
                return recipe // chosen recipe is unstored
            }
        } else {
            println("Recipe id is not valid")
        }
    }
    return null // selected recipe is in Recipe Book
}
private fun askUserToChooseIngredient(recipe: Recipe): Ingredient? {
    if (recipe.numberOfIngredients() > 0) {
        print(recipe.listIngredients())
        return recipe.findOne(readNextInt("\nEnter the id of the recipe: "))
    } else {
        println("No ingredients for chosen recipe")
        return null
    }
}

fun save() {
    try {
        recipeAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        recipeAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
    println("Exiting...Goodbye")
    exit(0)
}
