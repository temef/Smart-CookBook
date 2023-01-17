package SmartCookbook
package cookbook

import cookbook.SmartCookbook.recipess

import java.io.{BufferedReader, FileNotFoundException, FileReader, IOException}
import scala.collection.mutable.Buffer
import scala.swing.Dialog


class Recipe (title: String,
              ingredientList: IngredientStorage,
              guide: String,
              glutenFree: Boolean,
              milkFree:   Boolean,
              fishFree:   Boolean,
              nutFree:    Boolean,
              vegetarian: Boolean) {

  def getTitle = title
  def list = ingredientList
  def getGuide = guide
  def getGluten = glutenFree
  def getMilk = milkFree
  def getFish = fishFree
  def getNut = nutFree
  def getVege = vegetarian


  def readData(recipeData: String): Buffer[Recipe] = {

    val lineIn = new FileReader(recipeData)
    val lineReader = new BufferedReader(lineIn)


    var currentLine: String = null
    var recipeBuffer = Buffer[Recipe]()

    var takeTitle: String = null
    var takeIngredientList = Buffer[Ingredient]()
    var takeGuide: String = ""
    var takeGlutenFree: Boolean = false
    var takeMilkFree:   Boolean = false
    var takeFishFree:   Boolean = false
    var takeNutFree:    Boolean = false
    var takeVegetarian: Boolean = false

    var newName: String = null
    var newAmount: Double = 0
    var newUnit: String = null
    var newDensity: Double = 0

    try {
    while ({currentLine =  lineReader.readLine(); currentLine != null}) {



        if (currentLine.startsWith("title")) {
            takeTitle = currentLine.dropWhile(_ != ':').drop(1)
        }

        if (currentLine.startsWith("ingredient")) {
            newName = currentLine.dropWhile(_ != ':').drop(2).takeWhile(_ != ':')
            newAmount = currentLine.dropWhile(_ != ':').drop(2).dropWhile(_ != ':').drop(1).takeWhile(_ != ':').toDouble
            newUnit = currentLine.dropWhile(_ != ':').drop(2).dropWhile(_ != ':').drop(1).dropWhile(_ != ':').drop(1).takeWhile(_ != ':')
            newDensity = currentLine.dropWhile(_ != ':').drop(2).dropWhile(_ != ':').drop(1).dropWhile(_ != ':').drop(1).dropWhile(_ != ':').drop(1).toDouble
          takeIngredientList += new Ingredient(newName, newAmount, newUnit, newDensity)
        }

        if (currentLine.startsWith("guide")) {
            var stringLine = currentLine.dropWhile(_ != ':').drop(1)
            takeGuide = takeGuide + stringLine
        }

        if (currentLine.startsWith("glutenFree")) {
            val value = currentLine.dropWhile(_ != ':').drop(1)
            if (value == "true") takeGlutenFree = true
        }

        if (currentLine.startsWith("milkFree")) {
            val value = currentLine.dropWhile(_ != ':').drop(1)
            if (value == "true") takeMilkFree = true
        }

        if (currentLine.startsWith("fishFree")) {
            val value = currentLine.dropWhile(_ != ':').drop(1)
            if (value == "true") takeFishFree = true
        }

        if (currentLine.startsWith("nutFree")) {
            val value = currentLine.dropWhile(_ != ':').drop(1)
            if (value == "true") takeNutFree = true
        }

        if (currentLine.startsWith("vegetarian")) {
            val value = currentLine.dropWhile(_ != ':').drop(1)
            if (value == "true") takeVegetarian = true
        }

        if (currentLine.startsWith("#")) {
          recipeBuffer += new Recipe(takeTitle, new IngredientStorage(takeIngredientList), takeGuide, takeGlutenFree, takeMilkFree, takeFishFree, takeNutFree, takeVegetarian)
          takeIngredientList = Buffer[Ingredient]()
          takeGuide = ""
        }
    }

      try {
      recipeBuffer
        } finally {
        lineIn.close()
        lineReader.close()
      }
      } catch {
      case e:
        FileNotFoundException => scala.Console.println("File not found")
        recipeBuffer
      case r: IOException => scala.Console.println("reading finished with an error")
        recipeBuffer
      case s: ExceptionInInitializerError =>  scala.Console.println("Empty string found")
        recipeBuffer
      case t: NumberFormatException => scala.Console.println("Empty string found")
        recipeBuffer
    }
  }

  def missing(recipeList: IngredientStorage, ingredientStorage: IngredientStorage): Buffer[Ingredient] = {
    val missingBuffer = Buffer[Ingredient]()
    recipeList.storage.map(ingredient => if (!ingredientStorage.storage.map(i => i.getName).contains(ingredient.getName))  missingBuffer += ingredient)
    missingBuffer
  }

  def done(recipe: Recipe, ingredientStorage: IngredientStorage) = {
    val converter = new UnitConverter
    val missings = missing(recipe.list, ingredientStorage)
    val amountBuffer = Buffer[Double]()

    if(missings.isEmpty) {
      ingredientStorage.storage.map(x => recipe.list.storage.map(y => if(x.getName == y.getName && x.getUnit == y.getUnit) amountBuffer += (x.getAmount - y.getAmount)
      else if(x.getName == y.getName) amountBuffer += (x.getAmount - converter.calculate(x, y))))
    }
    println(amountBuffer.map(y => y))

    if(missings.isEmpty && !amountBuffer.exists(_ < 0)) {
      ingredientStorage.storage.map(x => if(x != null) recipe.list.storage.map(y => if(x.getName == y.getName && x.getUnit == y.getUnit && y != null) ingredientStorage.delete(y.getName, y.getAmount)
                                                                      else if(x.getName == y.getName && y != null) ingredientStorage.delete(y.getName, converter.calculate(x, y))))
    }
    else Dialog.showMessage(recipess, "You do not have enough of these ingredients: " + missing(recipe.list, ingredientStorage).map(x => x.getName + ", ").mkString , "Error!")
  }

}
