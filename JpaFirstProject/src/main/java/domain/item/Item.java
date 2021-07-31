package domain.item;

import domain.CategoryItem;
import domain.OrderItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "ITEM_SEQ_GENERATOR",
        sequenceName = "ITEM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GENERATOR")
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_price")
    private int price;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<CategoryItem> getCategoryItems() {
        return categoryItems;
    }


    public static Item createItem(String itemName, int price, int stockQuantity) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);

        return item;
    }

    //주문 개수만큼 제외하는 메소드
    public void removeCount(OrderItem orderItem) {
        Item item = orderItem.getItem();
        int remove = item.getStockQuantity() - orderItem.getOrderCount();
        item.setStockQuantity(remove);
    }

    //주문취소일 경우(재고 더하기)
    public void addCount(OrderItem orderItem) {
        Item item = orderItem.getItem();
        int add = item.getStockQuantity() + orderItem.getOrderCount();
        item.setStockQuantity(add);
    }

}
