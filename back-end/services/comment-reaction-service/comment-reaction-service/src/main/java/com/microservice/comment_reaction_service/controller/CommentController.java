package com.microservice.comment_reaction_service.controller;

import com.microservice.comment_reaction_service.model.Comment;
import com.microservice.comment_reaction_service.model.Reaction;
//import com.microservice.comment_reaction_service.service.CommentService;
import com.microservice.comment_reaction_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Endpoint to add a comment associated with a specific user
    @PostMapping("/{userId}")
    public String addComment(@PathVariable Long userId, @RequestBody Comment comment) {
        return commentService.addComment(userId, comment);
    }

    // Endpoint to add a reaction to a comment, associated with a specific user
    @PostMapping("/{postId}/react/{userId}")
    public String reactToComment(@PathVariable Long postId, @PathVariable Long userId, @RequestBody Reaction reaction) {
        return commentService.reactToComment(postId, userId, reaction);
    }
}
