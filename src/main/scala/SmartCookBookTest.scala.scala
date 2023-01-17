
package object cookbook

import scala.collection.mutable.Buffer
import SmartCookbook.cookbook.{Ingredient, IngredientStorage, Recipe}

object SmartCookBookTest extends App {

  val inputData = "SmartCookbook/data/ingredients.txt"
  val inputData2 = "SmartCookbook/data/recipes"
  val testIngredient = new Ingredient("beef", 400.0, "g", 1.0)
  val testIngredient2 = new Ingredient("potato",5.0, "pcs", 1.0)
  val storage = new IngredientStorage(Buffer[Ingredient](testIngredient2, testIngredient))

  val testRecipe = new Recipe("soppa", storage, "Ei muutakun keitolle", false, false, false, false, false)

  def loadRecipes(recipeData: String): Buffer[Recipe] = testRecipe.readData(recipeData)

  def loadIngredients(ingredientData: String): Buffer[Ingredient] = testIngredient.readData(ingredientData)


// Indeed, this class proved futile and I used it to test certain components before the interface was complete.

}