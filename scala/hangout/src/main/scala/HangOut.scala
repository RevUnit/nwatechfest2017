import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object HangOut extends App {

   def callInPizzaDelivery(pizza: String): Future[String] = {
    println(s"calling in pizza $pizza")
    Future {
      blocking {
        Thread.sleep(5000)
        pizza
      }
    }
  }

  def inviteFriendOver(friend: String): Future[String] = {
    println(s"calling friend $friend")
    Future {
      blocking {
        Thread.sleep(5000)
        friend
      }
    }
  }

  def hangout(): Unit = {
    val pizzaFuture = callInPizzaDelivery("pepperoni")
    val friendFuture = inviteFriendOver("Jack")
    for {
      pizza <- pizzaFuture
      friend <- friendFuture
    } println(s"Hanging out with $friend eating a $pizza pizza.")
  }

  hangout()
  scala.io.StdIn.readChar()
}
