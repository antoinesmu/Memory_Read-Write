package utils

import java.nio.charset.StandardCharsets
import scala.annotation.tailrec

object AsciiConvertor {

  def asciiToBytes(ascii:String) : Array[Int] = { //Convert a string to an array with ascii values inside
    ascii.map(_.toInt).toArray
  }

  def bytesToAscii(bytes:Array[Int]) : String = { //Convert an array of ascii values to a string
    new String(bytes.map(_.toByte), StandardCharsets.US_ASCII)
  }

  def printArray(array:Array[Int]): String = { //Method to print to values inside an array of integers
    printArrayRec(array, "", 0)
  }
  @tailrec
  def printArrayRec(array:Array[Int], output:String, index:Int): String = {
    if (index < array.length) {
      printArrayRec(array, output++(array(index).toString + " "), index+1)
    }
    else {
      output
    }
  }

}