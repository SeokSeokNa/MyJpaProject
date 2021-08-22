package com.firstjpa.minijpa.api_dto;

import com.firstjpa.minijpa.domain.Photo;
import lombok.Getter;

@Getter
public class PhotoApiDto {

    private Long photo_id;
    private String fileName;

    public PhotoApiDto(Photo photo) {
        this.photo_id = photo.getId();
        this.fileName = photo.getNewFileName();
    }
}
