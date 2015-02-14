package ildl.plugin
package metadata

import scala.tools.nsc.plugins.PluginComponent
import scala.collection.immutable.ListMap

trait ildlDefinitions {
  this: ildlHelperComponent =>

  import global._
  import definitions._

  lazy val ildlPackageObjectSymbol = rootMirror.getPackageObject("ildl")
  lazy val idllAdrtSymbol = definitions.getMemberMethod(ildlPackageObjectSymbol, TermName("adrt"))
}
