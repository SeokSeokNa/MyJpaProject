package domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "CATEGORY_SEQ_GENERATOR",
        sequenceName = "CATEGORY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_SEQ_GENERATOR")
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    private int level;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static Category createCategory(String name , int level) {
        Category category = new Category();
        category.setName(name);
        category.setLevel(level);

        return category;
    }
}
