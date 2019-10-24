package com.geecon.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Configuration
public class FunctionalConfiguration {

	@Bean
	RouterFunction<ServerResponse> routes() {
		return RouterFunctions
				.route(RequestPredicates.GET("/greeting").or(RequestPredicates.POST("/greeting")),
						serverRequest -> ServerResponse.ok().body(
								Mono.just("Hello, world"), String.class))
				.andRoute(RequestPredicates.GET("/hi"),
						serverRequest -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
								Flux.generate(sink -> sink.next(new Greeting("Hello!")))
										.take(5)
										.delayElements(Duration.ofSeconds(1)), Greeting.class
						)
				);
	}
}
