package Barcos

import elementosCarga.ContenedorCarga
import excepciones.{ContainerExists, ContainerNotExists}




/*
* Definimos buque como un tipo de barco que puede cargar cosas
* Por lo tanto definimos su carga y funciones para cargar y descargar los contenedores.
* */
class Buque (n:String,m:String) extends Barco{
  override val nombre = n
  override val matricula = m
  override var navegando = false
  var carga:List[ContenedorCarga]=List[ContenedorCarga]()


  def cargaContenedor(c:ContenedorCarga): Unit ={

    println(this.carga.find(element => {
      c.id == element.id
    }))
    if(this.carga.find(element => {
      c.id == element.id
    })==None) this.carga=c::carga
    else throw ContainerExists(c.id)
  }

  def descargaContenedor(c:ContenedorCarga): Unit ={
    if(this.carga.find(element => {
      c.id == element.id
    })==None) throw ContainerNotExists(c.id)
    else this.carga=this.carga.filter((element)=> element.id != c.id)
  }


}




