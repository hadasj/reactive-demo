package com.geecon.demo;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

@RestController
public class GreetingRestController {
	private int counter = 1;

	@GetMapping(value = "/greetings", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Publisher<Greeting> greetings() {
		Flux<Greeting> greetingFlux = Flux.<Greeting>generate(sink -> sink.next(new Greeting("Hello world " + next())))
				.take(15);
		return greetingFlux;
	}

	@GetMapping(value = "/delayGreetings", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Publisher<Greeting> sseGreetings() {
		return Flux.<Greeting>generate(sink -> sink.next(new Greeting("Hello, world @" + Instant.now().toString())))
				.delayElements(Duration.ofSeconds(1));
	}

	private int next() {
		return counter++;
	}
}
