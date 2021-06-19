import Barcos.Buque
import elementosCarga.{ContenedorCarga, Producto}
import excepciones.{AnyProducts, ContainerExists, ContainerNotExists, LeftOverContainers}
import puerto.Puerto

object Maersk {

  var flota:List[Buque]=List[Buque]()
  var puertos:List[Puerto]=List[Puerto]()

  def crearFloata(): Unit ={
    val Buque1= new Buque("Marino", "SX456")
    val Buque2  = new Buque("Cargador","SX56231")
    val Buque3= new Buque("Marino2", "SX45312")
    val Buque4  = new Buque("Juana","SX5412")
    flota=List[Buque](Buque1,Buque2,Buque3,Buque4)
  }

  def inicializarPuertos(): Unit ={
    val puerto1 = new Puerto("New York PORT","New York")
    puerto1.entradaDeBarco(this.flota(0))

    val puerto2 = new Puerto("Barcelona PORT","Barcelona")
    puerto2.entradaDeBarco(this.flota(1))
    val puerto3 = new Puerto("Sanfrancisco PORT","San Francisco")
    puerto3.entradaDeBarco(this.flota(2))
    val puerto4 = new Puerto("Nante PORT","Nante")
    puerto4.entradaDeBarco(this.flota(3))
    this.puertos=List[Puerto](puerto1,puerto2,puerto3,puerto4)
  }

  def llenarContenedores(pL:List[Producto],nContenedores:Int):List[ContenedorCarga] ={
    //recorre llista segons index fer un match
    val prod:List[Producto] =pL
    var lC: List[ContenedorCarga] = List[ContenedorCarga]()
    prod match{
      case Nil=> throw AnyProducts()
      case l if(l.length<=nContenedores) => throw LeftOverContainers(nContenedores)
      case _ => {
        var aux: List[Producto] = List[Producto]()

        var i:Int=0
        pL.foreach((producto) => {
          i+=1
          aux = producto :: aux
          if ((i.toFloat/ nContenedores.toFloat ) == 1) {
            val id="ID-"+i.toString
            var c = new ContenedorCarga(aux, id)
            lC = c :: lC
            aux = List[Producto]()
          }
        })

        if (aux.length != 0) {
          val id="ID-"+pL.length.toString
          var lastC = new ContenedorCarga(aux, id)
          lC = lastC :: lC
        }

      }
    }
    lC
  }

  def main(args: Array[String]): Unit = {
    crearFloata()
    inicializarPuertos()
    var productos = List[Producto](Producto(nombre = "test", peso = 22f), Producto(nombre = "test", peso = 22f), Producto(nombre = "test", peso = 22f))
    var ruta1 = new RutaMaritima(this.puertos(0),this.puertos(1),this.flota(0),productos,2)
    ruta1.realizarRuta(llenarContenedores)
    var ruta2 = new RutaMaritima(this.puertos(2),this.puertos(3),this.flota(2),productos,5)
    ruta1.realizarRuta(llenarContenedores)
    var ruta3 = new RutaMaritima(this.puertos(2),this.puertos(3),this.flota(1),productos,5)
    ruta1.realizarRuta(llenarContenedores)

  }
}
