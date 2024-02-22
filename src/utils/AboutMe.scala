package utils

import utils.File.{readFile, readLines}

object AboutMe {

  def aboutMe(): Unit = {
    val am = readFile("AboutMe.md")
    readLines(am)
  }

}