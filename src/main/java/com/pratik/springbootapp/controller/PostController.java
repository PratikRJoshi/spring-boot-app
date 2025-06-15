package com.pratik.springbootapp.controller;

import com.pratik.springbootapp.model.Post;
import com.pratik.springbootapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

// The controller delegates to the PostService to do the heavy lifting.
@RestController // @Controller and @ResponseBody — so you return objects, not views.
@RequestMapping("/api") // prefixes all routes here with /api.
public class PostController {

    private final PostService postService;

    // TODO: This is a simple testing endpoint. Delete this at the end of the project requirement implementation
    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) String name) {
        return "Hello " + (name != null ? name : "World");
    }


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // GET /api/getPosts
    @GetMapping("/getPosts")
    public Flux<Post> getAllPosts() { // a reactive stream of Post objects (non-blocking and asynchronous).
        return this.postService.fetchAllPosts();
    }
}
