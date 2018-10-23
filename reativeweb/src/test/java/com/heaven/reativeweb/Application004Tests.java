package com.heaven.reativeweb;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class Application004Tests {

    @Test
    public void stepVerifier() {
        Mono<Integer> mono = Mono.just(1).subscribeOn(Schedulers.single());

        StepVerifier.create(mono)
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    public void stepVerifier2() {
        // var 로 받는 경우 경고와 에러 발생
        // Warning:(22, 13) java: as of release 10, 'var' is a restricted local variable type and cannot be used for type declarations or as the element type of an array
        // Error:(22, 9) java: cannot find symbol
        //      symbol:   class var
        //      location: class com.heaven.reativeweb.Application004Tests
        Flux<Integer> flux = Flux.just(1, 2, 3)
                .concatWith(Mono.error(new RuntimeException()))
                .subscribeOn(Schedulers.single());

        StepVerifier.create(flux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectError(RuntimeException.class)
                .verify();
    }

}
