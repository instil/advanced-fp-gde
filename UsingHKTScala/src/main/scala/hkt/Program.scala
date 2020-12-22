package hkt

import cats.data._
import cats.data.Validated._

trait Competition[T[_,_]] {
  def judge[U, V](input: T[U, V]): String
}

object EitherCompetition extends Competition[Either] {
  override def judge[U, V](input: Either[U, V]): String = {
    if(input.isRight) "winner" else "loser"
  }
}

object ValidatedCompetition extends Competition[Validated] {
  override def judge[U, V](input: Validated[U, V]): String = {
    if(input.isValid) "winner" else "loser"
  }
}

object Program {
  def main(args: Array[String]): Unit = {
    val tst1 = Right(123)
    val tst2 = Left(456)
    val tst3 = Valid(123)
    val tst4 = Invalid(456)

    println(EitherCompetition.judge(tst1))
    println(EitherCompetition.judge(tst2))
    println(ValidatedCompetition.judge(tst3))
    println(ValidatedCompetition.judge(tst4))
  }
}
