package typeclasses.marshalling

object Marshalling {
  implicit class JsonMarshallingOps[T](value: T) {
    def marshal(implicit m: Marshaller[T]): String = m.marshal(value)
    def unmarshal(input: String)(implicit m: Marshaller[T]): T = m.unmarshal(input)
  }
}
