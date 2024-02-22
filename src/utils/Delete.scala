package utils

import entity.Cassette

import File.writeFile
import MemoryCheck.listDataIds
import Read.bytesBeforeData

object Delete {
  
  def deleteById(cassette:Cassette, id:Int): Unit = { //Delete a data
    val dataLength = cassette.table(listDataIds(cassette).indexOf(id)*2+1) //Get data length
    val bbd = bytesBeforeData(cassette, id) //Get the number of byte before the data in the storage
    val idIndex = listDataIds(cassette).indexOf(id)*2 //Index of data's id in the table
    val lengthIndex = idIndex+1 //Index of data's length in the table

    val oldData1 = Array.fill[Int](bbd)(0)
    val oldData2 = cassette.data.slice(bbd+dataLength, 800)
    cassette.data.copyToArray(oldData1, 0, bbd)
    val newData = oldData1 ++ oldData2 ++ Array.fill[Int](dataLength)(0) //Create the new data array with defragmentation

    val oldTable1 = Array.fill[Int](idIndex)(0)
    val oldTable2 = cassette.table.slice(lengthIndex+1, 200)
    cassette.table.copyToArray(oldTable1, 0, idIndex)
    val newTable = oldTable1 ++ oldTable2 ++ Array[Int](0, 0) ////Create the new table array with defragmentation

    val newCassette = Cassette(newData, newTable) //Create a new cassette with the updated data 
    writeFile(newCassette, "memory.txt") //Update the memory file
  }

  def deleteAll(): Unit = { //Wipe all data and create a new memory file filled with zeros
    val emptyCassette = Cassette()
    writeFile(emptyCassette, "memory.txt")
  }
}