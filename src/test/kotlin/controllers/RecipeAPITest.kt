package controllers


import models.Recipe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
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
    fun setup() {
        americanPancakes =
            Recipe("American -style Pancakes", "Breakfast", "flour,sugar,milk,eggs,butter", 1, 4, 5, false)
        overnightOats = Recipe("Overnight Oats", "Breakfast", "oats,yoghurt,berries,honey,cinnamon", 1, 1, 4, false)
        salmonSalad = Recipe("Salmon Salad", "Lunch", "salmon,potatoes,salad leaves", 2, 2, 5, true)
        cauliflowerSoup = Recipe("Cauliflower Soup", "Lunch", "cauliflower,cream,garlic,onion,celery", 3, 6, 1, false)
        lasagne = Recipe("Lasagne", "Dinner", "mince meat,milk,cheese,pasta sheets,tomato sauce", 2, 4, 4, true)
        pastaBake = Recipe("Pasta Bake", "Dinner", "pasta,chicken,cheese", 1, 6, 5, false)

        //adding 6 Recipes to recipe api
        populatedRecipes!!.add(americanPancakes!!)
        populatedRecipes!!.add(overnightOats!!)
        populatedRecipes!!.add(salmonSalad!!)
        populatedRecipes!!.add(cauliflowerSoup!!)
        populatedRecipes!!.add(lasagne!!)
        populatedRecipes!!.add(pastaBake!!)
    }

    @AfterEach
    fun tearDown() {
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
    }

    @Nested
    inner class ListRecipes {
        @Test
        fun `listAllRecipes returns No Recipes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            assertTrue(emptyRecipes!!.listAllRecipes().lowercase().contains("no recipes"))
        }

        @Test
        fun `listAllRecipes returns Recipes when ArrayList has recipes stored`() {
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            val recipesString = populatedRecipes!!.listAllRecipes().lowercase()
            assertTrue(recipesString.contains("american -style pancakes"))
            assertTrue(recipesString.contains("overnight oats"))
            assertTrue(recipesString.contains("salmon salad"))
            assertTrue(recipesString.contains("cauliflower soup"))
            assertTrue(recipesString.contains("lasagne"))
            assertTrue(recipesString.contains("pasta bake"))
        }


    @Test
    fun `listRecipesNotInBook returns no unsaved recipes when ArrayList is empty`() {
        assertEquals(0, emptyRecipes!!.numberOfRecipesNotInBook())
        assertTrue(
            emptyRecipes!!.listRecipesNotInBook().lowercase().contains("no unsaved recipes")
        )
    }

    @Test
    fun `listRecipesNotInBook returns unsaved recipes when ArrayList has unsaved notes`() {
        assertEquals(4, populatedRecipes!!.numberOfRecipesNotInBook())
        val activeRecipesString = populatedRecipes!!.listRecipesNotInBook().lowercase()
        assertTrue(activeRecipesString.contains("american -style pancakes"))
        assertTrue(activeRecipesString.contains("overnight oats"))
        assertFalse(activeRecipesString.contains("salmon salad"))
        assertTrue(activeRecipesString.contains("cauliflower soup"))
        assertFalse(activeRecipesString.contains("lasagne"))
        assertTrue(activeRecipesString.contains("pasta bake"))
    }

    @Test
    fun `listRecipesInBook returns no recipes in book when ArrayList is empty`() {
        assertEquals(0, emptyRecipes!!.numberOfRecipesInBook())
        assertTrue(
            emptyRecipes!!.listRecipesInBook().lowercase().contains("no recipes in book")
        )
    }

    @Test
    fun `listRecipesInBook returns recipes in book when ArrayList has recipes store in book`() {
        assertEquals(2, populatedRecipes!!.numberOfRecipesInBook())
        val archivedRecipesString = populatedRecipes!!.listRecipesInBook().lowercase(Locale.getDefault())
        assertFalse(archivedRecipesString.contains("american -style pancakes"))
        assertFalse(archivedRecipesString.contains("overnight oats"))
        assertTrue(archivedRecipesString.contains("salmon salad"))
        assertFalse(archivedRecipesString.contains("cauliflower soup"))
        assertTrue(archivedRecipesString.contains("lasagne"))
        assertFalse(archivedRecipesString.contains("pasta bake"))
    }

    @Test
    fun `listRecipesBySpecifiedRating returns No Recipes when ArrayList is empty`() {
        assertEquals(0, emptyRecipes!!.numberOfRecipes())
        assertTrue(
            emptyRecipes!!.listRecipesBySpecifiedRating(1).lowercase().contains("no recipes")
        )
    }

    @Test
    fun `listRecipesBySpecifiedRating returns no recipes when no recipes with that rating exist`() {
        assertEquals(6, populatedRecipes!!.numberOfRecipes())
        val rating2String = populatedRecipes!!.listRecipesBySpecifiedRating(2).lowercase()
        assertTrue(rating2String.contains("no recipes"))
        assertTrue(rating2String.contains("2"))
    }

    @Test
    fun `listRecipesBySpecifiedRating returns all recipes that match that rating when recipes with that rating exist`() {
        assertEquals(6, populatedRecipes!!.numberOfRecipes())
        val rating1String = populatedRecipes!!.listRecipesBySpecifiedRating(1).lowercase()
        assertTrue(rating1String.contains("1 recipe"))
        assertTrue(rating1String.contains("rating 1"))
        assertFalse(rating1String.contains("american -style pancakes"))
        assertFalse(rating1String.contains("overnight oats"))
        assertFalse(rating1String.contains("salmon salad"))
        assertTrue(rating1String.contains("cauliflower soup"))
        assertFalse(rating1String.contains("lasagne"))
        assertFalse(rating1String.contains("pasta bake"))


        val rating4String = populatedRecipes!!.listRecipesBySpecifiedRating(4).lowercase(Locale.getDefault())
        assertTrue(rating4String.contains("2 recipe"))
        assertTrue(rating4String.contains("rating 4"))
        assertFalse(rating4String.contains("american -style pancakes"))
        assertTrue(rating4String.contains("overnight oats"))
        assertFalse(rating4String.contains("salmon salad"))
        assertFalse(rating4String.contains("cauliflower soup"))
        assertTrue(rating4String.contains("lasagne"))
        assertFalse(rating4String.contains("pasta bake"))
    }
        @Test
        fun `listRecipesBySpecifiedDifficultyLevel returns No Recipes when ArrayList is empty`() {
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            assertTrue(
                emptyRecipes!!.listRecipesBySpecifiedDifficultyLevel(1).lowercase().contains("no recipes")
            )
        }
        @Test
        fun `listRecipesBySpecifiedDifficultyLevel returns no recipes when no recipes with that difficulty level exist`() {
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            val difficultyLevel2String = populatedRecipes!!.listRecipesBySpecifiedDifficultyLevel(5).lowercase()
            assertTrue(difficultyLevel2String.contains("no recipes"))
            assertTrue(difficultyLevel2String.contains("5"))
        }

        @Test
        fun `listRecipesBySpecifiedDifficultyLevel returns all recipes that match that difficulty level when recipes with that difficulty level exist`() {
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            val difficultyLevel1String = populatedRecipes!!.listRecipesBySpecifiedDifficultyLevel(1).lowercase()
            assertTrue(difficultyLevel1String.contains("american -style pancakes"))
            assertTrue(difficultyLevel1String.contains("overnight oats"))
            assertFalse(difficultyLevel1String.contains("salmon salad"))
            assertFalse(difficultyLevel1String.contains("cauliflower soup"))
            assertFalse(difficultyLevel1String.contains("lasagne"))
            assertTrue(difficultyLevel1String.contains("pasta bake"))


            val difficultyLevel2String = populatedRecipes!!.listRecipesBySpecifiedDifficultyLevel(2).lowercase(Locale.getDefault())
            assertFalse(difficultyLevel2String.contains("american -style pancakes"))
            assertFalse(difficultyLevel2String.contains("overnight oats"))
            assertTrue(difficultyLevel2String.contains("salmon salad"))
            assertFalse(difficultyLevel2String.contains("cauliflower soup"))
            assertTrue(difficultyLevel2String.contains("lasagne"))
            assertFalse(difficultyLevel2String.contains("pasta bake"))
        }
}
    @Nested
    inner class DeleteRecipes{
        @Test
        fun `deleting a Recipe that does not exist, returns null`(){
            assertNull(emptyRecipes!!.deleteRecipe(0))
            assertNull(populatedRecipes!!.deleteRecipe(-1))
            assertNull(populatedRecipes!!.deleteRecipe(6))
        }
        @Test
        fun `deleting a recipe that exists delete and returns deleted object`(){
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            assertEquals(overnightOats,populatedRecipes!!.deleteRecipe(1))
            assertEquals(5, populatedRecipes!!.numberOfRecipes())
            assertEquals(pastaBake, populatedRecipes!!.deleteRecipe(4))
            assertEquals(4, populatedRecipes!!.numberOfRecipes())

        }
    }

}
