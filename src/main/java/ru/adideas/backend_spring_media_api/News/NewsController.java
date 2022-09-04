package ru.adideas.backend_spring_media_api.News;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.adideas.backend_spring_media_api.User.UserAuthentication;

import javax.validation.Valid;
import java.util.Set;

@Validated
@RestController
@RequestMapping("api/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public Set<News> allNews(UserAuthentication auth) {
        return this.newsService.findNewsForMe(auth.getPrincipal());
    }

    @GetMapping("/me")
    public Set<News> myNews(UserAuthentication auth) {
        return this.newsService.findMyNews(auth.getPrincipal());
    }

    @GetMapping("{id}")
    public News showNews(@PathVariable("id") Integer newsId, UserAuthentication auth) {
        return this.newsService.find(newsId, auth);
    }

    @PostMapping
    public ResponseEntity<?> makeNews(@Valid @RequestBody NewsDTO newsDTO, UserAuthentication auth) {
        this.newsService.make(newsDTO, auth);
        return ResponseEntity.ok().build();
    }
}
