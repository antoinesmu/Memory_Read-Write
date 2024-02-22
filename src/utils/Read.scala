package utils

import entity.Cassette
import utils.AsciiConvertor.bytesToAscii
import utils.MemoryCheck.{checkIfIdExists, listDataIds}
import utils.AsciiConvertor.printArray

import scala.annotation.tailrec

object Read {

  def bytesBeforeData(cassette:Cassette, dataId:Int): Int = { //Number of bytes before the selected data
    bytesBeforeDataRec(cassette, dataId, 0, 1)
  }
  @tailrec
  def bytesBeforeDataRec(cassette:Cassette, dataId:Int, output:Int, index:Int) : Int = {
    if (index-1 < cassette.table.indexOf(dataId)) {
      bytesBeforeDataRec(cassette, dataId, output+cassette.table(index), index+2)
    }
    else {
      output
    }
  }

  def readById(cassette:Cassette, id:Int) : Unit = { //Method to read a data by its id
    if (checkIfIdExists(cassette, id)) {
      val dataLength = cassette.table(cassette.table.indexOf(id) + 1)
      val skipLength = bytesBeforeData(cassette, id)
      val data = cassette.data.slice(skipLength, skipLength + dataLength)
      val text = bytesToAscii(data)
      println("#" + id + ": " + text + " | Length: " + data.length + " byte(s) | Byte(s) value: " + printArray(data))
    }
    else {
      print("Id does not exists")
    }
  }

  def readAll(cassette:Cassette): Unit = { //Method to read every data stored
    readAllRec(cassette, 0)
  }
  @tailrec
  def readAllRec(cassette:Cassette, index:Int) : Unit = {
    val listId = listDataIds(cassette)

    if (index < listId.length) {
      readById(cassette, listId(index))
      readAllRec(cassette, index+1)
    }
    else {
    }
  }

}