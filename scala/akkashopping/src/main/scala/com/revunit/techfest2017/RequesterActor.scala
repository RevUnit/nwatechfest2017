package com.revunit.techfest2017

import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, Props}
import com.revunit.techfest2017.ShoppingDomain._

import scala.collection.mutable
import scala.concurrent.duration._

class RequesterActor(val coordinatorActor: ActorRef) extends Actor with ActorLogging {
  import context._

  case object AskForItem

  private val favoriteItems = mutable.Queue[Item]()

  override def preStart(): Unit = {
    favoriteItems.enqueue(Item("waffles"))
    favoriteItems.enqueue(Item("ice cream"))
    system.scheduler.scheduleOnce(2.seconds, self, AskForItem)
  }

  def receive = {
    case AskForItem => askForItem()
    case DoneShopping => doneShopping()
  }

  def askForItem() = {
    if (favoriteItems.nonEmpty) {
      val item = favoriteItems.dequeue()
      log.info(s"Requesting ${item.name}")
      coordinatorActor ! RequestItem(item)
      if (favoriteItems.nonEmpty) {
        system.scheduler.scheduleOnce(10.seconds, self, AskForItem)
      }
    } else {
      shutdown("out of items to request")
    }
  }

  def doneShopping() = {
    shutdown("we are done shopping")
  }

  def shutdown(reason: String) = {
    log.info(s"Shutting down requester. $reason")
    context.stop(self)
  }
}

object RequesterActor {
  def props(coordinatorActor: ActorRef) = {
    Props(new RequesterActor(coordinatorActor))
  }
}
