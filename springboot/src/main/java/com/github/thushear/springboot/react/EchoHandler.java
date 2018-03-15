package com.github.thushear.springboot.react;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * <pre>
 * Created: 2018年03月14日  上午 11:18
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月14日  上午 11:18
 * Update Log:
 * Comment:
 * </pre>
 */
@Component
public class EchoHandler {




    public Mono<ServerResponse> echo(ServerRequest serverRequest){
        return ServerResponse.ok().body(serverRequest.bodyToMono(String.class),String.class);
    }




}
