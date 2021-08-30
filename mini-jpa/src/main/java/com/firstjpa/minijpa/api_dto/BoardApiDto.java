package com.firstjpa.minijpa.api_dto;

import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.dto.PhotoDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter @Setter
public class BoardApiDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDate writeDate;
    private Integer hit;
    private String userName;
    private List<PhotoApiDto> photoList;

    public BoardApiDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.writeDate = board.getWriteDate();
        this.hit = board.getHit();
        this.userName = board.getUser().getName();

        photoList = board.getPhotos().stream()
                .map(photo -> new PhotoApiDto(photo))
                .collect(toList());
    }



}
