package domain;

import domain.item.Item;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "CATEGORY_ITEM_SEQ_GENERATOR",
        sequenceName = "CATEGORY_ITEM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class CategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_ITEM_SEQ_GENERATOR")
    @Column(name = "category_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        item.getCategoryItems().add(this);
    }

    public static CategoryItem createCategoryItem(Category category , Item item) {
        CategoryItem category_item = new CategoryItem();
        category_item.setCategory(category);
        category_item.setItem(item);


        return category_item;
    }
}
