package typeclasses.marshalling

import typeclasses.Order
import typeclasses.Person

object JsonMarshalling {
    fun forPerson(): Marshaller<Person> = PersonJsonMarshaller
    fun forOrder(): Marshaller<Order> = OrderJsonMarshaller

    private object PersonJsonMarshaller: Marshaller<Person> {
        override fun Person.marshal(): String {
            return "{ \"person\": ${this.name} }"
        }

        override fun Person.unmarshal(input: String): Person {
            val regex = "\\{ \"person\": \"(.+)\" }".toRegex()
            val name = regex.find(input)?.groupValues?.get(1)
            return Person(name ?: "unknown")
        }
    }

    private object OrderJsonMarshaller: Marshaller<Order> {
        override fun Order.marshal(): String {
            return "{ \"order\": ${this.value} }"
        }

        override fun Order.unmarshal(input: String): Order {
            val regex = "\\{ \"order\": (.+) }".toRegex()
            val value = regex.find(input)?.groupValues?.get(1)?.toDouble()
            return Order(value ?: 0.0)
        }
    }
}
