package com.revunit.techfest2017

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import ShoppingDomain._

import scala.concurrent.duration._
import scala.util.Random

class ShopperActor extends Actor with ActorLogging {

  var shoppingForItem:Option[Item] = None
  import context._

  def receive = {
    case PrepareToShop => prepareToShop(sender())
  	case BuyItem(item) => buyItem(item, sender())
    case DoneShopping => doneShopping()
  }

  private def prepareToShop(ref: ActorRef) = {
    log.info("Asked to prepare for shopping")
    ref ! ReadyToShop
  }

  private def buyItem(item: Item, sender: ActorRef): Unit = {
    log.info(s"Asked to buy $item")
    if (shoppingForItem.isEmpty) {
      shoppingForItem = Some(item)
      val shoppingTime = Random.nextInt(5) + 2
      system.scheduler.scheduleOnce(shoppingTime.seconds) {
        shoppingForItem = None
        log.info(s"Bought item $item")
        sender ! BoughtItem(item)
      }
    }
  }

  private def doneShopping() = {
    log.info("Done shopping. Stopping shopper")
    context.stop(self)
  }
}

object ShopperActor {
  def props = Props[ShopperActor]
}
