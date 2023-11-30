import controllers.RecipeAPI
import models.Recipe
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit




private val logger = KotlinLogging.logger {}
//private val recipeAPI = RecipeAPI(XMLSerializer(File("recipe.xml")))
private val recipeAPI = RecipeAPI(JSONSerializer(File("recipe.json")))

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
         > |  20) Save Recipes
         > |  21) Load Recipes
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
            20 -> save()
            21 -> load()
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
    //logger.info { "updateRecipe() function invoked" }
    listRecipes()
    if (recipeAPI.numberOfRecipes()>0) {
        //only ask the user to choose the recipe if recipes exist
        val indexToUpdate = readNextInt("Enter the index of the recipe to update: ")
        if (recipeAPI.isValidIndex(indexToUpdate)) {
            val recipeName = readNextLine("Enter a title for the recipe: ")
            val recipeCategory = readNextLine("Enter a category for the recipe: ")
            val ingredients = readNextLine("Enter the ingredients needed for the recipe: ")
            val difficultyLevel = readNextInt("Enter the difficulty level (1-5): ")
            val servingSize = readNextInt("Enter the servingSize: ")
            val recipeRating = readNextInt("Enter the recipe rating(1-5): ")

            //pass the index of the recipe and the new recipe details to recipeAPI for updating and check for success.
            if (recipeAPI.updateRecipe(indexToUpdate, Recipe(recipeName, recipeCategory, ingredients, difficultyLevel, servingSize, recipeRating, false))){
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
    //logger.info { "deleteRecipe() function invoked" }
    listRecipes()
    if (recipeAPI.numberOfRecipes()>0){
        //only ask the user to choose the recipe to delete if recipes exist
        val indexToDelete = readNextInt("Enter the index of the recipe to delete: ")
        //pass the index of the recipe to RecipeAPI for deleting and check for success.
        val recipeToDelete = recipeAPI.deleteRecipe(indexToDelete)
        if (recipeToDelete != null){
            println("Delete Successful! Deleted Recipe: ${recipeToDelete.recipeName}")
        } else{
            println("Delete NOT Successful")
        }
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