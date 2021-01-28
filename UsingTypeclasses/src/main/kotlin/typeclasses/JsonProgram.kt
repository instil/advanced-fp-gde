package typeclasses

import typeclasses.marshalling.JsonMarshalling

fun main() {
    val person1 = Person("Jane")
    val order1 = Order(123.45)

    JsonMarshalling.forPerson().run {
        val text1 = person1.marshal()
        println(text1)

        val person2 = person1.unmarshal("{ \"person\": \"Dave\" }")
        println(person2)
    }

    JsonMarshalling.forOrder().run {
        val text2 = order1.marshal()
        println(text2)

        val order2 = order1.unmarshal("{ \"order\": 456.78 }")
        println(order2)
    }
}
