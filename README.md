# OpenLibertyIBMMQTest
Test project for Java EE 8, IBM MQ, Open Liberty, Message Driven Beans

# Start the ibm mq docker image by running
./startmq.sh script from the ibmmqsetup folder

this exposes the admin console on:
https://localhost:9443/ibmmq/console/login.html?error

# Start the application by running 
mvn liberty:dev from the root project folder

this exposes a the rest interfaces and an OpenApi UI on:
https://localhost:9080/openapi/ui/

# The application is setup to work as follows

The application receives a message on an in queue (Q.TEST.IN), processes the message by adding the amount to the total of the account (or creating a new one) and puts the new balance of the account on the out queue (Q.TEST.OUT)

There are three REST operations to interact with the system
1. POST api/accounts/send-amount; this operation receives an account and amount as json and transforms it into an xml message that is put in the Q.TEST.IN
    The message format is:
    {
      "account": "string",
      "amount": 0
    }
2. GET api/accounts/receive-total; this operation receives the account total update messages as xml from the Q.TEST.OUT queue and transforms it into a json.
    The message format is:
    {
      "account": "pangs",
      "total": 50
    }
3. POST api/accounts/account-overview; this operation returns an overview of the accounts and ammounts stored directly in the service.  
    {
      "pangs": 50,
      "kitty": 40
    }

