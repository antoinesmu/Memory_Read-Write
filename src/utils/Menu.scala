package utils

import utils.File.cassetteFromFile
import utils.MemoryCheck.{checkIfIdExists, memoryLeft}
import utils.Read.readById
import utils.Delete.{deleteAll, deleteById}

import scala.annotation.tailrec
import scala.io.StdIn

object Menu {
  val fileName = "memory.txt"

  @tailrec
  def mainMenu() : Unit = { //Display the main menu
    println("|---- Main Menu: ----|")
    println("|  1 - Read          |")
    println("|  2 - Add           |")
    println("|  3 - Delete        |")
    println("|  4 - Memory        |")
    println("|  5 - AboutMe       |")
    println("|  0 - Exit          V")
    print("|------ Your choice: ")

    val cmd = StdIn.readLine()
    cmd match{
      case "1" => readMenu()
      case "2" => addMenu()
      case "3" => deleteMenu()
      case "4" => memoryMenu()
      case "5" => aboutMe()
      case "0" => println("\n--Exit--")
      case _ => mainMenu()
    }
  }

  @tailrec
  def readMenu() : Unit = { //Display the read menu
    println("|---- Read Menu: ----|")
    println("|  1 - Read by ID    |")
    println("|  2 - Read All      |")
    println("|  0 - Back          V")
    print("|------ Your choice: ")

    val cmd = StdIn.readLine()
    cmd match{
      case "1" => readId()
      case "2" => readAll()
      case "0" => mainMenu()
      case _ => readMenu()
    }

  }
  def readId() : Unit = { //Start the procedure to read a single data by its id
    val cassette = cassetteFromFile(fileName)
    print("Read ID: ")
    val id = StdIn.readInt()
    readById(cassette, id)
    println("\n")
    readMenu()
  }
  def readAll() : Unit = { //Display all data stored
    val cassette = cassetteFromFile(fileName)
    Read.readAll(cassette)
    println("\n")
    mainMenu()
  }

  def addMenu(): Unit = { //Start procedure to add a data into the storage
    val cassette = cassetteFromFile(fileName)
    print("Enter data ID: ")
    try {
      val id = StdIn.readInt()
      print("Enter data: ")
      val data = StdIn.readLine()
      println("Confirm? (ID: " + id + ", data: " + data + ")")
      print("Type 'confirm' to confirm, any other entry will abort: ")
      val confirm = StdIn.readLine()
      if (confirm == "confirm") {
        Add.addData(cassette, id, data)
      }
      else {
        println("Operation aborted\n")
      }
    }
    catch {
      case e: NumberFormatException => println("ID must be an integer!")
        addMenu()
    }
    mainMenu()
  }

  @tailrec
  def deleteMenu(): Unit = { //Display the delete menu
    println("|---- Delete Menu: ----|")
    println("|  1 - Delete by ID    |")
    println("|  2 - Wipe Memory     |")
    println("|  0 - Back            V")
    print("|-------- Your choice: ")

    val cmd = StdIn.readLine()
    cmd match{
      case "1" => deleteId()
      case "2" => deleteAll()
      case "0" => mainMenu()
      case _ => deleteMenu()
    }
  }
  def deleteId(): Unit = { //Start procedure to delete a data by its id
    val cassette = cassetteFromFile(fileName)
    print("Enter data ID: ")
    try {
      val id = StdIn.readInt()
      if (checkIfIdExists(cassette, id)) {
        println("You will remove this data: ")
        readById(cassette, id)
        print("Type 'confirm' to confirm, any other entry will abort: ")
        val confirm = StdIn.readLine()
        if (confirm == "confirm") {
          deleteById(cassette, id)
          println("Data successfully removed!\n")
        }
        else {
          println("Operation aborted\n")
        }
      }
      else {
        println("ID does not exists!\n")
      }
    }
      catch {
        case e: NumberFormatException => println("ID must be an integer!")
          deleteMenu()
      }
    mainMenu()
  }
  def deleteAll(): Unit = { //Start procedure to wipe the memory file
    println("You're attempting to wipe all stored data")
    print("Type 'confirm wipe data' to confirm, any other entry will abort: ")
    val confirm = StdIn.readLine()
    if (confirm.equals("confirm wipe data")) {
      Delete.deleteAll()
      println("Memory successfully wiped!\n")
    }
    else {
      println("Operation aborted\n")
    }
    mainMenu()
  }

  def memoryMenu(): Unit = { //Display memory information
    val cassette = cassetteFromFile(fileName)
    val memoryLft = MemoryCheck.memoryLeft(cassette)
    val numberOfData = MemoryCheck.listDataIds(cassette).length
    println("Memory: " + memoryLft + "/800 bytes available remaining | " + numberOfData + " data stored\n")
    mainMenu()
  }

  def aboutMe(): Unit = { //Display the AboutMe.md file
    AboutMe.aboutMe()
    mainMenu()
  }
}