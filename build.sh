cd ./notification-service/
./gradlew build
wait

cd ../payment-service/
./gradlew build
wait

cd ../order-service/
./gradlew build
wait

docker compose up -d

exit