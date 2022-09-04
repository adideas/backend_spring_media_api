package ru.adideas.backend_spring_media_api.Comment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface CommentRepository  extends
        org.springframework.data.repository.Repository<Comment, Integer> {
    @Query(
            value = """
                    SELECT * FROM comments
                    WHERE id IN (:comments) AND news_id IN (:news)
                    ORDER BY id DESC
                    """,
            nativeQuery = true
    )
    Set<Comment> findLastCommentForNews(@Param("comments") Set<Integer> comments, @Param("news") Set<Integer> news);

    @Query(
            value = """
                    SELECT * FROM comments WHERE news_id = :news_id ORDER BY id DESC
                    """,
            nativeQuery = true
    )
    Set<Comment> findCommentsForNews(@Param("news_id") Integer newsId);

    @Query(
            value = """
                    INSERT INTO comments(news_id, user_id, text, created_at)
                    VALUES (:news_id, :user_id, :text, NOW())
                    """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void makeWithId(@Param("news_id") Integer newsId, @Param("user_id") Integer userId, @Param("text") String text);
}
