package typeclasses.solo.marshalling

interface Marshaller<T> {
    fun T.marshal(): String
    fun T.unmarshal(input: String): T
}
