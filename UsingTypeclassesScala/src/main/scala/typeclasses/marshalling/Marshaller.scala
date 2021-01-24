package typeclasses.marshalling

trait Marshaller[T] {
  def marshall(input: T): String
  def unmarshall(input: String): T
}
