package typeclasses.solo.marshalling

import typeclasses.solo.Order
import typeclasses.solo.Person

object XmlMarshalling {
    fun forPerson(): Marshaller<Person> = PersonXmlMarshaller
    fun forOrder(): Marshaller<Order> = OrderXmlMarshaller

    object PersonXmlMarshaller: Marshaller<Person> {
        override fun Person.marshal(): String {
            return "<person>${this.name}</person>"
        }

        override fun Person.unmarshal(input: String): Person {
            val regex = "<person>(.+)</person>".toRegex()
            val name = regex.find(input)?.groupValues?.get(1)
            return Person(name ?: "unknown")
        }
    }

    object OrderXmlMarshaller: Marshaller<Order> {
        override fun Order.marshal(): String {
            return "<order>${this.value}</order>"
        }

        override fun Order.unmarshal(input: String): Order {
            val regex = "<order>(.+)</order>".toRegex()
            val value = regex.find(input)?.groupValues?.get(1)?.toDouble()
            return Order(value ?: 0.0)
        }
    }
}
