package domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "DELIVERY_SEQ_GENERATOR",
        sequenceName = "DELIVERY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ_GENERATOR")
    @Column(name = "delivery_id")
    private Long id;

    private String city;
    private String zipcode;
    private String street;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static Delivery setDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setCity(member.getCity());
        delivery.setStreet(member.getStreet());
        delivery.setZipcode(member.getZipcode());

        return delivery;
    }
}
