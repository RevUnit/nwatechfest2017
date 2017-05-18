package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	for i := 1; i <= 5; i++ {
		printNum(i)
	}
}

func printNum(i int) {
	random := rand.Intn(3)
	time.Sleep(time.Duration(random) * time.Second)
	fmt.Print(i)
}
