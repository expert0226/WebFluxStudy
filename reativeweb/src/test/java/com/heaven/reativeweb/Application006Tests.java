package com.heaven.reativeweb;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

import java.time.Duration;
import java.util.function.Consumer;

public class Application006Tests {
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/mockwebserver/3.11.0
    private MockWebServer server;
    private WebClient webClient;

    @Before
    public void setup() {
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector();
        this.server = new MockWebServer();
        this.webClient = WebClient
                .builder()
                .clientConnector(connector)
                .baseUrl(this.server.url("/").toString())
                .build();
    }

    @Test
    public void shouldReceiveResponseHeaders() {
        prepareResponse(response -> response
            .setHeader("Content-Type", "text/plain")
            .setBody("Hello Spring!"));

        Mono<HttpHeaders> result = this.webClient.get()
                .uri("/greeting?name=Spring").exchange()
                .map(response -> response.headers().asHttpHeaders());

        StepVerifier.create(result).consumeNextWith(
                httpHeaders -> {
                    assertEquals(MediaType.TEXT_PLAIN, httpHeaders.getContentType());
                    assertEquals(13L, httpHeaders.getContentLength());
                })
                .expectComplete()
                .verify(Duration.ofSeconds(3));

        expectRequestCount(1);

        expectRequest(request -> {
            assertEquals("*/*", request.getHeader(HttpHeaders.ACCEPT));
            assertEquals("/greeting?name=Spring", request.getPath());
        });
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        this.server.enqueue(response);
    }

    private void expectRequestCount(int count) {
        assertEquals(count, this.server.getRequestCount());
    }

    private void expectRequest(Consumer<RecordedRequest> consumer) {
        try {
            consumer.accept(this.server.takeRequest());
        }
        catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
