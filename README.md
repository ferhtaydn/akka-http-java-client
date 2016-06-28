
#### akka-http based simple Java http client.

##### Simple akka-http client level examples:
* hostLevel
* connectionLevel
* requestLevel

##### Simple endpoint of GoEuro is used
* the response json is written to a csv file.
* settings are read from application.conf
* jar can be created with:

  ```sh
  sbt assembly
  ```
* jar can be called with:

  ```sh
  java -jar target/scala-2.11/GoEuroTest.jar Berlin
  ```
