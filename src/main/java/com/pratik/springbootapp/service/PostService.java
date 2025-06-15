package com.pratik.springbootapp.service;

import com.pratik.springbootapp.model.Payload;
import com.pratik.springbootapp.model.Post;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    Flux<Post> fetchAllPosts();
    Mono<ResponseEntity<Post>> fetchPostById(int id);
    Mono<ResponseEntity<Void>> processPayload(Payload payload);

}
