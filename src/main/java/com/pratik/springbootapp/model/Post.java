package com.pratik.springbootapp.model;

import lombok.Data;

/**
 * Here’s what a single post looks like in JSON from that API:

    {
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit..."
    }
 */

@Data
public class Post {
    // Fields named exactly as JSON keys so Jackson can auto-bind fields without extra annotations.
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
