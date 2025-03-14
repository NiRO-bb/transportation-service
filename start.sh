#!/bin/bash/

cd server

mvn clean package

java -jar target/*.jar
