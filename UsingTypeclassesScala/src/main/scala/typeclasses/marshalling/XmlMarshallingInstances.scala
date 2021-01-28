package typeclasses.marshalling

import typeclasses.{Order, Person}

object XmlMarshallingInstances {
  implicit val personXmlMarshaller: Marshaller[Person] = new Marshaller[Person] {
    override def marshal(input: Person) = s"<person>${input.name}</person>"

    override def unmarshal(input: String): Person = {
      val regex = "<person>(.+)</person>".r
      val name: String = regex
        .findFirstMatchIn(input)
        .map(_.group(1))
        .getOrElse("unknown")
      new Person(name)
    }
  }

  implicit val orderXmlMarshaller: Marshaller[Order] = new Marshaller[Order] {
    override def marshal(input: Order) = s"<order>${input.value}</order>"

    override def unmarshal(input: String): Order = {
      val regex = "<order>(.+)</order>".r
      val value = regex
        .findFirstMatchIn(input)
        .map(_.group(1))
        .map(_.toDouble)
        .getOrElse(0.0)
      new Order(value)
    }
  }
}
