package com.pratik.springbootapp.service;

import com.pratik.springbootapp.model.Payload;
import com.pratik.springbootapp.model.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

/**
 * Implementation of PostService that uses WebClient for non-blocking HTTP
 * requests.
 * This service demonstrates reactive programming patterns using Project
 * Reactor's Mono and Flux.
 */
@Service
public class PostServiceImpl implements PostService {

    // WebClient is Spring's non-blocking HTTP client used to make external API
    // calls.
    // We build it with the base URL of the external API for posts.
    private final WebClient webClient;
    // Global timeout for all reactive operations to prevent hanging requests
    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    /**
     * Constructor that builds a WebClient instance with the base URL for the
     * external API.
     * WebClient.Builder is auto-configured by Spring Boot for reactive HTTP
     * requests.
     */
    public PostServiceImpl(WebClient.Builder webClientBuilder) {
        // Base URL is set once here for reuse in all requests.
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    /**
     * Fetches all posts from the external API using reactive patterns.
     * 
     * Reactive Chain:
     * 1. Makes a GET request to /posts
     * 2. Converts response to Flux<Post> (stream of posts)
     * 3. Applies timeout to prevent hanging
     * 4. Handles errors with fallback strategies
     * 
     * @return Flux<Post> - A reactive stream of Post objects
     */
    @Override
    public Flux<Post> fetchAllPosts() {
        // webClient sends a GET request to "/posts" endpoint
        // retrieve() prepares to extract the response body
        // bodyToMono(Post[].class) says: expect JSON array of posts, convert to Post[]
        // block() makes this call synchronous, waits for the response and returns
        // Post[]
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Post.class) // Return multiple items as Flux<Post>
                .timeout(TIMEOUT)
                .onErrorResume(throwable -> {
                    // Log the error and return an empty Flux
                    System.err.println("Error fetching posts: " + throwable.getMessage());
                    return Flux.empty();
                })
                .onErrorReturn(new Post()); // Fallback to empty post if needed
    }

    /**
     * Fetches a single post by ID using reactive patterns.
     * 
     * Reactive Chain:
     * 1. Makes a GET request to /posts/{id}
     * 2. Converts response to Mono<ResponseEntity<Post>>
     * 3. Applies timeout protection
     * 4. Implements error handling with appropriate HTTP status codes
     * 
     * @param id The ID of the post to fetch
     * @return Mono<ResponseEntity<Post>> - A reactive single result wrapped in
     *         ResponseEntity
     */
    @Override
    public Mono<ResponseEntity<Post>> fetchPostById(int id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/posts/{id}").build(id)) // Build the URI dynamically using the
                                                                             // provided ID
                .retrieve() // Send the HTTP request and wait for a response — still non-blocking
                .toEntity(Post.class) // Convert the response body to a Post object, wrap it inside ResponseEntity,
                                      // and return it wrapped in a Mono (i.e., async result with 0 or 1 item)
                .timeout(TIMEOUT)
                .onErrorResume(throwable -> {
                    // Log the error and return a 500 response
                    System.err.println("Error fetching post by id: " + throwable.getMessage());
                    return Mono.just(ResponseEntity.status(500).build());
                })
                .onErrorReturn(ResponseEntity.status(500).build());
    }

    /**
     * Processes a payload using reactive patterns.
     * 
     * Reactive Chain:
     * 1. Validates input payload
     * 2. Simulates processing with a delay
     * 3. Returns appropriate HTTP status
     * 4. Implements error handling
     * 
     * @param payload The payload to process
     * @return Mono<ResponseEntity<Void>> - A reactive result indicating
     *         success/failure
     */
    @Override
    public Mono<ResponseEntity<Void>> processPayload(Payload payload) {
        // Input validation
        if (payload == null || payload.getPayloadId() == null || payload.getKafkaTopic() == null) {
            return Mono.just(ResponseEntity.badRequest().build()); // Immediately return a 400 Bad Request wrapped in a
                                                                   // Mono
        }

        // Simulate processing with a delay
        return Mono.just(payload)
                .delayElement(Duration.ofMillis(100)) // Simulate processing time
                .map(p -> ResponseEntity.ok().<Void>build())
                .timeout(TIMEOUT)
                .onErrorResume(throwable -> {
                    // Log the error and return a 500 response
                    System.err.println("Error processing payload: " + throwable.getMessage());
                    return Mono.just(ResponseEntity.status(500).build());
                })
                .onErrorReturn(ResponseEntity.status(500).build());
    }
}
