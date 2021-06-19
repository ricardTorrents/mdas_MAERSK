package Barcos
/*
Definimos esta classe como abstract como tipo geneerico de Barco
Con las funciones zarpar y amarrar ya que todos los barcos tienen como minimo de poder hacer estas funciones
 */
 abstract class Barco{
  val nombre:String
  val matricula:String
  var navegando:Boolean

   def zarpar(): Unit ={
     navegando=true
   }
   def atracar(): Unit ={
     navegando=false
   }
}