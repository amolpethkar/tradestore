# TradeStore
TradeStore repository 

### Build and deployment
mvn clean install

### Building and deploying Docker Image
Build docker image
docker build -t trade-store:trade-v1 .

Run docker image
docker run --name Trade-Store -p 8080:8080 trade-store:trade-v1

### Posting Trades
Post trades using Postman : Example

curl --location --request POST 'http://[IP:PORT]/trade' \
--header 'Content-Type: application/json' \
--data-raw '{ "tradeId":"T3", "version":3, "counterParty":"CP-3",
"bookId": "B2", "createdDate":"2023-09-23", "maturityDate": "2023-09-26", "expiredFlag":"Y" }'

### Scheduler
Scheduler uses the cron expression which is currently set to 10min and can be configured to required time using application properties
0/10 * * * * ?



