package com.firstjpa.minijpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PostWrite {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User user;

    @Column(length = 20)
    private String title;
    @Column(length = 50)
    private String contents;

    public static PostWrite createPost(User user , String title , String contents) {
        PostWrite postWrite = new PostWrite();
        postWrite.setUser(user);
        postWrite.setTitle(title);
        postWrite.setContents(contents);
        return postWrite;
    }
}
