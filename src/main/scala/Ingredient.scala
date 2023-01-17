package SmartCookbook
package cookbook

import scala.collection.mutable.Buffer
import java.io.{BufferedReader, FileNotFoundException, FileReader, IOException}


class Ingredient (name: String, amount: Double, unit: String, density: Double) {

  val getName = name
  var getAmount = amount
  val getUnit = unit
  val getDensity = density

  private val ingredientFile ="ingredients.txt"

  def readData(ingredientData: String): Buffer[Ingredient] = {

    val lineIn = new FileReader(ingredientData)
    val lineReader = new BufferedReader(lineIn)


    var currentLine: String = null
    var ingredientBuffer = Buffer[Ingredient]()

    var newName: String = null
    var newAmount: Double = 0
    var newUnit: String = null
    var newDensity: Double = 0
    var counter = 0

    try {
    while ({currentLine =  lineReader.readLine(); currentLine != null}) {

        while (counter < 4) {
          if (counter == 0) {
          newName = currentLine.takeWhile(_ != ':')
          }
          if (counter == 1) {
          newAmount = currentLine.takeWhile(_ != ':').toDouble
          }
          if (counter == 2) {
          newUnit = currentLine.takeWhile(_ != ':')
          }
          if (counter == 3) {
          newDensity = currentLine.takeWhile(_ != ':').toDouble
          }
          currentLine = currentLine.dropWhile(_ != ':').drop(1)
          counter += 1
        }
        counter = 0
      ingredientBuffer += new Ingredient(newName, newAmount, newUnit, newDensity)
      }
      val newStorage = new IngredientStorage(ingredientBuffer)
      try {
    ingredientBuffer

      } finally {
        lineIn.close()
        lineReader.close()
      }
  } catch {
      case e:
        FileNotFoundException => scala.Console.println("File not found")
        ingredientBuffer
      case r: IOException =>
        scala.Console.println("reading finished with an error")
        ingredientBuffer
    }
  }


}