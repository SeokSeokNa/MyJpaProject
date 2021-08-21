package com.firstjpa.minijpa.dto;

import com.firstjpa.minijpa.domain.Photo;
import lombok.Getter;

@Getter
public class PhotoDto {

    private Long id;
    private String orgFileName;
    private String newFileName;

    public PhotoDto(Photo photo) {
        this.id = photo.getId();
        this.orgFileName = photo.getOrgFileName();
        this.newFileName = photo.getNewFileName();
    }
}
