package main

import (
	"fmt"
	"time"
)

type Pizza struct {
	kind string
}

type Friend struct {
	name string
}

func main() {
	hangout()
}

func callInPizzaDelivery(kind string, done chan Pizza) {
	fmt.Printf("calling in pizza %s\n", kind)
	time.Sleep(5 * time.Second)
	done <- Pizza{kind: kind}
}

func inviteFriendOver(name string, done chan Friend) {
	fmt.Printf("calling friend %s\n", name)
	time.Sleep(3 * time.Second)
	done <- Friend{name: name}
}

func hangout() {
	friendChannel := make(chan Friend)
	pizzaChannel := make(chan Pizza)

	go callInPizzaDelivery("pepperoni", pizzaChannel)
	go inviteFriendOver("Jack", friendChannel)

	var pizza Pizza
	var friend Friend
	for i := 0; i < 2; i++ {
		select {
		case friend = <-friendChannel:
		case pizza = <-pizzaChannel:
		}
	}

	fmt.Printf("Hanging out with %s eating a %s pizza.\n", friend.name, pizza.kind)
}
