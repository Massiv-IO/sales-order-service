# sales-order-service
Sales Order Service


 java -javaagent:/Users/jim/workspace/metaform/samples/sales/newrelic/order/newrelic.jar  -jar target/sales-order-serivce-1.0.0.jar --server.port=9001

 java -javaagent:[newrelic.jar] -jar target/sales-order-serivce-1.0.0.jar --server.port=9001


curl -H "Content-Type: application/json" -d '{"item":"1234","quantity":1}' localhost:9001/order/

