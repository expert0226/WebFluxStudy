package com.heaven.rx;

import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exam001 {
    // 4.3. Flux 또는 Mono를 만들고 그것을 구독하는 간단한 방법
    // http://projectreactor.io/docs/core/release/reference/#_simple_ways_to_create_a_flux_or_mono_and_subscribe_to_it
    @Test
    public void test01() {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);

        Mono<String> noData = Mono.empty();

        Mono<String> data = Mono.just("foo");

        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3); // Start, n

//        subscribe(Consumer<? super T> consumer,
//                Consumer<? super Throwable> errorConsumer,
//                Runnable completeConsumer,
//                Consumer<? super Subscription> subscriptionConsumer);

        seq1.subscribe();
        seq1.subscribe(System.out::println);
    }

    // 4.3.1. subscribe메소드 예제
    // http://projectreactor.io/docs/core/release/reference/#__code_subscribe_code_method_examples
    @Test
    public void test02() {
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe();

        ints.subscribe(System.out::println);
    }

    @Test
    public void test03() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });

        // JVM Error Hander: reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.RuntimeException: Got to 4
        // ints.subscribe(System.out::println);
        ints.subscribe(System.out::println, error -> System.err.printf("Error: %s\n", error));
    }

    @Test
    public void test04() {
        Flux<Integer> ints = Flux.range(1, 4);

        ints.subscribe(
                System.out::println,
                error -> System.err.printf("Error: %s\n", error),
                () -> System.out.println("Done"));

        Flux<Integer> intsWithError= Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });

        intsWithError.subscribe(
                System.out::println,
                error -> System.err.printf("Error: %s\f", error),
                () -> System.out.println("Done"));
    }

    @Test
    public void test05() {
        Flux<Integer> ints = Flux.range(1, 4);

        ints.subscribe(subscriber);

        Flux<Integer> intsWithError= ints
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });

        intsWithError.subscribe(subscriber);

        System.out.println("Publisher - Subscriber");
        Publisher<Integer> publisher = ints;
        publisher.subscribe(subscriber);
    }

    Subscriber<Integer> subscriber = new Subscriber<Integer>() {
        @Override
        public void onSubscribe(Subscription s) {
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Integer integer) {
            System.out.println(integer);
        }

        @Override
        public void onError(Throwable t) {
            System.err.printf("Error: %s\f", t);
        }

        @Override
        public void onComplete() {
            System.out.println("Done");
        }
    };

    @Test
    public void test06() {
        Flux<Integer> ints = Flux.range(1, 4);

        ints.subscribe(subscriber2);

        Flux<Integer> intsWithError= ints
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });

        intsWithError.subscribe(subscriber2);

        System.out.println("Publisher - Subscriber");
        Publisher<Integer> publisher = ints;
        publisher.subscribe(subscriber2);
    }

    Subscriber<Integer> subscriber2 = new Subscriber<Integer>() {
        @Override
        public void onSubscribe(Subscription s) {
            s.request(2);
        }

        @Override
        public void onNext(Integer integer) {
            System.out.println(integer);
        }

        @Override
        public void onError(Throwable t) {
            System.err.printf("Error: %s\f", t);
        }

        @Override
        public void onComplete() {
            System.out.println("Done");
        }
    };
}
