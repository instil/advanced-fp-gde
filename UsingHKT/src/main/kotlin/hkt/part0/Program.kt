package hkt.part0

import arrow.Kind
import arrow.core.*
import arrow.core.extensions.either.applicative.applicative
import arrow.core.extensions.list.traverse.traverse

fun main() {
    val data1 = (1..5).map { num -> Right(num) }
    output1(data1)

    val data2 = (1..5)
        .toList()
        .traverse(Either.applicative()) { num -> Right(num) }
    output2(data2.fix().map { it.fix() })
    output3(data2)
}

fun output1(input: List<Either<Int, Int>>) = println(input)

fun output2(input: Either<Int, List<Int>>) = println(input)

fun output3(input: Kind<EitherPartialOf<Nothing>, Kind<ForListK, Int>>) = println(input)

