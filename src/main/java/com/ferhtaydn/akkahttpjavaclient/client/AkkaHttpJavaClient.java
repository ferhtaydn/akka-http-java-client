package com.ferhtaydn.akkahttpjavaclient.client;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.HostConnectionPool;
import akka.http.javadsl.Http;
import akka.http.javadsl.OutgoingConnection;
import akka.http.javadsl.model.*;
import akka.japi.Pair;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import com.ferhtaydn.akkahttpjavaclient.client.settings.Settings;
import com.ferhtaydn.akkahttpjavaclient.client.settings.SettingsImpl;
import scala.util.Try;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class AkkaHttpJavaClient {

    private final ActorSystem system;
    private final Materializer materializer;
    private final SettingsImpl settings;
    private final Flow<HttpRequest, HttpResponse, CompletionStage<OutgoingConnection>> connectionFlow;
    private final Flow<Pair<HttpRequest, Integer>, Pair<Try<HttpResponse>, Integer>, HostConnectionPool> poolClientFlow;

    public AkkaHttpJavaClient(ActorSystem sys, Materializer mat) {
        system = sys;
        materializer = mat;
        settings = Settings.SettingsProvider.get(system);
        connectionFlow = Http.get(system).outgoingConnection(ConnectHttp.toHost(settings.HOST, settings.PORT));
        poolClientFlow = Http.get(system).cachedHostConnectionPool(ConnectHttp.toHost(settings.HOST, settings.PORT), materializer);
    }

    public ActorSystem getSystem() {
        return system;
    }

    public Materializer getMaterializer() {
        return materializer;
    }

    public void close() {
        System.out.println("Shutting down client");
        Http.get(system).shutdownAllConnectionPools().whenComplete((s, f) -> system.terminate());
    }

    private Uri getUri(Optional<String> s) {
        return Uri.create(settings.URI + s.orElse("").replace(" ", "%20"));
    }

    public <U> CompletionStage<U> connectionLevel(Optional<String> s,
                                                  Function<HttpResponse, CompletionStage<U>> responseHandler) {
        return Source.single(HttpRequest.create().withUri(getUri(s)))
                .via(connectionFlow)
                .runWith(Sink.head(), materializer)
                .thenComposeAsync(responseHandler);
    }

    public <U> CompletionStage<U> hostLevel(Optional<String> s,
                          Function<Pair<Try<HttpResponse>, Integer>, CompletionStage<U>> responseHandler) {
        return Source.single(Pair.create(HttpRequest.create().withUri(getUri(s)), 42))
                .via(poolClientFlow)
                .runWith(Sink.head(), materializer)
                .thenComposeAsync(responseHandler);
    }

    public <U> CompletionStage<U> requestLevelFutureBased(Optional<String> s,
                                                          Function<HttpResponse, CompletionStage<U>> responseHandler) {
        return Http.get(system)
                .singleRequest(HttpRequest.create().withUri(getUri(s)), materializer)
                .thenComposeAsync(responseHandler);
    }

}
