package main

import (
  "fmt"
  "math/rand"
  "time"
)

func main() {
  done := make(chan bool)
  for i := 1; i <= 3; i++ {
    go printNum(i, done)
  }
  for i := 1; i <= 3; i++ {
    <-done
  }
}

func printNum(i int, done chan bool) {
  random := rand.Intn(3)
  time.Sleep(time.Duration(random) * time.Second)
  fmt.Printf("%d", i)
  done <- true
}
