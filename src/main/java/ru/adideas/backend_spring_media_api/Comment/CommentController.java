package ru.adideas.backend_spring_media_api.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.adideas.backend_spring_media_api.Comment.DTO.CommentLastDTO;
import ru.adideas.backend_spring_media_api.User.UserAuthentication;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/comment")
@ConstructorBinding
@Validated
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Map<Integer, Comment> getLastComments(
            @Valid @RequestBody CommentLastDTO commentLastDTO,
            UserAuthentication auth
    ) {
        return this.commentService.findLastCommentForNews(commentLastDTO, auth);
    }

    @GetMapping("/news/{news_id}")
    public Set<Comment> getComments(@PathVariable("news_id") Integer newsId, UserAuthentication auth) {
        return this.commentService.findComments(newsId, auth);
    }
}
