# Data processing pipeline

A demo project for implementation of a data processing pipeline. Consists of the folliwing components:
- A [Spring Boot](https://spring.io/projects/spring-boot) REST endpoint accepting a dummy object as JSON. It acts as producer and sends the object to a messaging system.
- The Messaging system [Apache Kafka](https://kafka.apache.org/)
- A [Spring Boot](https://spring.io/projects/spring-boot) consumer service which reads the messages from [Apache Kafka](https://kafka.apache.org/) and stores them in a Document database.
- The database [mongoDB](https://www.mongodb.com/)
- A [Spring Boot](https://spring.io/projects/spring-boot) REST endpoint which retrieves all stored objects from the database.

The above components are supposed to be run in separate [Docker](https://www.docker.com/) containers, so that they could be easily scaled and maintained.

The project is managed with [Apache Maven](https://maven.apache.org/). The three Spring Boot components are organized as sub-modules in the project so they could be built and run independently.

For the dummy object we will use a basic representation of a Vehicle object (i.e. used by Car sale engines) that will be transferred through the pipeline:

```
{
   "type": "car",
   "make": "BMW",
   "model": "X3",
   "mileage": 117000,
   "price": 18500,
   "currency": "EUR"
}
```

## Getting Started

The following instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

Assuming you have [git](https://git-scm.com/) installed on your machine, you should clone the project:

```
$ git clone https://github.com/denislav-mihov/pipeline
```

To be able to run the ensemble you will also need a docker machine running locally, so if you don't have one you should go to the official [Docker](https://www.docker.com/) site and get one.

### Installing

The project uses a [docker-compose.yml](https://github.com/denislav-mihov/pipeline/blob/master/docker-compose.yml) setup with the Dockerfile-s for each of the modules with maven build commands included in so there are only two steps to get the application up and running:

1. From the root folder of the project (the one containing the file docker-compose.yml) run the following:

```
$ docker-compose up --build -d
```

This should build the jar files, create the docker images and run the containers.

2. Create a database user for the application, for example using the following commands:

Connect to the mongoDB container:
```
$ docker exec -it mongodb bash
```

Then start mongo shell by using admin credentials:
```
# mongo --authenticationDatabase "admin" -u "admindb" -p "admindb"
```

Switch to "pipelinedb" database (that would be the one used by our application) and create a user "clientdb" with password "clientdb".
```
use pipelinedb
db.createUser(
  {
    user: "clientdb",
    pwd:  "clientdb",
    roles: [ { role: "readWrite", db: "pipelinedb" } ]
  }
)
```

If you prefer to use different credentials please update them accordingly in the corresponding application.properties files in the project.

You can check if all containers are up and runnig by typing:
```
$ docker-compose ps
```

If OK you should see something similar to this - a total of 6 containers:
```
  Name                 Command               State                         Ports
-------------------------------------------------------------------------------------------------------
consumer    java -jar consumer.jar --l ...   Up
kafka       start-kafka.sh                   Up      0.0.0.0:29092->29092/tcp, 0.0.0.0:9092->9092/tcp
mongodb     docker-entrypoint.sh mongo ...   Up      0.0.0.0:27017->27017/tcp
producer    java -jar producer.jar --l ...   Up      0.0.0.0:8081->8081/tcp
reader      java -jar reader.jar --log ...   Up      0.0.0.0:8082->8082/tcp
zookeeper   /bin/sh -c /usr/sbin/sshd  ...   Up      0.0.0.0:2181->2181/tcp, 22/tcp, 2888/tcp, 3888/tcp
```

### Initial test

Now you can test the application i.e. by simple curl requests.

Have in mind that for the two endpoints there is [basic access authentication](https://en.wikipedia.org/wiki/Basic_access_authentication) enabled and there is an in-memory authentication manager in the application which creates a default user with the following credentials:
```
api.username=user1
api.password=pass123
```

You should do a base64 encoding of the string "{username}:{password}" and put it in the request authorization header:
```
-H "Authorization: Basic dXNlcjE6cGFzczEyMw== "
```

Example curl request for the producer endpoint:
```
curl -X POST http://192.168.99.100:8081/pipeline/api/messages -H "Authorization: Basic dXNlcjE6cGFzczEyMw== " -H "Content-Type: application/json" -d "{\"type\":\"car\", \"make\":\"Ford\", \"model\":\"Mondeo\", \"mileage\":87000, \"price\":13000, \"currency\":\"EUR\"}"
```

Example curl request for the reader endpoint:
```
curl -X GET http://192.168.99.100:8082/pipeline/api/messages -H "Authorization: Basic dXNlcjE6cGFzczEyMw=="
```

In the above examples 192.168.99.100 is the IP of the local docker machine, you should change that with yours. The ports are configured to 8081 for the producer service and 8082 for the reader service, so unless you change them in the configuration they could remain as they are.

### REST API documentation

The project uses [Swagger2](https://swagger.io/) to document the end-points.

How to see the documentation? With a running application assuming that the two endpoints are accessible on:

```
http://192.168.99.100:8081/pipeline/api/messages
http://192.168.99.100:8082/pipeline/api/messages
```

you will be able to see the api description as json on the following urls:

```
http://192.168.99.100:8081/pipeline/v2/api-docs
http://192.168.99.100:8082/pipeline/v2/api-docs
```

and by using the Swagger UI you will see the above output in a more readable form and will also have the possibility to run some tests against the api:
```
http://192.168.99.100:8081/pipeline/swagger-ui.html
http://192.168.99.100:8082/pipeline/swagger-ui.html
```

Any of those links will require you to provide a username and password to access the data - the default ones user1/pass123.


## Unit Tests

There is a [JUnit](https://junit.org/junit5/) test for each service.

Unfortunately the tests are not fully independent from a running application.

While EmbeddedKafka is used to replace the real kafka server in the tests, the ones that interact with mongoDB still need to access a runnig database - either the one which is configured in the container or a separate database running elsewhere.

Also you will most probably need to adjust the mongoDB access uri with the corresponding hostname or ip and (eventually) changed user credentials in the application.properties file in the test package:
```
spring.data.mongodb.uri=mongodb://clientdb:clientdb@192.168.99.100:27017/pipelinedb?authSource=pipelinedb
```



