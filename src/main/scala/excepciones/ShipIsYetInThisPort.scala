package excepciones

case class ShipIsYetInThisPort(matricula:String) extends Exception(s"El barco $matricula ya esta en este puerto" )
