# scala-bank-account [![Build Status](https://travis-ci.org/damintu/scala-bank-account.svg?branch=master)](https://travis-ci.org/damintu/scala-bank-account)

## Objectif


### Illustrer la maîtrise des frameworks dans une mini application fullstack

    Software Factory (outil de build + docker)
    Framework front + back
    BDD (Behavior Driven Development)
    
BankAccount:https://gist.github.com/abachar/d20bdcd07dac589feef8ef21b487648c

Implementation : PlayFramework/Scala

### Stories to implement :

    account creation
    deposit with amount >0
    deposit with amount <= 0
    withdrawal with amount >0
    withdrawal with amount <= 0
    list of operations
    
## Run it with docker !
simply run 

    docker-compose up -d
    
and connect to localhost:9100

## To run the project simply run 

    sbt run
    
## To launch the tests 

    sbt test

## API : 
To browse the exposed API see [https://documenter.getpostman.com/view/4165901/RztiuVo8](https://documenter.getpostman.com/view/4165901/RztiuVo8)
