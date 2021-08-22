package com.firstjpa.minijpa.domain;

import com.firstjpa.minijpa.controller.Form.BoardForm;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.util.Lazy;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", // 매핑할 데이터베이스 시퀀스 이름
        sequenceName = "BOARD_SEQ",      // DB에 생성된 시퀀스 이름
        initialValue = 1,                 // DDL 생성시만 사용되며 시작값
        allocationSize = 1)
public class Board{
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "BOARD_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id" , nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "board",
            cascade = CascadeType.PERSIST, //게시글 등록시 사진을 같이 영속화 하여 등록될 수있게
            orphanRemoval = true //부모와 연관이 끊긴 자식을 삭제하기 위해
    )
    private List<Photo> photos = new ArrayList<>();

    @Column(length = 20)
    private String title;
    @Column(length = 50)
    private String contents;

    private LocalDate writeDate;

    @ColumnDefault("0")
    private Integer hit = 0;


    public void upHit() {
        this.hit +=1;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setBoard(this);
    }

    public static Board createBoard(User user , String title , String contents , List<MultipartFile> uploadFiles) {
        Board board = new Board();
        board.setUser(user);
        board.setTitle(title);
        board.setContents(contents);
        board.setWriteDate(LocalDate.now());

        //파일이 존재하면 파일관련 연관관계 및 저장셋팅
        if (uploadFiles.get(0).getSize() !=0){
            for (MultipartFile file : uploadFiles) {
                Photo photo = new Photo();
                photo.saveFile(file);
                board.addPhoto(photo);
            }
        }

        return board;
    }


    public static Board createBoard2(User user , String title , String contents , MultipartFile[] uploadFiles) {
        Board board = new Board();
        board.setUser(user);
        board.setTitle(title);
        board.setContents(contents);
        board.setWriteDate(LocalDate.now());

        //파일이 존재하면 파일관련 연관관계 및 저장셋팅
        photoUpload(board,uploadFiles);

        return board;
    }

    public static void photoUpload(Board board, MultipartFile[] uploadFiles) {
        //파일이 존재하면 파일관련 연관관계 및 저장셋팅
        if (uploadFiles != null){
            for (MultipartFile file : uploadFiles) {
                Photo photo = new Photo();
                photo.saveFile(file);
                board.addPhoto(photo);
            }
        }
    }


}
