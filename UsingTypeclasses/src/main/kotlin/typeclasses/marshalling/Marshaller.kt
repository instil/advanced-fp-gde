package typeclasses.marshalling

interface Marshaller<T> {
    fun T.marshall(): String
    fun T.unmarshall(input: String): T
}
