package com.revunit.techfest2017

object ShoppingDomain {
  case class Item(name:String)

  case object PrepareToShop
  case object ReadyToShop
  case object DoneShopping
  case class BuyItem(item: Item)
  case class BoughtItem(item: Item)
  case class RequestItem(item: Item)
}
