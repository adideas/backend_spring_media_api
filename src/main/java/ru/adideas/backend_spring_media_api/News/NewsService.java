package ru.adideas.backend_spring_media_api.News;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Service;
import ru.adideas.backend_spring_media_api.User.UserAuthentication;

import java.util.Set;

@Service
@ConstructorBinding
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Set<News> findNewsForMe(Integer userId) {
        return this.newsRepository.findNewsForMe(userId);
    }

    public Set<News> findMyNews(Integer userId) {
        return this.newsRepository.findMyNews(userId);
    }

    public News find(Integer userId, UserAuthentication auth) {
        return this.newsRepository.find(userId, auth.getPrincipal());
    }

    public void make(NewsDTO newsDTO, UserAuthentication auth) {
        String text = newsDTO.getText();
        Integer user_id = auth.getPrincipal();
        this.make(text, user_id);
    }

    public void make(String text, Integer userId) {
        this.newsRepository.make(userId, text);
    }

    public void make(Integer id, String text, Integer userId) {
        this.newsRepository.makeWithId(id, userId, text);
    }


    public Integer count() {
        return this.newsRepository.count();
    }
}
