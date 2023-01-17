package SmartCookbook
package cookbook

import scala.collection.mutable.Buffer
import java.io.{BufferedWriter, FileNotFoundException, FileWriter, IOException}


import scala.swing.Dialog

class IngredientStorage(ingredientStorage: Buffer[Ingredient]) {


  def storage = ingredientStorage

  def add(ingredient: Ingredient): Buffer[Ingredient] = {
       println(ingredient.getName + " added to the storage")
       Dialog.showMessage(SmartCookbook.newRecipeFrame,   ingredient.getName + " added to the storage", "This is a message")
       storage += ingredient
    }


  def delete(name: String, amount: Double) = {

    for (i <- storage) {

      if (i != null) {

      if (i.getName == name && i.getAmount > amount) {
        i.getAmount = i.getAmount - amount
        Dialog.showMessage(SmartCookbook.recipess, "Deleted " + amount + " " + i.getUnit + " from " + name, "This is a message")
        println("Deleted " + amount + i.getUnit + " from " + name)
      }
      else if (i.getName == name && i.getAmount < amount) {
        Dialog.showMessage(SmartCookbook.recipess, "That amount cannot be deleted from " + i.getName, "This is a message")
        println("That amount cannot be deleted from " + i.getName)
      }
      else if (i.getName == name && i.getAmount == amount) {
        storage -= i
        println("All " + name + " deleted")
      }
      }
    }
  }


  def writeToData(targetData: String, ingredientStorage: IngredientStorage) = {

      try {
        val fileWriter = new FileWriter(targetData)
        val ingredientWriter = new BufferedWriter(fileWriter)

        val storageBuffer: Buffer[(String, String, String, String)] = {
          ingredientStorage.storage.map(x => (x.getName, x.getAmount.toString, x.getUnit, x.getDensity.toString))
        }

        try {
          for (x <- storageBuffer) {
            ingredientWriter.write(x._1 + ":" + x._2 + ":" + x._3 + ":" + x._4)
            ingredientWriter.newLine()
          }
          } finally {
            ingredientWriter.close()
          }
          } catch {
            case e: FileNotFoundException =>
              scala.Console.println("File not found")
            case e: IOException =>
              scala.Console.println("Writing finished with an error")
          }
  }

}
