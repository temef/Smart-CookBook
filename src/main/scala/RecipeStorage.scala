package SmartCookbook
package cookbook


import java.io.{BufferedWriter, FileNotFoundException, FileWriter, IOException}
import scala.collection.mutable.Buffer
import scala.swing.Dialog


class RecipeStorage(recipeStorage: Buffer[Recipe]) {

  def rStorage = recipeStorage

  def writeToData(targetData: String, targetStorage: RecipeStorage) = {

      try {
        val fileWriter = new FileWriter(targetData)
        val recipeWriter = new BufferedWriter(fileWriter)

        val recipeBuffer: Buffer[(String, Buffer[(String, String, String, String)], Buffer[(String)], String, String, String, String, String)] = {
          targetStorage.rStorage.map(recipe =>
            (recipe.getTitle, recipe.list.storage.map(ingr => (ingr.getName, ingr.getAmount.toString, ingr.getUnit, ingr.getDensity.toString)),
                                                               recipe.getGuide.split('.').toBuffer, recipe.getGluten.toString, recipe.getMilk.toString, recipe.getFish.toString,
                                                               recipe.getNut.toString, recipe.getNut.toString))
        }

        var counter = 0
        recipeWriter.write("recipes \n\n\n")
        try {
            for (x <- recipeBuffer) {
              recipeWriter.write("title:" + x._1 + "\n")
              recipeWriter.newLine()

            for (i <- x._2) {
              recipeWriter.write("ingredient: " + i._1 + ":" + i._2 + ":" + i._3 + ":" + i._4)
              recipeWriter.newLine()
            }
              recipeWriter.write("\n")
              recipeWriter.newLine()

            while (counter < x._3.length) {
              recipeWriter.write("guide: " + x._3(counter) + ".")
              recipeWriter.newLine()
              counter += 1
            }
              recipeWriter.write("\n")
              recipeWriter.newLine()

              recipeWriter.write("glutenFree:" + x._4)
              recipeWriter.newLine()

              recipeWriter.write("milkFree:" + x._5)
              recipeWriter.newLine()

              recipeWriter.write("fishFree:" + x._6)
              recipeWriter.newLine()

              recipeWriter.write("nutFree:" + x._7)
              recipeWriter.newLine()

              recipeWriter.write("vegetarian:" + x._8)
              recipeWriter.newLine()
              recipeWriter.write("\n###\n")
              counter = 0
             }
            } finally {
            recipeWriter.close()
          }
             } catch {
              case e: FileNotFoundException =>
                scala.Console.println("File not found")
              case e: IOException =>
                scala.Console.println("Writing finished with an error")
          }
  }

  def deleteRecipe(recipeTitle: String) = {
    var epic = false

      for (i <-  rStorage) {
        if (i != null) {
          if (i.getTitle == recipeTitle) {
                rStorage -= i
                epic = true
              }
            }
          }
        if(epic) Dialog.showMessage(SmartCookbook.recipesScroll,  recipeTitle + " deleted", "This is a message")
        else Dialog.showMessage(SmartCookbook.recipesScroll,  "Could not find " + recipeTitle, "This is a message")
      }

}
