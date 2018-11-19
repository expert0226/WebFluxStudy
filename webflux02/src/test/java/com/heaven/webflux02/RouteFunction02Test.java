package com.heaven.webflux02;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.test.context.junit4.SpringRunner;

//import static org.junit.Assert.*;

//@RunWith(SpringRunner.class)
//@WebFluxTest(RouteFunction02.class)
public class RouteFunction02Test {
    WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient
                .bindToRouterFunction(new RouteFunction02().route())
                .build();
    }

    @Test
    public void list() {
        webTestClient.get()
                .uri("/boards")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Board.class);
    }

    @Test
    public void create() {
        Board board = new Board() {
            {
                this.setTitle("제목");
                this.setContent("내용");
            }
        };

        webTestClient.post()
                .uri("/boards")
                .body(Mono.just(board), Board.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    public void read() {
        webTestClient.get()
                .uri("/boards/3")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"title\":\"제목 3\", \"content\":\"내용 3\"}");
    }

    @Test
    public void put() {
        Board board = new Board() {
            {
                this.setTitle("제목");
                this.setContent("내용");
            }
        };

        webTestClient.put()
                .uri("/boards/5")
                .body(Mono.just(board), Board.class)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    @Test
    public void delete() {
        webTestClient.delete()
                .uri("/boards/7")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    @Test
    public void test() {
        Mono.just("Hello WebFlux").log().subscribe();
    }
}