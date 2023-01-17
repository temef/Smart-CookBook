package SmartCookbook
package cookbook

import cookbook.SmartCookbook.{ingredientDeleteUnit, ingredientInfo}

import scala.swing.Dialog


class UnitConverter  {

  def calculate(ingredient1: Ingredient, ingredient2: Ingredient): Double = {

    /**
     * This calculate method will convert all pre-defined units.
     * "Pieces" unit is only unit that cannot be converted.
     * The calculator uses a simple density calculation formula
     * There are comments inside the converter on how the conversion takes place.
     */

    var amount2: Double = ingredient2.getAmount

    if (ingredient1.getUnit != ingredient2.getUnit) {

        ingredient1.getUnit match {
          case  "dl"  =>  {
            ingredient2.getUnit match {
              case  "l"   =>   amount2 = ingredient2.getAmount * 10 // l to dl
              case  "ml"  =>   amount2 = ingredient2.getAmount * 1/100
              case  "tsp" =>   amount2 = ingredient2.getAmount * 5/100
              case "tbsp" =>   amount2 = ingredient2.getAmount * 15/100
              case  "g"   =>   amount2 = ingredient2.getAmount / 1000 / ingredient2.getDensity * 10 // g to kg then kg to l then l to dl
              case  "kg"  =>   amount2 = ingredient2.getAmount / ingredient2.getDensity * 10
              case  "mg"  =>   amount2 = ingredient2.getAmount / 1000000 / ingredient2.getDensity * 10 // mg to kg then kg to l then l to dl
              case  "pcs" =>   Dialog.showMessage(ingredientInfo, "Can't delete pieces from dl! Error occured with: " + ingredient1.getName  , "Error!")
            }
          }
          case  "l"   =>  {
            ingredient2.getUnit match {
              case  "dl"  =>   amount2 = ingredient2.getAmount * 1/10
              case  "ml"  =>   amount2 = ingredient2.getAmount * 1/1000
              case  "tsp" =>   amount2 = ingredient2.getAmount * 5/1000
              case "tbsp" =>   amount2 = ingredient2.getAmount * 15/1000
              case  "g"   =>   amount2 = ingredient2.getAmount / 1000 / ingredient2.getDensity // g to kg then kg to l
              case  "kg"  =>   amount2 = ingredient2.getAmount / ingredient2.getDensity
              case  "mg"  =>   amount2 = ingredient2.getAmount / 1000000 / ingredient2.getDensity // mg to kg then kg to l
              case  "pcs" =>   Dialog.showMessage(ingredientInfo, "Can't delete pieces from liter! Error occured with: " + ingredient1.getName  , "Error!")
            }
          }
          case  "ml"  => {
            ingredient2.getUnit match {
              case  "dl"  =>   amount2 = ingredient2.getAmount * 100
              case  "l"   =>   amount2 = ingredient2.getAmount * 1000
              case  "tsp" =>   amount2 = ingredient2.getAmount * 5
              case "tbsp" =>   amount2 = ingredient2.getAmount * 15
              case  "g"   =>   amount2 = ingredient2.getAmount / 1000 / ingredient2.getDensity * 1000 //g to kg, kg to l, l to ml
              case  "kg"  =>   amount2 = ingredient2.getAmount / ingredient2.getDensity * 1000
              case  "mg"  =>   amount2 = ingredient2.getAmount / 1000000 / ingredient2.getDensity * 1000
              case  "pcs" =>   Dialog.showMessage(ingredientInfo, "Can't delete pieces from ml! Error occured with: " + ingredient1.getName  , "Error!")
            }
          }
          case  "tsp" =>  {
            ingredient2.getUnit match {
              case  "dl"  =>   amount2 = ingredient2.getAmount * 5/100
              case  "l"   =>   amount2 = ingredient2.getAmount * 5/1000
              case  "ml"  =>   amount2 = ingredient2.getAmount * 1/5
              case "tbsp" =>   amount2 = ingredient2.getAmount * 3
              case  "g"   =>   amount2 = ingredient2.getAmount / 1000 / ingredient2.getDensity * 5/1000
              case  "kg"  =>   amount2 = ingredient2.getAmount / ingredient2.getDensity * 5/1000
              case  "mg"  =>   amount2 = ingredient2.getAmount / 1000000 / ingredient2.getDensity * 5/1000
              case  "pcs" =>   Dialog.showMessage(ingredientInfo, "Can't delete pieces from tsp! Error occured with: " + ingredient1.getName  , "Error!")
            }
          }
          case "tbsp" =>  {
            ingredient2.getUnit match {
              case  "dl"  =>   amount2 = ingredient2.getAmount * 15/100
              case  "l"   =>   amount2 = ingredient2.getAmount * 15/1000
              case  "ml"  =>   amount2 = ingredient2.getAmount * 1/15
              case  "tsp" =>   amount2 = ingredient2.getAmount * 3
              case  "g"   =>   amount2 = ingredient2.getAmount / 1000 / ingredient2.getDensity * 15/1000
              case  "kg"  =>   amount2 = ingredient2.getAmount / ingredient2.getDensity * 15/1000
              case  "mg"  =>   amount2 = ingredient2.getAmount / 10000 / ingredient2.getDensity * 15/1000
              case  "pcs" =>   Dialog.showMessage(ingredientInfo, "Can't delete pieces from tbsp! Error occured with: " + ingredient1.getName , "Error!")
            }
          }
          case  "g"   =>  {
            ingredient2.getUnit match {
              case  "dl"  =>   amount2 = ingredient2.getAmount * 1/10 * ingredient2.getDensity * 1/1000 // dl to l, l to kg, kg to g
              case  "l"   =>   amount2 = ingredient2.getAmount * ingredient2.getDensity * 1/1000
              case  "ml"  =>   amount2 = ingredient2.getAmount * 1/1000 * ingredient2.getDensity * 1/1000 //ml to l, l to kg, kg to g
              case  "tsp" =>   amount2 = ingredient2.getAmount * 5/1000 * ingredient2.getDensity * 1/1000
              case "tbsp" =>   amount2 = ingredient2.getAmount * 15/1000 * ingredient2.getDensity * 1/1000
              case  "kg"  =>   amount2 = ingredient2.getAmount * 1000
              case  "mg"  =>   amount2 = ingredient2.getAmount * 1000
              case  "pcs" =>   Dialog.showMessage(ingredientInfo, "Can't delete pieces from grams! Error occured with: " + ingredient1.getName  , "Error!")
            }
          }
          case  "kg"  =>  {
            ingredient2.getUnit match {
              case  "dl"  =>   amount2 = ingredient2.getAmount * 1/10 * ingredient2.getDensity
              case  "l"   =>   amount2 = ingredient2.getAmount * ingredient2.getDensity
              case  "ml"  =>   amount2 = ingredient2.getAmount * 1/1000 * ingredient2.getDensity
              case  "tsp" =>   amount2 = ingredient2.getAmount * 5/1000 * ingredient2.getDensity
              case "tbsp" =>   amount2 = ingredient2.getAmount * ingredient2.getDensity * 15/1000
              case  "g"   =>   amount2 = ingredient2.getAmount * 1/1000
              case  "mg"  =>   amount2 = ingredient2.getAmount * 1/1000000
              case  "pcs" =>   Dialog.showMessage(ingredientInfo, "Can't delete pieces from kilograms! Error occured with: " + ingredient1.getName , "Error!")
            }
          }
          case  "mg"  =>  {
            ingredient2.getUnit match {
              case  "dl"  =>   amount2 = ingredient2.getAmount * 1/10 * ingredient2.getDensity * 1/1000000
              case  "l"   =>   amount2 = ingredient2.getAmount * ingredient2.getDensity * 1/1000000
              case  "ml"  =>   amount2 = ingredient2.getAmount * 1/1000 * ingredient2.getDensity * 1/10000000
              case  "tsp" =>   amount2 = ingredient2.getAmount * 5/1000 * ingredient2.getDensity * 1/1000000
              case "tbsp" =>   amount2 = ingredient2.getAmount * 15/1000 * ingredient2.getDensity * 1/1000000
              case  "g"   =>   amount2 = ingredient2.getAmount * 1000
              case  "kg"  =>   amount2 = ingredient2.getAmount * 1000000
              case  "pcs" =>   Dialog.showMessage(ingredientInfo, "Can't delete pieces from mg! Error occured with: " + ingredient1.getName  , "Error!")
            }
          }
        }
    }
    amount2
  }


}
