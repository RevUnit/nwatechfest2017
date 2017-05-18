import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object MakeLunch extends App {

  case class Pizza(brand: String)

   def cookPizza(pizza: Pizza): Future[Pizza] = {
    println("put pizza in oven")
    Future {
      blocking {
        Thread.sleep(5000)
        println("oven timer sounded")
        pizza
      }
    }
  }

  def preparePizzaAndOven(): Pizza = {
    println("prepared pizza")
    Pizza("Digiorno")
  }

  def makeSalad(): Unit = println("make salad")

  def removePizzaAndSliceIt(cookedPizza: Pizza): Unit =
    println(s"removed ${cookedPizza.brand} pizza and sliced it")

  def makeLunch(): Unit = {
    val pizza = preparePizzaAndOven()
    cookPizza(pizza).foreach(removePizzaAndSliceIt)
    makeSalad()
  }

  makeLunch()
  scala.io.StdIn.readChar()
}


