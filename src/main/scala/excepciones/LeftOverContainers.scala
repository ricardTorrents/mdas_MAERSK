package excepciones

case class LeftOverContainers(nContainers:Int) extends Exception(s"There are more containers than products ( n containers $nContainers)")
