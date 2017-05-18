package com.revunit.techfest2017

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.revunit.techfest2017.ShoppingDomain._

import scala.concurrent.duration._
import scala.collection.mutable

class CoordinatorActor extends Actor with ActorLogging {
  import context._

  private case class PendingItem(shopper: ActorRef, item: Item)

  private val shoppers = mutable.Queue[ActorRef]()
  private val shoppingList = mutable.Queue[Item]()
  private val pendingPurchases = mutable.ArrayBuffer[PendingItem]()
  private val purchasedItems = mutable.ArrayBuffer[Item]()
  private var currentlyShopping = true

  override def preStart(): Unit = {
    shoppingList.enqueue(Item("Cherries"))
    shoppingList.enqueue(Item("Corn"))
    shoppingList.enqueue(Item("Sweet Potatoes"))

    context.actorOf(ShopperActor.props, "Roosevelt") ! PrepareToShop
    context.actorOf(ShopperActor.props, "Lincoln") ! PrepareToShop
  }

  def receive = {
  	case ReadyToShop => readyToShop(sender())
  	case RequestItem(item) => itemRequested(sender(), item)
    case BoughtItem(item) => boughtItem(sender(), item)
  }

  private def readyToShop(shopper:ActorRef) = {
    log.info(s"${shopper.path.name} is ready to shop.")
    shoppers.enqueue(shopper)
    tryToSendItem()
  }

  private def itemRequested(requester:ActorRef, item: Item) = {
    if (currentlyShopping) {
      log.info(s"Adding ${item.name} to the shopping list for ${requester.path.name}")
      shoppingList.enqueue(item)
      tryToSendItem()
    } else {
      log.info(s"Letting ${requester.path.name} know we are done shopping")
      requester ! DoneShopping
    }
  }

  private def boughtItem(shopper: ActorRef, item: Item) = {
    log.info(s"pendingPurchases ${pendingPurchases.length}")
    purchasedItems.append(item)
    pendingPurchases -= PendingItem(shopper, item)
    queueOrShutdownShopper(shopper)
  }

  def shutdown() = {
    context.system.terminate()
  }

  private def queueOrShutdownShopper(shopper:ActorRef){
    if (currentlyShopping) {
      readyToShop(shopper)
    } else {
      log.info(s"letting ${shopper.path.name} know we are done shopping")
      shopper ! DoneShopping

      if (pendingPurchases.isEmpty) {
        log.info("could shutdown here. no pending items and not shopping")
      }
    }
  }

  // called when we have an item added to the shopping list or a new worker shows up
  private def tryToSendItem() = {
    if (shoppingList.nonEmpty && shoppers.nonEmpty) {
      val item = shoppingList.dequeue()
      val shopper = shoppers.dequeue()
      pendingPurchases += PendingItem(shopper, item)
      log.info(s"Sending ${item.name} to ${shopper.path.name}")
      shopper ! BuyItem(item)
    }
    finishShoppingIfListIsComplete()
  }

  private def finishShoppingIfListIsComplete() = {
    if (shoppingList.isEmpty && currentlyShopping) {
      log.info("#################################################################")
      log.info("##### Finished shopping. List is empty. #########################")
      log.info("#################################################################")
      currentlyShopping = false
      shoppers.dequeueAll(_ => true).foreach(_ ! DoneShopping)
    }
  }
}

object CoordinatorActor {
  val props: Props = Props[CoordinatorActor]
}