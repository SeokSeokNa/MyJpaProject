package com.firstjpa.minijpa.dto;

import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.Photo;
import com.firstjpa.minijpa.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter @Setter
public class BoardDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDate writeDate;
    private User user;
    private List<PhotoDto> photos;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.writeDate = board.getWriteDate();
        this.user = board.getUser();

        photos = board.getPhotos().stream()
                .map(photo -> new PhotoDto(photo))
                .collect(toList());
    }


}
