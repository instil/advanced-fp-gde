package typeclasses

import typeclasses.marshalling.Marshalling._
import typeclasses.marshalling.XmlMarshallingInstances._

object XmlProgram {
  def main(args: Array[String]): Unit = {
    val person1 = new Person("Jane")
    val order1 = new Order(123.45)

    val text1 = person1.marshall
    println(text1)

    val person2 = person1.unmarshall("<person>Dave</person>")
    println(person2)

    val text2 = order1.marshall
    println(text2)

    val order2 = order1.unmarshall("<order>456.78</order>")
    println(order2)
  }
}
