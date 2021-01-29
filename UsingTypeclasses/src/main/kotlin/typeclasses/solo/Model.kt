package typeclasses.solo

class Person(val name: String) {
    override fun toString() = "Person called $name"
}

class Order(val value: Double) {
    override fun toString() = "Order of value $value"
}
