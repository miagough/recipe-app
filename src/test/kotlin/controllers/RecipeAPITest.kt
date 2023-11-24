package controllers


import models.Recipe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class RecipeAPITest {

    private var americanPancakes: Recipe? = null
    private var overnightOats: Recipe? = null
    private var salmonSalad: Recipe? = null
    private var cauliflowerSoup: Recipe? = null
    private var lasagne: Recipe? = null
    private var pastaBake: Recipe? = null
    private var populatedRecipes: RecipeAPI? = RecipeAPI()
    private var emptyRecipes: RecipeAPI? = RecipeAPI()

    @BeforeEach
    fun setup(){
        americanPancakes = Recipe("American -style Pancakes","Breakfast","flour,sugar,milk,eggs,butter",1,4,5, false)
        overnightOats = Recipe("Overnight Oats","Breakfast","oats,yoghurt,berries,honey,cinnamon",1,1,4, false)
        salmonSalad = Recipe("Salmon Salad","Lunch","salmon,potatoes,salad leaves",2,2,5, false)
        cauliflowerSoup = Recipe("Cauliflower Soup","Lunch","cauliflower,cream,garlic,onion,celery",3,6,4, false)
        lasagne = Recipe("Lasagne","Dinner","mince meat,milk,cheese,pasta sheets,tomato sauce",2,4,4, false)
        pastaBake = Recipe("Pasta Bake","Dinner","pasta,chicken,cheese",1,6,5, false)

        //adding 6 Recipes to recipe api
        populatedRecipes!!.add(americanPancakes!!)
        populatedRecipes!!.add(overnightOats!!)
        populatedRecipes!!.add(salmonSalad!!)
        populatedRecipes!!.add(cauliflowerSoup!!)
        populatedRecipes!!.add(lasagne!!)
        populatedRecipes!!.add(pastaBake!!)
    }

    @AfterEach
    fun tearDown(){
        americanPancakes = null
        overnightOats = null
        salmonSalad = null
        cauliflowerSoup = null
        lasagne = null
        pastaBake = null
        populatedRecipes = null
        emptyRecipes = null
    }

    @Nested
    inner class AddRecipes {
        @Test
        fun `adding a Recipe to a populated list adds to ArrayList`() {
            val newRecipe =
                Recipe("American -style Pancakes", "Breakfast", "flour,sugar,eggs,butter,milk", 1, 4, 5, false)
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            assertTrue(populatedRecipes!!.add(newRecipe))
            assertEquals(7, populatedRecipes!!.numberOfRecipes())
            assertEquals(newRecipe, populatedRecipes!!.findRecipe(populatedRecipes!!.numberOfRecipes() - 1))
        }

        @Test
        fun `adding a Recipe to an empty list adds to ArrayList`() {
            val newRecipe =
                Recipe("American -style Pancakes", "Breakfast", "flour,sugar,eggs,butter,milk", 1, 4, 5, false)
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            assertTrue(emptyRecipes!!.add(newRecipe))
            assertEquals(1, emptyRecipes!!.numberOfRecipes())
            assertEquals(newRecipe, emptyRecipes!!.findRecipe(emptyRecipes!!.numberOfRecipes() - 1))
        }

        @Test
        fun `listAllRecipes returns No Recipes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            assertTrue(emptyRecipes!!.listAllRecipes().lowercase().contains("no recipes"))
        }

        @Test
        fun `listAllRecipes returns Recipes when ArrayList has notes stored`() {
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            val recipesString = populatedRecipes!!.listAllRecipes().lowercase()
            assertFalse(recipesString.contains("American -style Pancakes"))
            assertFalse(recipesString.contains("Overnight Oats"))
            assertFalse(recipesString.contains("Salmon Salad"))
            assertFalse(recipesString.contains("Cauliflower Soup"))
            assertFalse(recipesString.contains("Lasagne"))
            assertFalse(recipesString.contains("Pasta Bake"))
        }
    }
}
