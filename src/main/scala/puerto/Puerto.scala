package puerto

import Barcos.Barco
import excepciones.{ShipIsNotInThisPort, ShipIsYetInThisPort}

/*
Clase donde definimos el nombre del Puerto y su ubicación
 */
class Puerto(n:String,s:String){
  val nombre:String=n
  val situacion:String=s
  var barcos_atracados:List[Barco]=List[Barco]()

  def salidaDeBarco(b:Barco): Unit ={
    if(this.barcos_atracados.find(element => {
      b.matricula == element.matricula
    })==None) throw ShipIsNotInThisPort(b.matricula)
    else{
      this.barcos_atracados=this.barcos_atracados.filter((element)=> element.matricula != b.matricula)
      b.zarpar()
    }

  }

  def entradaDeBarco(b:Barco):Unit={
    if(this.barcos_atracados.find(element => {
      b.matricula == element.matricula
    })==None) {
      this.barcos_atracados = b::this.barcos_atracados
      b.atracar()
    }
    else throw ShipIsYetInThisPort(b.matricula)


  }
}
