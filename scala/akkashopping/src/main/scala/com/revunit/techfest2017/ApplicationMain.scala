package com.revunit.techfest2017

import akka.actor.ActorSystem

import scala.concurrent.Await

object ApplicationMain extends App {
  val system = ActorSystem("ShoppingActorSystem")
  val coordinatorActor = system.actorOf(CoordinatorActor.props, "Washington")
  val requesterActor = system.actorOf(RequesterActor.props(coordinatorActor), "Jefferson")
}