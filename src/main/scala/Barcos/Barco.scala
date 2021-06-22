package Barcos
/*
Definimos esta clase como abstract como tipo genérico de Barco
Con las funciones zarpar y atracar ya que todos los barcos tienen como mínimo de poder hacer estas funciones
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