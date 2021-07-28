package domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", // 매핑할 데이터베이스 시퀀스 이름
        sequenceName = "MEMBER_SEQ",      // DB에 생성된 시퀀스 이름
        initialValue = 1,                 // DDL 생성시만 사용되며 시작값
        allocationSize = 1)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_name" , length = 50)
    private String name;


    private String city;
    private String zipcode;
    private String street;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
