import controllers.RecipeAPI
import models.Recipe
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit




private val logger = KotlinLogging.logger {}
private val recipeAPI = RecipeAPI()

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
         > -----------------------------
         > |        RECIPE APP         |
         > -----------------------------
         > | RECIPE MENU               |
         > |   1) Add a recipe         |
         > |   2) List all recipes     |
         > |   3) Update a recipe      |
         > |   4) Delete a recipe      |
         > -----------------------------
         > |   0) Exit                 |
         > -----------------------------
         > ==>> """.trimMargin(">"))


}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addRecipe()
            2  -> listRecipes()
            3  -> updateRecipe()
            4  -> deleteRecipe()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addRecipe() {
    //logger.info { "addRecipe() function invoked" }
    val recipeName = readNextLine("Enter recipe name: ")
    val recipeCategory = readNextLine("Enter a category for your recipe: ")
    val ingredients = readNextLine("Enter the ingredients needed: ")
    val difficultyLevel = readNextInt("Enter a difficulty level (1-easy, 2, 3, 4, 5-very difficult): ")
    val servingSize = readNextInt("Enter the serving size: ")
    val recipeRating = readNextInt("Rate this dish (1-not good, 2, 3, 4, 5-excellent): ")
    val isAdded = recipeAPI.add(Recipe(recipeName,recipeCategory,ingredients,difficultyLevel,servingSize,recipeRating, false))

    if (isAdded) {
        println("Added Successfully")
    }else {
        println("Add Failed")
    }
}

fun listRecipes() {
    //logger.info { "listRecipes() function invoked" }
    println(recipeAPI.listAllRecipes())
}

fun updateRecipe() {
    logger.info { "updateRecipe() function invoked" }
}

fun deleteRecipe() {
    logger.info { "deleteRecipe() function invoked" }
}

fun exitApp() {
    println("Exiting...Goodbye")
    exit(0)
}