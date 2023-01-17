package SmartCookbook
package cookbook

import java.awt.Color
import scala.swing._
import scala.collection.mutable.Buffer
import scala.swing.ListView.Renderer
import java.awt.Font


/**
 * Most UI components are named so that it is easy to deduce from them what they are doing.
 * However, some components have been accompanied by more specific comments.
 */


object SmartCookbook extends SimpleSwingApplication {

  val readData = "src/main/scala/data/ingredients.txt"
  val recipeData = "src/main/scala/data/recipes"
  val testIngredient = new Ingredient("beef", 400.0, "g", 1.0)
  val testIngredient2 = new Ingredient("potato",5.0, "pcs", 1.0)
  val testRecipe = new Recipe("soppa", new IngredientStorage(Buffer(testIngredient)), "Ei muutakun keitolle", false, false, false, false, false)
  val searchEngine = new SearchBar

  val width = 1200
  val height = 700
  val storage = new IngredientStorage(testIngredient.readData(readData))
  var newRecipeIngredients = Buffer[Ingredient]()
  var storage2 = new IngredientStorage(newRecipeIngredients)
  val converter = new UnitConverter
  val recipes = new RecipeStorage(testRecipe.readData(recipeData))
  var listData2 = Buffer[String]()

  def top = new MainFrame {
    title    = "SmartCookbook"
    contents = allComponents
    preferredSize = new Dimension(width,height)
    maximumSize   = new Dimension(width,height)
    minimumSize   = new Dimension(width,height)
    resizable = false
    centerOnScreen()
  }
  // Components:


  val allComponents = new BoxPanel(Orientation.Horizontal)

  val recipe = new BoxPanel(Orientation.Vertical) {
    border = Swing.EmptyBorder(0, 30, 0, 40)
  }

  val ingredient = new BoxPanel(Orientation.Vertical) {
  }

  val allComponents2 = new BoxPanel(Orientation.Horizontal)
  val ingredient2 = new BoxPanel(Orientation.Vertical)
  val justButton2 = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(10, 0, 0, 0)
  }

  val recipeList = new ListView[(String)] () {
    listData = recipes.rStorage.map(title => title.getTitle)

  }


  val recipePanel = new TextArea() {
    background = Color.lightGray
    wordWrap      = true
    lineWrap      = true
    text          = ""
    editable      = false
  }

  val searchPanel = new BoxPanel(Orientation.Vertical) {
  }

  val search = new TextField() {
    preferredSize = new Dimension(200,30)
    maximumSize   = new Dimension(200,30)
    //minimumSize   = new Dimension(200,30)
  }



  val searchResult = new BoxPanel(Orientation.Horizontal)

  val searchFrame = new Frame {
    visible = false
    title    = "Search results"
    contents = searchResult
    preferredSize = new Dimension(700,500)
    maximumSize   = new Dimension(700,500)
    minimumSize   = new Dimension(700,500)
    resizable = false
    centerOnScreen()
  }

  val searchTextArea = new TextArea() {
    background = Color.pink
    wordWrap      = true
    lineWrap      = true
    text          = ""
    editable      = false
  }

  val searchedRecipes = new ListView[(String)]() {

  }

  val searchButton = new Button("Search") {
    action = Action("Search") {
      try {
        listData2 = searchEngine.search(search.text.trim.toLowerCase, recipes).rStorage.map(title => title.getTitle)
        searchedRecipes.listData = searchEngine.search(search.text, recipes).rStorage.map(title => title.getTitle)
        val selected = searchEngine.search(search.text, recipes).rStorage.head
        var selectedIngredients = selected.list.storage.map( i => (i.getName + ", " + i.getAmount.toString + i.getUnit + ", " + i.getDensity.toString + "\n"))
        searchFrame.visible = true
        searchTextArea.text = ("Title: " + selected.getTitle + "\n\n"
                          + selectedIngredients.mkString
                          + "\n\n"
                          + selected.getGuide + "\n\n"
                          + "Glutenfree: " + selected.getGluten.toString + "\n"
                          + "Milkfree: " + selected.getMilk.toString + "\n"
                          + "Fishfree: " + selected.getFish.toString + "\n"
                          + "Nutfree: " + selected.getNut.toString + "\n"
                          + "Vegetarian: " + selected.getVege.toString
                          )
     } catch {
        case e: IndexOutOfBoundsException => Dialog.showMessage(recipesScroll, "Could not find recipes", "This is a message")
        case r: NumberFormatException => Dialog.showMessage(recipesScroll, "Error occured!", "This is a message")
      }
    }
  }

  val selectButton = new Button("Select") {
    action = Action("Select") {
      try {
        val selected = recipes.rStorage.filter(_.getTitle.contains(search.text.trim.toLowerCase)).head
        var selectedIngredients = selected.list.storage.map( i => (i.getName + ", " + i.getAmount.toString + i.getUnit + ", " + i.getDensity.toString + "\n"))
       recipePanel.text = ("Title: " + selected.getTitle + "\n\n"
                          + selectedIngredients.mkString
                          + "\n\n"
                          + selected.getGuide + "\n\n"
                          + "Glutenfree: " + selected.getGluten.toString + "\n"
                          + "Milkfree: " + selected.getMilk.toString + "\n"
                          + "Fishfree: " + selected.getFish.toString + "\n"
                          + "Nutfree: " + selected.getNut.toString + "\n"
                          + "Vegetarian: " + selected.getVege.toString
                          )
     } catch {
        case e: IndexOutOfBoundsException => Dialog.showMessage(recipesScroll, "Recipe not found!", "This is a message")
        case r: NumberFormatException => Dialog.showMessage(recipesScroll, "Choose right unit, please!", "This is a message")
      }
    }
  }

  /**
   * Below this there are warning components.
   * They are made in such a way that the user is asked if the user definitely wants to perform that transaction.
   */

  val warningComponents2 = new BoxPanel(Orientation.Vertical)

  val warningV2 = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(10,100,10,0)
  }

  val warning2 = new Label() {
      foreground = Color.RED
      font = new Font("Roboto", 0 , 19)
  }

  val warningFrame2 = new Frame {
    visible = false
    title    = "Warning!"
    contents = warningComponents2
    preferredSize = new Dimension(900,100)
    maximumSize   = new Dimension(900,100)
    minimumSize   = new Dimension(900,100)
    resizable = false
    centerOnScreen()
  }

  val warningNo2 = new Button("No") {
      action = Action("No") {
      warningFrame2.visible = false
    }
  }

  val warningYes2 = new Button("Yes") {
    action = Action("Yes") {
      try {
        val selected = recipes.rStorage.filter(_.getTitle.contains(search.text.trim.toLowerCase)).head
        val missingIngredients = selected.missing(selected.list, storage)
          selected.done(selected, storage)
         ingredientList.listData = storage.storage.map(x => (x.getName, x.getAmount, x.getUnit, x.getDensity))
         storage.writeToData(readData, storage)
         warningFrame2.visible = false
      } catch {
        case e: IndexOutOfBoundsException => warningFrame2.visible = false
        case r: NumberFormatException => Dialog.showMessage(recipess, "number!", "This is a message")

      }
    }
  }

  val doneButton = new Button("Done") {
      action = Action("Done") {
      warningFrame2.visible = true
      warning2.text = try {
        "You have done: " + recipes.rStorage.filter(_.getTitle.contains(search.text.trim.toLowerCase)).head.getTitle +  ". Delete used ingredients from storage?"
      } catch {
        case r: IndexOutOfBoundsException =>  "Delete " + recipes.rStorage.head.getTitle + "??"
      }
   }
  }

  warningV2.contents += warningYes2
  warningV2.contents += warningNo2
  warningComponents2.contents += warning2
  warningComponents2.contents += warningV2

  val warningComponents = new BoxPanel(Orientation.Vertical)

  val warningV = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(10,100,10,0)
  }

  val warning = new Label() {
      foreground = Color.RED
      font = new Font("Roboto", 0 , 19)
  }

  val warningFrame = new Frame {
    visible = false
    title    = "Warning!"
    contents = warningComponents
    preferredSize = new Dimension(600,100)
    maximumSize   = new Dimension(600,100)
    minimumSize   = new Dimension(600,100)
    resizable = false
    centerOnScreen()
  }

  val warningNo = new Button("No") {
    action = Action("No") {
      warningFrame.visible = false
    }
  }

  val warningYes = new Button("Yes") {
    action = Action("Yes") {
      try {
        val selected = recipes.rStorage.filter(_.getTitle.contains(search.text)).head
        recipes.deleteRecipe(selected.getTitle)
        recipes.writeToData(recipeData, recipes)
        recipeList.listData = recipes.rStorage.map(title => title.getTitle)
        warningFrame.visible = false
      } catch {
        case e: IndexOutOfBoundsException => Dialog.showMessage(recipess, "Error occured!", "This is a message")
        case r: NumberFormatException => Dialog.showMessage(recipess, "Error occured!", "This is a message")
      }
    }
  }



  warningV.contents += warningYes
  warningV.contents += warningNo
  warningComponents.contents += warning
  warningComponents.contents += warningV

  val deletRecipeButton = new Button("Delete") {
    action = Action("Delete") {
      warningFrame.visible = true
      warning.text = try {
        "Delete " + recipes.rStorage.filter(_.getTitle.contains(search.text)).head.getTitle + "??"
      } catch {
        case r: IndexOutOfBoundsException =>  "Delete " + recipes.rStorage.head.getTitle + "??"
      }
   }
  }

  val searchLabel = new Label("Select or search recipe(s)") {
    foreground = Color.RED
    font = new Font("Roboto", 0 , 19)
  }

  val deleteLabel = new Label("Choose the recipe you want delete.") {
    foreground = Color.RED
    font = new Font("Roboto", 0 , 19)
  }

  val doneLabel = new Label("Choose the recipe you have made.") {
    foreground = Color.RED
    font = new Font("Roboto", 0 , 19)
  }

  val recipeHeader = new Label("Recipes") {
      foreground = Color.RED
      font = new Font("Roboto", 0 , 19)
  }


  val newRecipeFrame = new Frame {
    visible = false
    title    = "New Recipe"
    background = Color.cyan
    contents = allComponents2
    preferredSize = new Dimension(width-100,height+100)
    maximumSize   = new Dimension(width-100,height+100)
    minimumSize   = new Dimension(width-100,height+100)
    resizable = false
    centerOnScreen()
  }

  val ingredientList2 = new ListView[(String , Double , String , Double)] () {
  }

  val ingredientAdder2 = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(0, 10, 0, 0)
  }
  val ingredientInfo2 = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(0, 10, 0, 60)
  }


  val addRecipeButton = new Button("Add new recipe") {
    action = Action("Add new recipe") {
      newRecipeFrame.visible = true
      storage2 = new IngredientStorage(newRecipeIngredients.empty)
      ingredientList2.listData = storage2.storage.map(x => (x.getName, x.getAmount, x.getUnit, x.getDensity)).empty
    }
  }

  /**
   * A few components above this and down here which are marked with number 2 are
   * components which I am using on the addRecipe frame...
   */

  val newTitle2 = new TextField("Recipe title") {
    preferredSize = new Dimension(200,30)
    maximumSize   = new Dimension(200,30)
    minimumSize   = new Dimension(200,30)
  }

  val newGuide2 = new TextArea("Recipe guide") {
  }
  val glutLabel2 = new Label("Glutenfree?") {
      foreground = Color.RED
      font = new Font("Roboto", Font.BOLD , 12)
  }
  val fishLabel2 = new Label("Fishfree?") {
      foreground = Color.RED
      font = new Font("Roboto", Font.BOLD , 12)
  }
  val nutLabel2 = new Label("Nutfree?") {
      foreground = Color.RED
      font = new Font("Roboto", Font.BOLD , 12)
  }
  val milkLabel2 = new Label("Milkfree?") {
      foreground = Color.RED
      font = new Font("Roboto", Font.BOLD , 12)
  }
  val vegeLabel2 = new Label("Vegetarian?") {
      foreground = Color.RED
      font = new Font("Roboto", Font.BOLD , 12)
  }

  val newGlut2 = new ComboBox[String](Seq("true", "false")) {
    preferredSize = new Dimension(90,30)
    maximumSize   = new Dimension(90,30)
    minimumSize   = new Dimension(90,30)
    background = Color.pink
  }
  val newFish2 = new ComboBox[String](Seq("true", "false")) {
    preferredSize = new Dimension(90,30)
    maximumSize   = new Dimension(90,30)
    minimumSize   = new Dimension(90,30)
    background = Color.pink
  }
  val newNut2 = new ComboBox[String](Seq("true", "false")) {
    preferredSize = new Dimension(90,30)
    maximumSize   = new Dimension(90,30)
    minimumSize   = new Dimension(90,30)
    background = Color.pink
  }
  val newMilk2 = new ComboBox[String](Seq("true", "false")) {
    preferredSize = new Dimension(90,30)
    maximumSize   = new Dimension(90,30)
    minimumSize   = new Dimension(90,30)
    background = Color.pink
  }
  val newVege2 = new ComboBox[String](Seq("true", "false")) {
    preferredSize = new Dimension(90,30)
    maximumSize   = new Dimension(90,30)
    minimumSize   = new Dimension(90,30)
    background = Color.pink
  }

  val addRecipeButton2 = new Button("Add new recipe") {
    action = Action("Add new recipe") {
      val addingRecipe = new Recipe(newTitle2.text, storage2, newGuide2.text, newGlut2.item.toBoolean, newMilk2.item.toBoolean, newFish2.item.toBoolean, newNut2.item.toBoolean, newVege2.item.toBoolean)

      recipes.rStorage += addingRecipe
      recipes.writeToData(recipeData, recipes)
      recipeList.listData = recipes.rStorage.map(title => title.getTitle)
      newRecipeFrame.visible = false
    }
  }

  val list2 = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(5, 0, 5, 15)
  }

  val listLabel2 = new Label("Ingredient  Amount  Unit  Density") {
      foreground = Color.RED
      font = new Font("Roboto", Font.BOLD , 12)
  }

  val amountLabel2 = new Label("Amount") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }

  val densityLabel2 = new Label("Density kg/dm3") {
    preferredSize = new Dimension(90,30)
    maximumSize   = new Dimension(90,30)
    minimumSize   = new Dimension(90,30)
  }

  val ingredientLabel2 = new Label("Ingredient") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }

  val unitLabel2 = new Label("Unit") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }

  var ingredientName2 = new TextField("Name") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
  var ingredientAmount2 = new TextField("1") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
  var ingredientUnit2 = new ComboBox[String](Seq("dl", "l", "ml", "tsp", "tbsp", "g", "kg", "mg", "pcs")) {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
  var ingredientDensity2 = new TextField("1") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }


  val ingredientList = new ListView[(String , Double , String , Double)] () {
    listData = storage.storage.map(x => (x.getName, x.getAmount, x.getUnit, x.getDensity))
    renderer = Renderer(y => (y._1 + ",   " + y._2 + y._3 + ",   " + y._4))

  }
   //top,left,bottom,right

  val ingredientAdder = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(0, 10, 0, 0)
  }
  val ingredientDeleter = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(0, 10, 0, 0)
  }
  val ingredientInfo = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(0, 10, 0, 60)
  }

  val recipess = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(40, 0, 5, 0)
  }

  val addRecipes = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(40, 0, 5, 0)
  }


  val list = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(5, 0, 5, 15)
  }

  val listLabel = new Label("Ingredient  Amount  Unit  Density") {
      foreground = Color.RED
      font = new Font("Roboto", Font.BOLD , 12)
  }


  val amountLabel = new Label("Amount") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }

  val densityLabel = new Label("Density kg/dm3") {
    preferredSize = new Dimension(90,30)
    maximumSize   = new Dimension(90,30)
    minimumSize   = new Dimension(90,30)
  }

  val ingredientLabel = new Label("Ingredient") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }

  val unitLabel = new Label("Unit") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }

  var ingredientName = new TextField("Name") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
  var ingredientAmount = new TextField("1") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
  var ingredientUnit = new ComboBox[String](Seq("dl", "l", "ml", "tsp", "tbsp", "g", "kg", "mg", "pcs")) {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
  var ingredientDensity = new TextField("1") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
   var ingredientDeleteName = new TextField("Name") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }

  var ingredientDeleteAmount = new TextField("1") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
  var ingredientDeleteDensity = new TextField("1") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }
  var ingredientDeleteUnit = new ComboBox[String](Seq("dl", "l", "ml", "tsp", "tbsp", "g", "kg", "mg", "pcs")) {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
  }



  val ingredientDeleteButton = new Button("Delete") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
    action = Action("Delete") {
      try {
      val delet = storage.storage.filter(_.getName == ingredientDeleteName.text.trim.toLowerCase).head
       if (delet.getUnit != "pcs" && ingredientDeleteUnit.item == "pcs") Dialog.showMessage(ingredientInfo, "Cannot convert pieces, choose right unit, please!", "This is a message!")
       else if (delet.getUnit == ingredientDeleteUnit.item) {
         storage.delete(ingredientDeleteName.text.trim.toLowerCase, ingredientDeleteAmount.text.toDouble)
         ingredientList.listData = storage.storage.map(x => (x.getName, x.getAmount, x.getUnit, x.getDensity))
         storage.writeToData(readData, storage)
       }
       else {
         storage.delete(ingredientDeleteName.text.trim.toLowerCase, converter.calculate
         (delet, new Ingredient(ingredientDeleteName.text.trim.toLowerCase, ingredientDeleteAmount.text.toDouble, ingredientDeleteUnit.item, ingredientDeleteDensity.text.toDouble)))
         ingredientList.listData = storage.storage.map(x => (x.getName, x.getAmount, x.getUnit, x.getDensity))
         storage.writeToData(readData, storage)
         //Dialog.showMessage(ingredientInfo, "Choose right unit, please!", "This is a message")
       }
     } catch {
        case e: IndexOutOfBoundsException => Dialog.showMessage(ingredientInfo, "Ingredient not found", "Error!")
        case r: NumberFormatException => Dialog.showMessage(ingredientInfo, "Check your Amount and Unit values!", "Error!")
        case h: MatchError => Dialog.showMessage(ingredientInfo, "Can't delete " + ingredientDeleteUnit.item +  " from pieces!", "Error!")
      }
    }
  }

  val ingredientButton = new Button("Add") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
    action = Action("Add") {
      try {
      var newIngredient = new Ingredient(ingredientName.text.toLowerCase, ingredientAmount.text.toDouble, ingredientUnit.item, ingredientDensity.text.toDouble)
        if(storage.storage.map(_.getName).contains(ingredientName.text.trim.toLowerCase) ) {
          val mutable = storage.storage.filter(_.getName == ingredientName.text.trim.toLowerCase).head
          newIngredient = new Ingredient(ingredientName.text.toLowerCase, ingredientAmount.text.toDouble + mutable.getAmount, ingredientUnit.item, ingredientDensity.text.toDouble)
          storage.delete(ingredientName.text.toLowerCase, mutable.getAmount)
          storage.add(newIngredient)
          ingredientList.listData = storage.storage.map(x => (x.getName, x.getAmount, x.getUnit, x.getDensity))
          storage.writeToData(readData, storage)
        }
          else {
          storage.add(newIngredient)
          ingredientList.listData = storage.storage.map(x => (x.getName, x.getAmount, x.getUnit, x.getDensity))
          storage.writeToData(readData, storage)
          println("ingredient: " + ingredientName.text, ingredientAmount.text.toDouble, ingredientUnit.item, ingredientDensity.text.toDouble)
        }
      } catch {
        case r: NumberFormatException => Dialog.showMessage(ingredientInfo, "Check your Amount and Unit values!", "Error!")
      }
    }
  }


   val ingredientButton2 = new Button("Add") {
    preferredSize = new Dimension(70,30)
    maximumSize   = new Dimension(70,30)
    minimumSize   = new Dimension(70,30)
    action = Action("Add") {
      try {
      var newIngredient = new Ingredient(ingredientName2.text.trim.toLowerCase, ingredientAmount2.text.toDouble, ingredientUnit2.item, ingredientDensity2.text.toDouble)
          storage2.add(newIngredient)
          ingredientList2.listData = storage2.storage.map(x => (x.getName, x.getAmount, x.getUnit, x.getDensity))
          println("ingredient: " + ingredientName2.text.trim.toLowerCase, ingredientAmount2.text.toDouble, ingredientUnit2.item, ingredientDensity2.text.toDouble)
      } catch {
        case r: NumberFormatException => Dialog.showMessage(newRecipeFrame, "Check your Amount and Unit values!", "Error!")
      }
    }
  }


  val whatCanDo = new BoxPanel(Orientation.Vertical) {
    border = Swing.EmptyBorder(20, 0, 0, 0)
  }

  val chooseLabelBox = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(0 ,0 ,0 ,0)
  }
  //top,left,bottom,right

  val chooseLabel = new Label("Choose from these:") {
      foreground = Color.orange
      font = new Font("Roboto", Font.BOLD , 19)
  }

  val whatCanDoFrame = new Frame {
    title = "What I can do"
    visible = false
    contents = whatCanDo
    preferredSize = new Dimension(400, 500)
    maximumSize   = new Dimension(400, 500)
    minimumSize   = new Dimension(400, 500)
    resizable = false
    centerOnScreen()
  }

  val canDoList = new ListView[String]() {
  }

  val canDoneButton = new Button("What recipe(s) I can make?") {
    background = Color.orange
    action = Action("What recipe(s) I can make?") {
      whatCanDoFrame.visible = true
      val canDoBuffer = Buffer[Recipe]()
      recipes.rStorage.map(x => if(x.missing(x.list, storage).isEmpty) canDoBuffer += x)
      canDoList.listData = canDoBuffer.map(r => r.getTitle)
    }
  }


  justButton2.contents += addRecipeButton2

  val newGuideScroll2 = new ScrollPane(newGuide2) {
    preferredSize = new Dimension(width-100,100)
    maximumSize   = new Dimension(width-100,100)
    minimumSize   = new Dimension(width-100,100)
  }
  /**
   * Almost all components are compounded here:
   * And down here
   */

  val adderBuffer = Buffer(ingredientName, ingredientAmount, ingredientUnit, ingredientDensity, ingredientButton)
  val deleteBuffer = Buffer(ingredientDeleteName, ingredientDeleteAmount, ingredientDeleteUnit, ingredientDeleteDensity, ingredientDeleteButton)
  val infoBuffer = Buffer(ingredientLabel, amountLabel, unitLabel, densityLabel)
  val adderBuffer2 = Buffer(ingredientName2, ingredientAmount2, ingredientUnit2, ingredientDensity2, ingredientButton2)
  val infoBuffer2 = Buffer(ingredientLabel2, amountLabel2, unitLabel2, densityLabel2)
  val recipeBuffer2 = Buffer(newTitle2, newGuideScroll2, glutLabel2, newGlut2, milkLabel2, newMilk2, fishLabel2, newFish2, nutLabel2, newNut2, vegeLabel2, newVege2, justButton2)

  ingredientInfo.contents    ++= infoBuffer
  ingredientAdder.contents   ++= adderBuffer
  ingredientDeleter.contents ++= deleteBuffer
  list.contents              +=  listLabel
  recipess.contents          +=  recipeHeader
  addRecipes.contents        +=  addRecipeButton
  ingredientInfo2.contents   ++= infoBuffer2
  ingredientAdder2.contents  ++= adderBuffer2
  list2.contents             += listLabel2


  /**
   * There I have added scroll-components for all listViews and textFields.
   */

  val scroll = new ScrollPane(ingredientList) {
    preferredSize = new Dimension(200,200)
    maximumSize   = new Dimension(200,200)
    minimumSize   = new Dimension(200,200)
  }

  val recipeScroll = new ScrollPane(recipeList) {
    preferredSize = new Dimension(200,200)
    maximumSize   = new Dimension(200,200)
    minimumSize   = new Dimension(200,200)
  }

  val searchScroll = new ScrollPane(searchedRecipes) {
    preferredSize = new Dimension(200,200)
    maximumSize   = new Dimension(200,200)
    minimumSize   = new Dimension(200,200)
  }

  val recipesScroll = new ScrollPane(recipePanel) {
    preferredSize = new Dimension(500, height)
    maximumSize   = new Dimension(500, height)
    minimumSize   = new Dimension(500, height)
  }

  val searchRecipeScroll = new ScrollPane(searchTextArea) {
    preferredSize = new Dimension(400, 500)
    maximumSize   = new Dimension(400, 500)
    minimumSize   = new Dimension(400, 500)
  }

  val scroll2 = new ScrollPane(ingredientList2) {
    preferredSize = new Dimension(200,200)
    maximumSize   = new Dimension(200,200)
    minimumSize   = new Dimension(200,200)
  }

  val scroll3 = new ScrollPane(canDoList) {
    preferredSize = new Dimension(200,200)
    maximumSize   = new Dimension(200,200)
    minimumSize   = new Dimension(200,200)
  }


  /**
   * Almost all components are compounded here:
   */

  ingredient.contents += list
  ingredient.contents += scroll
  ingredient.contents += ingredientInfo
  ingredient.contents += ingredientAdder
  ingredient.contents += ingredientDeleter
  ingredient.contents += recipess
  ingredient.contents += recipeScroll
  ingredient.contents += addRecipes


  ingredient2.contents += list2
  ingredient2.contents += scroll2
  ingredient2.contents += ingredientInfo2
  ingredient2.contents += ingredientAdder2
  ingredient2.contents ++= recipeBuffer2

  chooseLabelBox.contents += chooseLabel
  whatCanDo.contents += chooseLabelBox
  whatCanDo.contents += scroll3

  recipe.contents += recipesScroll

  searchPanel.contents += canDoneButton
  searchPanel.contents += searchLabel
  searchPanel.contents += searchButton
  searchPanel.contents += selectButton
  searchPanel.contents += deleteLabel
  searchPanel.contents += deletRecipeButton
  searchPanel.contents += doneLabel
  searchPanel.contents += doneButton
  searchPanel.contents += search


  searchResult.contents += searchScroll
  searchResult.contents += searchRecipeScroll

  allComponents.contents += ingredient
  allComponents.contents += recipe
  allComponents.contents += searchPanel

  allComponents2.contents += ingredient2

}
