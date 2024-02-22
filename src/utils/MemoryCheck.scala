package utils

import entity.Cassette

import scala.annotation.tailrec

object MemoryCheck {

  def listDataIds(cassette:Cassette): Array[Int] = { //Return all existing data's id from a cassette
    listDataIdsRec(cassette, Array[Int](), 0)
  }
  @tailrec
  def listDataIdsRec(cassette:Cassette, output:Array[Int], index:Int): Array[Int] = {
    if (index < cassette.table.length) {
      if (cassette.table(index) != 0) {
        listDataIdsRec(cassette, output.:+(cassette.table(index)), index + 2)
      }
      else {
        output
      }
    }
    else {
      output
    }
  }

  def checkIfIdExists(cassette:Cassette, id:Int) : Boolean = { //Check into a cassette if the id already exists
    val idList = listDataIds(cassette)
    if (idList.contains(id)) {
      true
    }
    else {
      false
    }
  }

  def memoryLeft(cassette:Cassette): Int = { //Check the amount of memory left into a cassette
    memoryLeftRec(cassette, 0, 0)
  }
  @tailrec
  def memoryLeftRec(cassette:Cassette, index:Int, output:Int) : Int = {
    if (cassette.data.contains(0)) {
      if (index < cassette.data.length) {
        if(cassette.data(index) == 0) {
          memoryLeftRec(cassette, index+1, output+1)
        }
        else {
          memoryLeftRec(cassette, index+1, output)
        }
      }
      else {
        output
      }
    }
    else {
      output
    }
  }

}