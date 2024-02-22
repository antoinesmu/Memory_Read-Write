package utils

import entity.Cassette

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.FileSystem
import scala.annotation.tailrec
import scala.io.Source.fromFile

object File {
  lazy val executionPath = getClass.getProtectionDomain.getCodeSource.getLocation.toURI.getPath
  lazy val path = executionPath.replaceAll("/Projet.jar", "").replaceFirst("/", "")+"/"

  def readFile(fileName:String) : Array[String] = { //Read the memory file
    val source = fromFile(path + fileName)
    val lines = try {
      source.getLines.toArray
    } finally {
      source.close() //Close the file
    }
    lines
  }

  def writeFile(cassette:Cassette, memoryFileName:String): Unit = { //Write into the memory file
    val tempFile = new File(path+"tempMemory.txt") //Create a temporary file
    val oldFile = new File(path+memoryFileName)
    val bw = new BufferedWriter(new FileWriter(tempFile))
    writeLine(bw, cassette, 0) //Write data into the temporary file
    bw.close() //Close the file
    if (oldFile.exists) {
      oldFile.delete() //Delete the old file if it exists
    }
    tempFile.renameTo(new File(path+memoryFileName)) //Rename the memory file to default name: "memory.txt"
  }

  @tailrec
  def writeLine(bw:BufferedWriter, cassette:Cassette, index:Int): Unit = { //Write a single line into a file
    if (index<50) {
      bw.write(lineFromCassette(cassette, "", index*20, 0))
      writeLine(bw, cassette, index+1)
    }
  }

  def readLines(file:Array[String]): Unit = {
    readLinesRec(file, 0)
  }
  @tailrec
  def readLinesRec(file:Array[String], index:Int): Unit = {
    if (index<file.length) {
      println(file(index))
      readLinesRec(file, index+1)
    }
  }

  @tailrec
  def dataFromReadFile(file:Array[String], output:String, index:Int) : Array[Int] = { //Create data array from a read file
    if(index < 40) {
      dataFromReadFile(file, output++file(index), index+1)
    }
    else {
      output.split(",").map(_.toInt)
    }
  }

  @tailrec
  def tableFromReadFile(file:Array[String], output:String, index:Int) : Array[Int] = { //Create able array from a read file
    if(index < 50) {
      tableFromReadFile(file, output++file(index), index+1)
    }
    else {
      output.split(",").map(_.toInt)
    }
  }

  def cassetteFromFile(fileName:String) : Cassette = { //Create a cassette object from a read file
    Cassette(dataFromReadFile(readFile(fileName), "", 0), tableFromReadFile(readFile(fileName), "", 40))
  }

  @tailrec
  def lineFromCassette(cassette:Cassette, output:String, index:Int, count:Int): String = { //Convert cassette's array into a string to be written in a file
    if (count<20) {
      lineFromCassette(cassette, output+cassette.getElement(cassette, index).toString+",", index+1, count+1)
    }
    else {
      output+"\n"
    }
  }

}