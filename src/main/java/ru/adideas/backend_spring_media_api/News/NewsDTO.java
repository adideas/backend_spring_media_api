package ru.adideas.backend_spring_media_api.News;

import javax.validation.constraints.NotNull;

public class NewsDTO {
    @NotNull
    private String text;

    public String getText() {
        return text;
    }
}
