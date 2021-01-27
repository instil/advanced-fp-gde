package using.kleisli

import scalaz.Kleisli
import scalaz.Scalaz.eitherMonad

object Program {
  def propertyViaJVM(key: String): Either[String, String] = {
    val result = System.getProperty(key)
    if (result != null) {
      Right(result)
    } else {
      Left(s"No JVM property: $key")
    }
  }

  def addProperties(items: String*) {
    items.reduce((last: String, current: String) => {
        System.setProperty(last, current)
        current
      })
  }

  def addAtreidesLineage(): Unit = addProperties(
    "Vladimir",
    "Jessica",
    "Paul",
    "Ghanima",
    "Moneo",
    "Siona"
  )

  def breakLineage(): Unit = System.setProperty("Jessica", "Alia")

  private def demoKleisli() {
    val kleisli = Kleisli(propertyViaJVM)
    val composition = kleisli >=> kleisli >=> kleisli >=> kleisli >=> kleisli

    val result = composition
      .map(_ + " Atreides")
      .run("Vladimir")

    println(result)
  }

  def main(args: Array[String]): Unit = {
    addAtreidesLineage()
    demoKleisli()

    breakLineage()
    demoKleisli()
  }
}
