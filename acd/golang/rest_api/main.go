package main

import (
	"log"

	"github.com/gin-gonic/gin"
)

func main(){
	router := gin.Default()

	router.GET("/",homepage)

	err := router.Run()
	
	if err != nil{
		log.Fatal("Server is not running")
	}
}

func  homepage(c *gin.Context){
	c.JSON(200,gin.H{
		"message":"hello world",
	})
}