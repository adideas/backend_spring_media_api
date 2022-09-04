package ru.adideas.backend_spring_media_api.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.adideas.backend_spring_media_api.Comment.DTO.CommentLastDTO;
import ru.adideas.backend_spring_media_api.News.NewsRepository;
import ru.adideas.backend_spring_media_api.User.UserAuthentication;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    public Map<Integer, Comment> findLastCommentForNews(CommentLastDTO commentLastDTO, UserAuthentication auth) {
        Set<Integer> news = newsRepository.findIdNews(auth.getPrincipal());

        Map<Integer, Comment> data = new LinkedHashMap<>();
        Set<Comment> list = this.commentRepository.findLastCommentForNews(
                commentLastDTO.getComments(),
                news
        );

        for(Comment comment : list) {
            data.put(comment.getId(), comment);
        }
        return data;
    }

    public Set<Comment> findComments(Integer newsId, UserAuthentication auth) {
        Set<Integer> news = newsRepository.findIdNews(auth.getPrincipal());
        if (news.contains(newsId)) {
            return this.commentRepository.findCommentsForNews(newsId);
        }
        return Set.of();
    }

    public void make(Integer newsId, Integer userId, String text) {
        this.commentRepository.makeWithId(newsId, userId, text);
    }
}
