import java.lang.System.exit
import java.util.*


val scanner = Scanner(System.`in`)


fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    print("""
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
    return scanner.nextInt()

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

fun addRecipe(){
    println("You chose Add Recipe")
}

fun listRecipes(){
    println("You chose List Recipes")
}

fun updateRecipe(){
    println("You chose Update Recipe")
}

fun deleteRecipe(){
    println("You chose Delete Recipe")
}

fun exitApp(){
    println("Exiting...Goodbye")
    exit(0)
}