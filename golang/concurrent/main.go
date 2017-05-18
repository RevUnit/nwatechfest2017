package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	for i := 1; i <= 3; i++ {
		go printNum(i)
	}

	var input string
	fmt.Scanln(&input)
}

func printNum(i int) {
	random := rand.Intn(3)
	time.Sleep(time.Duration(random) * time.Second)
	fmt.Printf("%d", i)
}
