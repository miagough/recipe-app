package controllers


import models.Recipe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import persistence.JSONSerializer
import java.io.File
import java.util.*
import kotlin.test.assertEquals


class RecipeAPITest {

    private var americanPancakes: Recipe? = null
    private var overnightOats: Recipe? = null
    private var salmonSalad: Recipe? = null
    private var cauliflowerSoup: Recipe? = null
    private var lasagne: Recipe? = null
    private var pastaBake: Recipe? = null
    private var populatedRecipes: RecipeAPI? = RecipeAPI(XMLSerializer(File("recipe.xml")))
    private var emptyRecipes: RecipeAPI? = RecipeAPI(XMLSerializer(File("recipe.xml")))

    @BeforeEach
    fun setup() {
        americanPancakes = Recipe(0,"American -style Pancakes", "Breakfast", 1, 4, 5, false)
        overnightOats = Recipe(1,"Overnight Oats", "Breakfast", 1, 1, 4, false)
        salmonSalad = Recipe(2,"Salmon Salad", "Lunch",2, 2, 5, true)
        cauliflowerSoup = Recipe(3,"Cauliflower Soup", "Lunch",  3, 6, 1, false)
        lasagne = Recipe(4,"Lasagne", "Dinner", 2, 4, 4, true)
        pastaBake = Recipe(5,"Pasta Bake", "Dinner",  1, 6, 5, false)

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
                Recipe(0,"American -style Pancakes", "Breakfast",  1, 4, 5, false)
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            assertTrue(populatedRecipes!!.add(newRecipe))
            assertEquals(7, populatedRecipes!!.numberOfRecipes())
            assertEquals(newRecipe, populatedRecipes!!.findRecipe(populatedRecipes!!.numberOfRecipes() - 1))
        }

        @Test
        fun `adding a Recipe to an empty list adds to ArrayList`() {
            val newRecipe =
                Recipe(0,"American -style Pancakes", "Breakfast",  1, 4, 5, false)
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
    fun `listRecipesNotInBook returns no recipes in book when ArrayList is empty`() {
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

    @Nested
    inner class UpdateNotes {
        @Test
        fun `updating a recipe that does not exist returns false`(){
            assertFalse(populatedRecipes!!.updateRecipe(7, Recipe(7,"Updating Recipe", "breakfast",  1,1,3,false)))
            assertFalse(populatedRecipes!!.updateRecipe(-1, Recipe(-1,"Updating Recipe", "breakfast", 1,1,3,false)))
            assertFalse(emptyRecipes!!.updateRecipe(0, Recipe(0,"Updating Recipe", "breakfast", 1, 1,3,false)))
        }

        @Test
        fun `updating a recipe that exists returns true and updates`() {
            //check recipe 2 exists and check the contents
            assertEquals(overnightOats, populatedRecipes!!.findRecipe(1))
            assertEquals("Overnight Oats", populatedRecipes!!.findRecipe(1)!!.recipeName)
            assertEquals("Breakfast", populatedRecipes!!.findRecipe(1)!!.recipeCategory)
            assertEquals(1, populatedRecipes!!.findRecipe(1)!!.difficultyLevel)
            assertEquals(1, populatedRecipes!!.findRecipe(1)!!.servingSize)
            assertEquals(4, populatedRecipes!!.findRecipe(1)!!.recipeRating)

            //update note 1 with new information and ensure contents updated successfully
            assertTrue(populatedRecipes!!.updateRecipe(1, Recipe(1,"Updating Recipe", "Lunch",  2,2,4,false)))
            assertEquals("Updating Recipe", populatedRecipes!!.findRecipe(1)!!.recipeName)
            assertEquals("Lunch", populatedRecipes!!.findRecipe(1)!!.recipeCategory)
            assertEquals(2, populatedRecipes!!.findRecipe(1)!!.difficultyLevel)
            assertEquals(2, populatedRecipes!!.findRecipe(1)!!.servingSize)
            assertEquals(4, populatedRecipes!!.findRecipe(1)!!.recipeRating)
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty recipe.XML file.
            val storingRecipes = RecipeAPI(XMLSerializer(File("recipes.xml")))
            storingRecipes.store()

            //Loading the empty recipes.xml file into a new object
            val loadedRecipes = RecipeAPI(XMLSerializer(File("recipes.xml")))
            loadedRecipes.load()

            //Comparing the source of the recipes (storingRecipes) with the XML loaded recipes (loadedRecipes)
            assertEquals(0, storingRecipes.numberOfRecipes())
            assertEquals(0, loadedRecipes.numberOfRecipes())
            assertEquals(storingRecipes.numberOfRecipes(), loadedRecipes.numberOfRecipes())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't loose data`() {
            // Storing 3 recipes to the recipes.XML file.
            val storingRecipes = RecipeAPI(XMLSerializer(File("recipes.xml")))
            storingRecipes.add(americanPancakes!!)
            storingRecipes.add(overnightOats!!)
            storingRecipes.add(salmonSalad!!)
            storingRecipes.store()

            //Loading recipes.xml into a different collection
            val loadedRecipes = RecipeAPI(XMLSerializer(File("recipes.xml")))
            loadedRecipes.load()

            //Comparing the source of the recipes (storingRecipes) with the XML loaded recipes (loadedRecipes)
            assertEquals(3, storingRecipes.numberOfRecipes())
            assertEquals(3, loadedRecipes.numberOfRecipes())
            assertEquals(storingRecipes.numberOfRecipes(), loadedRecipes.numberOfRecipes())
            assertEquals(storingRecipes.findRecipe(0), loadedRecipes.findRecipe(0))
            assertEquals(storingRecipes.findRecipe(1), loadedRecipes.findRecipe(1))
            assertEquals(storingRecipes.findRecipe(2), loadedRecipes.findRecipe(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty recipes.json file.
            val storingRecipes = RecipeAPI(JSONSerializer(File("recipes.json")))
            storingRecipes.store()

            //Loading the empty recipes.json file into a new object
            val loadedRecipes = RecipeAPI(JSONSerializer(File("recipes.json")))
            loadedRecipes.load()

            //Comparing the source of the recipes (storingRecipes) with the json loaded recipes (loadedRecipes)
            assertEquals(0, storingRecipes.numberOfRecipes())
            assertEquals(0, loadedRecipes.numberOfRecipes())
            assertEquals(storingRecipes.numberOfRecipes(), loadedRecipes.numberOfRecipes())
        }

        @Test
        fun `saving and loading a loaded collection in JSON doesn't loose data`() {
            // Storing 3 recipes to the recipes.json file.
            val storingRecipes = RecipeAPI(JSONSerializer(File("recipe.json")))
            storingRecipes.add(americanPancakes!!)
            storingRecipes.add(overnightOats!!)
            storingRecipes.add(salmonSalad!!)
            storingRecipes.store()

            //Loading recipes.json into a different collection
            val loadedRecipes = RecipeAPI(JSONSerializer(File("recipe.json")))
            loadedRecipes.load()

            //Comparing the source of the recipes (storingRecipes) with the json loaded recipes (loadedRecipes)
            assertEquals(3, storingRecipes.numberOfRecipes())
            assertEquals(3, loadedRecipes.numberOfRecipes())
            assertEquals(storingRecipes.numberOfRecipes(), loadedRecipes.numberOfRecipes())
            assertEquals(storingRecipes.findRecipe(0), loadedRecipes.findRecipe(0))
            assertEquals(storingRecipes.findRecipe(1), loadedRecipes.findRecipe(1))
            assertEquals(storingRecipes.findRecipe(2), loadedRecipes.findRecipe(2))
        }
    }

    @Nested
    inner class RecipesInBook {
        @Test
        fun `adding a recipe to book that does not exist returns false`(){
            assertFalse(populatedRecipes!!.recipeInBook(7))
            assertFalse(populatedRecipes!!.recipeInBook(-1))
            assertFalse(emptyRecipes!!.recipeInBook(0))
        }

        @Test
        fun `adding an already added recipe to book returns false`(){
            assertTrue(populatedRecipes!!.findRecipe(2)!!.recipeInBook)
            assertFalse(populatedRecipes!!.recipeInBook(2))
        }

        @Test
        fun `adding an unstored recipe that already exists returns true and archives`() {
            assertFalse(populatedRecipes!!.findRecipe(1)!!.recipeInBook)
            assertTrue(populatedRecipes!!.recipeInBook(1))
            assertTrue(populatedRecipes!!.findRecipe(1)!!.recipeInBook)
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfRecipesCalculatedCorrectly() {
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
        }

        @Test
        fun numberOfRecipesInBookCalculatedCorrectly() {
            assertEquals(2, populatedRecipes!!.numberOfRecipesInBook())
            assertEquals(0, emptyRecipes!!.numberOfRecipesInBook())
        }

        @Test
        fun numberOfRecipesNotInBookCalculatedCorrectly() {
            assertEquals(4, populatedRecipes!!.numberOfRecipesNotInBook())
            assertEquals(0, emptyRecipes!!.numberOfRecipesNotInBook())
        }

        @Test
        fun numberOfRecipesByRatingCalculatedCorrectly() {
            assertEquals(1, populatedRecipes!!.numberOfRecipesByRating(1))
            assertEquals(0, populatedRecipes!!.numberOfRecipesByRating(2))
            assertEquals(0, populatedRecipes!!.numberOfRecipesByRating(3))
            assertEquals(2, populatedRecipes!!.numberOfRecipesByRating(4))
            assertEquals(3, populatedRecipes!!.numberOfRecipesByRating(5))
            assertEquals(0, emptyRecipes!!.numberOfRecipesByRating(1))
        }
    }

    @Nested
    inner class SearchMethods {

        @Test
        fun `search recipes by title returns no recipes when no recipes with that title exist`() {
            //Searching a populated collection for a title that doesn't exist.
            assertEquals(6, populatedRecipes!!.numberOfRecipes())
            val searchResults = populatedRecipes!!.searchByTitle("no results expected")
            assertTrue(searchResults.isEmpty())

            //Searching an empty collection
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            assertTrue(emptyRecipes!!.searchByTitle("").isEmpty())
        }

        @Test
        fun `search recipes by title returns recipes when recipes with that title exist`() {
            assertEquals(6, populatedRecipes!!.numberOfRecipes())

            //Searching a populated collection for a full title that exists (case matches exactly)
            var searchResults = populatedRecipes!!.searchByTitle("American -style Pancakes")
            assertTrue(searchResults.contains("American -style Pancakes"))
            assertFalse(searchResults.contains("Overnight Oats"))

            //Searching a populated collection for a partial title that exists (case matches exactly)
            searchResults = populatedRecipes!!.searchByTitle("g")
            assertTrue(searchResults.contains("Overnight Oats"))
            assertTrue(searchResults.contains("Lasagne"))
            assertFalse(searchResults.contains("Pasta Bake"))

            //Searching a populated collection for a partial title that exists (case doesn't match)
            searchResults = populatedRecipes!!.searchByTitle("G")
            assertTrue(searchResults.contains("Overnight Oats"))
            assertTrue(searchResults.contains("Lasagne"))
            assertFalse(searchResults.contains("Pasta Bake"))
        }
    }

}
