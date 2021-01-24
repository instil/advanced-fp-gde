package typeclasses.marshalling

import typeclasses.Order
import typeclasses.Person

object JsonMarshalling {
    fun forPerson(): Marshaller<Person> = PersonJsonMarshaller
    fun forOrder(): Marshaller<Order> = OrderJsonMarshaller

    private object PersonJsonMarshaller: Marshaller<Person> {
        override fun Person.marshall(): String {
            return "{ \"person\": ${this.name} }"
        }

        override fun Person.unmarshall(input: String): Person {
            val regex = "\\{ \"person\": \"(.+)\" }".toRegex()
            val name = regex.find(input)?.groupValues?.get(1)
            return Person(name ?: "unknown")
        }
    }

    private object OrderJsonMarshaller: Marshaller<Order> {
        override fun Order.marshall(): String {
            return "{ \"order\": ${this.value} }"
        }

        override fun Order.unmarshall(input: String): Order {
            val regex = "\\{ \"order\": (.+) }".toRegex()
            val value = regex.find(input)?.groupValues?.get(1)?.toDouble()
            return Order(value ?: 0.0)
        }
    }
}
