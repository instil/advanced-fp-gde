package typeclasses.solo

import typeclasses.solo.marshalling.XmlMarshalling

fun main() {
    val person1 = Person("Jane")
    val order1 = Order(123.45)

    XmlMarshalling.forPerson().run {
        val text1 = person1.marshal()
        println(text1)

        val person2 = person1.unmarshal("<person>Dave</person>")
        println(person2)
    }

    XmlMarshalling.forOrder().run {
        val text2 = order1.marshal()
        println(text2)

        val order2 = order1.unmarshal("<order>456.78</order>")
        println(order2)
    }
}
