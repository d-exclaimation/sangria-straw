//
//  GraphQLInterface.scala
//  graphql-soda
//
//  Created by d-exclaimation on 2:19 PM.
//


package io.github.dexclaimation.graphqlSoda.schema

import sangria.schema.InterfaceType.emptyPossibleTypes
import sangria.schema.{Field, InterfaceType, PossibleInterface}

import scala.reflect.ClassTag

/**
 * Soda Implementable Interface Type definition.
 *
 * Help to avoid nesting, avoid prefixing with `Types`
 *
 * Accessing the InterfaceType from `t: InterfaceType` properties
 *
 * @param name Name of the Interface.
 * @tparam Ctx Context type for this Interface.
 * @tparam Val Value paired for this Interface (*best to implement this on a case class's companion object)
 */
abstract class SodaInterfaceType[Ctx, Val: ClassTag](name: String) {
  def description: String = ""

  def definition: List[Field[Ctx, Val]]

  def implement: List[PossibleInterface[Ctx, Val]] = Nil

  /**
   * Sangria InterfaceType derivation.
   */
  def t: InterfaceType[Ctx, Val] = InterfaceType(
    name = name,
    description = Some(description),
    fieldsFn = () => definition,
    interfaces = implement.map(_.interfaceType),
    manualPossibleTypes = emptyPossibleTypes,
    astDirectives = Vector.empty,
    astNodes = Vector.empty
  )
}