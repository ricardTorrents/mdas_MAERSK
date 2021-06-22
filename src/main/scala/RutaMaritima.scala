import Barcos.{Barco, Buque}
import elementosCarga.{ContenedorCarga, Producto}
import excepciones.{ContainerExists, ContainerNotExists, ShipIsNotInThisPort, ShipIsYetInThisPort}
import puerto.Puerto
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


/*
Clase donde definimos la ruta maritima que iniciará un barco
 */

class RutaMaritima(pI:Puerto,pF:Puerto,b:Buque,lP:List[Producto],nC:Int) {
  val puertoInicio:Puerto=pI
  val puertoFinal:Puerto=pF
  val listaProductos:List[Producto]=lP
  val n_contenedores:Int = nC
  var barco:Buque=b



  private def cargarBarco(contenedores:List[ContenedorCarga]): Unit ={
    contenedores.foreach((contenedor)=>{
      try this.b.cargaContenedor(contenedor)
      catch {
        case e:ContainerExists => throw e
      }
    })
  }

  private def iniciarRuta(): Unit ={

    try puertoInicio.salidaDeBarco(b)
    catch {
      case e:ShipIsNotInThisPort => throw e
    }
  }
  def navegar():Future[Int]=Future {
    val r = scala.util.Random
    val navigationTime = r.nextInt(9)*1000
    Thread.sleep(navigationTime)
    navigationTime
  }


  private def finalizarRuta():Unit={
    try  puertoFinal.entradaDeBarco(b)
    catch {
      case e:ShipIsYetInThisPort => throw e
    }
  }
  private def descargarBarco(contenedores:List[ContenedorCarga]): Unit ={
    contenedores.foreach((contenedor)=>{
      try this.b.descargaContenedor(contenedor)
      catch {
        case e:ContainerNotExists => throw e
      }
    })
  }
  def realizarRuta(llenarContenedores:(List[Producto],Int)=>List[ContenedorCarga]):Unit ={
      val contenedores:List[ContenedorCarga]=llenarContenedores(this.listaProductos,this.n_contenedores)

      try {
        println(s"Cargando el barco: ${barco.nombre}")
        cargarBarco(contenedores)
        var carga=this.barco.carga
        println(s"El barco ha sido cargado con la siguiente carga: $carga")
        println("Iniciando ruta")
        iniciarRuta()
        val tiempoNav= navegar()
        val result = for {
          r1 <- tiempoNav
        } yield (r1)
        result.onComplete {
          case Success(value)=>{
            val tNav=value/1000
            println(s"El tiempo de navegación es de $tNav segundos")
            finalizarRuta()
            println("Se ha llegado al puerto destino")
            descargarBarco((contenedores))
            carga=this.barco.carga
            println(s"Carga del barco después de la descarga : $carga")
          }
          case Failure(ex)=>println("El barco ha naufragado.")
        }// AWait
        Thread.sleep(10000)

      }catch {
        case eCN:ContainerNotExists => println(eCN)
        case eSIY:ShipIsYetInThisPort => println(eSIY)
        case eSiNI:ShipIsNotInThisPort => println(eSiNI)
        case eCE:ContainerExists => println(eCE)
        case _ => println("Error")
      }finally {
        println("Ruta finalizada ")
      }
  }
}
