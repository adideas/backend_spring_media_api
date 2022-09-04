package ru.adideas.backend_spring_media_api.Comment.DTO;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentLastDTO {
    @NotNull
    private Set<Integer> comments;

    public Set<Integer> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return getComments().stream()
                .map(Object::toString)
                .collect(Collectors.joining( "," ));
    }
}
