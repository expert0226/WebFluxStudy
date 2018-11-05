package com.heaven.webflux02;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class RouteFunction02 {


    @Bean
    RouterFunction<ServerResponse> route() {
        BoardHandler boardHandler = new BoardHandler();

        return RouterFunctions.route(GET("/boards"), boardHandler.list)
                .andRoute(POST("/boards"), boardHandler.create)
                .andRoute(GET("/boards/{num}"), boardHandler.read)
                .andRoute(PUT("/boards/{num}"), boardHandler.update)
                .andRoute(DELETE("/boards/{num}"), boardHandler.delete);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Board {
    private int num;
    private String title;
    private String content;
}

class BoardHandler {
    // List.of 는 JAVA 9 이상
    List<Board> boards;
    int num;

    {
        boards = new ArrayList<>();

        for(num = 1; num < 11; num++) {
            boards.add(new Board(num, "제목 " + num, "내용 " + num));
        }
    }

    HandlerFunction list = req -> {
        Flux<Board> result = Flux.fromIterable(boards);
        Mono<ServerResponse> res = ok().body(result, Board.class);
        return res;
    };

    HandlerFunction create = req -> req.body(toMono(Board.class))
            .doOnNext(board -> { board.setNum(num++); boards.add(board); })
            .then(ok().build());

    HandlerFunction read = req -> {
        int num = Integer.valueOf(req.pathVariable("num"));

        Board foundBoard = null;

        for(Board board : boards) {
            if(board.getNum() == num) {
                foundBoard = board;
                break;
            }
        }

        Mono<ServerResponse> res;

        if(foundBoard == null) {
            res = notFound().build();
        } else {
            res = ok().body(Mono.just(foundBoard), Board.class);
        }

        return res;
    };

    HandlerFunction update = req -> {
        int num = Integer.valueOf(req.pathVariable("num"));

        return req.bodyToMono(Board.class)
                .doOnNext(updatedBoard -> {
                    for(Board board : boards) {
                        if(board.getNum() == num) {
                            if(updatedBoard.getTitle() != null) board.setTitle(updatedBoard.getTitle());
                            if(updatedBoard.getContent() != null) board.setContent(updatedBoard.getTitle());
                            break;
                        }
                    }
                }).then(notFound().build());
    };

    HandlerFunction delete = req -> {
        int num = Integer.valueOf(req.pathVariable("num"));

        for(Board board : boards) {
            if(board.getNum() == num) {
                boards.remove(board);
                break;
            }
        }

        Mono<ServerResponse> res = notFound().build();

        return res;
    };
}
