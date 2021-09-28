package it.csttec.reactivedemo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/common/notifications")
public class TestController {

    @GetMapping("/stream/mono")
    @Operation(summary = "Stream Notification", description = "Notification -> retrieve notification by mono stream ", tags = {"stream"})
    public Mono<Integer> streamMono(){
        Random random = new Random();
        return Mono.just(random.nextInt());
    }

    @GetMapping("/stream/flux")
    @Operation(summary = "Stream Notification", description = "Notification -> retrieve notification by flux stream ", tags = {"stream"})
    public Flux<Integer> streamFlux(){
        Random random = new Random();
        return Flux.interval(Duration.ofSeconds(1)).map(num -> random.nextInt());
    }

    @GetMapping("/stream/flux2")
    @Operation(summary = "Stream Notification", description = "Notification -> retrieve notification by flux stream resources ", tags = {"stream"})
    public Flux<Integer> streamFlux2(){
        Random random = new Random();
        return Flux.just(random.nextInt(),random.nextInt(),random.nextInt());
    }

    @GetMapping("/stream/event")
    @Operation(summary = "Stream Notification Event", description = "Notification -> retrieve notification event by flux stream resources ", tags = {"stream"})
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now().toString())
                        .build());
    }
}
