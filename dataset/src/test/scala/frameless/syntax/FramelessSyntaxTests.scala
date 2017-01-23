package frameless.syntax

import frameless.{TypedDataset, TypedDatasetSuite, TypedEncoder, X2}
import org.scalacheck.Prop
import org.scalacheck.Prop._

class FramelessSyntaxTests extends TypedDatasetSuite {

  def prop[A, B](data: Vector[X2[A, B]])(
    implicit ev: TypedEncoder[X2[A, B]]
  ): Prop = {
    val dataset = TypedDataset.create(data).dataset
    val dataframe = dataset.toDF()

    dataset.typed.collect().run().toVector ?= dataframe.toTyped[X2[A, B]].collect().run().toVector
  }

  test("dataset typed - toTyped") {
    check(forAll(prop[Int, String] _))
  }

}
