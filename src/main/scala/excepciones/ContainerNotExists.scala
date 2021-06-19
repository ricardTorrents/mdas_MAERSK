package excepciones

case class ContainerNotExists(id:String) extends Exception(s"El contenedor $id no existe" )
