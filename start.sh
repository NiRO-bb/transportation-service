#!/bin/bash/

cd server

# сборка проекта
mvn clean package

cd ..

# остановка рабочего docker-контейнера
docker stop $(docker ps -q)

# сборка docker-образа
docker build -t transportation_service .

# запуск docker-контейнера
docker run -p 8080:8080 transportation_service