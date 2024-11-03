package com.microservice.comment_reaction_service.controller;

 // Ensure you have a Reaction model
import com.microservice.comment_reaction_service.model.Comment;
import com.microservice.comment_reaction_service.model.Reaction;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @PostMapping
    public String addComment(@RequestBody Comment comment) {
        // Logic to save comment
        return "Comment added successfully";
    }

    @PostMapping("/{postId}/react")
    public String reactToComment(@PathVariable Long postId, @RequestBody Reaction reaction) {
        // Logic to react to a comment
        return "Reaction added successfully";
    }
}
