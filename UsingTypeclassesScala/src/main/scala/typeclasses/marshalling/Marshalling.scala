package typeclasses.marshalling

object Marshalling {
  implicit class JsonMarshallingOps[T](value: T) {
    def marshall(implicit m: Marshaller[T]): String = m.marshall(value)
    def unmarshall(input: String)(implicit m: Marshaller[T]): T = m.unmarshall(input)
  }
}
