package main

import (
  "fmt"
  "time"
)

type Pizza struct {
  brand string
}

func main() {
  makeLunch()
}

func cookPizza(pizza Pizza, pizzaDone chan Pizza) {
  fmt.Println("put pizza in oven")
  time.Sleep(time.Duration(5) * time.Second)
  fmt.Println("oven timer sounded")
  pizzaDone <- pizza
}

func preparePizzaAndOven() Pizza {
  pizza := Pizza{brand: "Digiorno"}
  fmt.Println("prepared pizza")
  return pizza
}

func makeSalad() {
  fmt.Println("made salad")
}

func removePizzaAndSliceIt(pizza Pizza) {
  fmt.Printf("removed %s pizza and sliced it.", pizza.brand)
}

func makeLunch() {
  pizzaDone := make(chan Pizza)
  pizza := preparePizzaAndOven()
  go cookPizza(pizza, pizzaDone)
  makeSalad()
  cookedPizza := <-pizzaDone
  removePizzaAndSliceIt(cookedPizza)
}
