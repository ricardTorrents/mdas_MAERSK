package excepciones

case class ShipIsNotInThisPort(matricula:String) extends Exception(s"El barco $matricula no esta en estue puerto" )
