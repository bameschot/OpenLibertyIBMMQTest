# OpenLibertyIBMMQTest
Test project for Java EE 8, IBM MQ, Open Liberty, Message Driven Beans

# Start the ibm mq docker image by running
./startmq.sh script from the ibmmqsetup folder

this exposes the admin console on:
https://localhost:9443/ibmmq/console/login.html?error

# start the application by running 
mvn liberty:dev from the root project folder

this exposes a the rest interfaces and an OpenApi UI on:
localhost:9080/openapi/ui/
