package typeclasses

class Person(var name: String) {
  override def toString = s"Person called $name"
}

class Order(var value: Double) {
  override def toString = s"Order of value $value"
}
