package utils

import entity.Cassette
import utils.AsciiConvertor.{asciiToBytes, printArray}
import utils.MemoryCheck.{checkIfIdExists, memoryLeft}
import utils.File.writeFile

object Add {

  def addData(cassette:Cassette, id:Int, data:String): Unit = { //Create a new data
    if (id!=0) { //Check if id isn't 0
      if (!checkIfIdExists(cassette, id)) { //Check if id doesn't already exists
        if (data.length <= memoryLeft(cassette)) { //Check if there is enough memory left
          //New data array
          val data0 = cassette.data.indexOf(0)
          val oldData = Array.fill[Int](data0)(0)
          cassette.data.copyToArray(oldData, 0, data0)
          val newData = oldData ++ asciiToBytes(data) ++ Array.fill[Int](800-oldData.length-data.length)(0) //New data added at the end

          //New table array
          val table0 = cassette.table.indexOf(0)
          val oldTable = Array.fill[Int](table0)(0)
          cassette.table.copyToArray(oldTable, 0, table0)
          val newTable = oldTable ++ Array[Int](id) ++ Array[Int](data.length) ++ Array.fill[Int](200-oldTable.length-2)(0) //New data added at the end

          val newCassette = Cassette(newData, newTable) //Creation of a new cassette with new data
          writeFile(newCassette, "memory.txt") //Update the memory file
          println("Data successfully added!\n")
        }

        else { println("Not enough memory left to store this data! (Required: " + data.length + " | Available: " + memoryLeft(cassette) + ")\n") }
      }
      else { println("This id already exists!\n") }
    }
    else { println("Data's ID can't be 0!\n") }
  }
}