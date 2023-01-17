package SmartCookbook
package cookbook

import scala.collection.mutable.Buffer

class SearchBar {

  def search(criteria: String, recipes: RecipeStorage): RecipeStorage = {

    var returnStorage = Buffer[Recipe]()

    recipes.rStorage.map( x =>
      if (x.getTitle.contains(criteria)) returnStorage += x
      else if (criteria.contains("fish") && criteria.contains("free") && x.getFish) returnStorage += x
      else if (criteria.contains("nut")  && criteria.contains("free") && x.getNut) returnStorage += x
      else if (criteria.contains("milk") && criteria.contains("free") && x.getMilk) returnStorage += x
      else if (criteria.contains("vege") && x.getVege) returnStorage += x
      else if (criteria.contains("glut") && criteria.contains("free") && x.getGluten) returnStorage += x
    )

    new RecipeStorage(returnStorage)
  }
}
