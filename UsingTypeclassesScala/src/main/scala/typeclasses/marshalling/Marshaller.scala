package typeclasses.marshalling

trait Marshaller[T] {
  def marshal(input: T): String
  def unmarshal(input: String): T
}
