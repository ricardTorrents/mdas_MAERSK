package excepciones

case class ContainerExists(id:String) extends Exception(s"El contenedor $id ya existe")
