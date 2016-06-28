package com.ferhtaydn.akkahttpjavaclient.client;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.stream.ActorMaterializer;
import akka.util.ByteString;
import com.ferhtaydn.akkahttpjavaclient.helpers.CsvHelper;
import com.ferhtaydn.akkahttpjavaclient.models.City;

import java.util.Arrays;
import java.util.Optional;

public class ClientApp {

    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create("client");
        ActorSystem actorSystemCsv = ActorSystem.create("clientCsv");

        AkkaHttpJavaClient client = new AkkaHttpJavaClient(actorSystem, ActorMaterializer.create(actorSystem));
        AkkaHttpJavaClient clientCsv = new AkkaHttpJavaClient(actorSystemCsv, ActorMaterializer.create(actorSystemCsv));

        System.out.println("request level: ");
        clientCsv.requestLevelFutureBased(Optional.of("Paris"), success ->
                Jackson.unmarshaller(City[].class)
                        .unmarshall(success.entity(),
                                clientCsv.getSystem().dispatcher(),
                                clientCsv.getMaterializer())
                        .thenApply(Arrays::asList))
                .thenAccept(cities -> new CsvHelper().writeToCSV(cities, "Paris"))
                .whenComplete((success, throwable) -> clientCsv.close());


        System.out.println("connection level: ");
        client.connectionLevel(Optional.of("NewYork"), success ->
                success.entity()
                        .getDataBytes()
                        .runForeach(byteString -> System.out.println("Result: " + byteString.utf8String()),
                                client.getMaterializer())
        ).whenComplete((success, throwable) -> client.close());


        System.out.println("host level: ");
        client.hostLevel(Optional.of("Istanbul"), success ->
                success.first().get().entity()
                        .getDataBytes()
                        .runFold(ByteString.empty(), ByteString::concat, client.getMaterializer())
                        .handle((byteString, f) -> {
                            if (f != null) {
                                System.err.println("Failure when handle the HttpEntity: " + f.getMessage());
                            } else {
                                System.out.println("Result: " + byteString.utf8String());
                            }
                            return NotUsed.getInstance();
                        })
        ).whenComplete((success, throwable) -> client.close());
    }
}