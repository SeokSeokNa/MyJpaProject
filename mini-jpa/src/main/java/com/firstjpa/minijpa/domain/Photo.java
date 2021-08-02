package com.firstjpa.minijpa.domain;

import com.firstjpa.minijpa.MiniJpaApplication;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Context;
import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.firstjpa.minijpa.MiniJpaApplication.UPLOAD_PATH;

@Slf4j
@Entity
@Getter
@Setter
@SequenceGenerator(name = "PHOTO_SEQ_GENERATOR", // 매핑할 데이터베이스 시퀀스 이름
        sequenceName = "PHOTO_SEQ",      // DB에 생성된 시퀀스 이름
        initialValue = 1,                 // DDL 생성시만 사용되며 시작값
        allocationSize = 1)
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "PHOTO_SEQ_GENERATOR")
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String orgFileName;
    private String newFileName;


    public void saveFile(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String saveName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(UPLOAD_PATH,saveName);
        log.info("upload() POST 호출");
        log.info("파일 이름: {}", file.getOriginalFilename());
        log.info("파일 크기: {}", file.getSize());
        try {
            orgFileName = file.getOriginalFilename();
            newFileName = saveName;
            file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
