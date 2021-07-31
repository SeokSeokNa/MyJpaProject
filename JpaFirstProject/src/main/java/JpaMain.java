import domain.*;
import domain.item.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("나원석");
            member.setCity("인천");
            member.setStreet("세월천로 16길");
            member.setZipcode("20121");
            em.persist(member);

            Item milk = Item.createItem("우유", 1200, 10);
            Item cocoBall = Item.createItem("코코볼", 8700, 25);
            em.persist(cocoBall);
            em.persist(milk);

            Category dairy_product = Category.createCategory("유제품", 1);
            Category snack = Category.createCategory("과자", 1);
            em.persist(dairy_product);
            em.persist(snack);

            CategoryItem dairy_product_item = CategoryItem.createCategoryItem(dairy_product, milk);
            CategoryItem snack_item = CategoryItem.createCategoryItem(snack, cocoBall);
            em.persist(dairy_product_item);
            em.persist(snack_item);

            dairy_product_item.getCategory();

            OrderItem milkItem = OrderItem.createOrderItem(milk, 6);
            OrderItem cocoBallItem = OrderItem.createOrderItem(cocoBall, 2);
            em.persist(milkItem);
            em.persist(cocoBallItem);

            milkItem.cancelOrder(milkItem);
            em.remove(milkItem);

            Delivery delivery = Delivery.setDelivery(member);
            em.persist(delivery);
            Order order = Order.createOrder(member, OrderStatus.ORDER, delivery , milkItem , cocoBallItem);
            em.persist(order);

            em.flush();
            em.clear();

            Order findOrder = em.find(Order.class, 1L);
//            Delivery orderDelivery= findOrder.getDelivery();
//            List<OrderItem> orderItems = findOrder.getOrderItems();
//            System.out.println("주문자 = "+findOrder.getMember().getName());
//            System.out.println("주문자 주소 = " + orderDelivery.getCity() + " " + orderDelivery.getStreet() + " " + orderDelivery.getZipcode());
//            System.out.println("");
//
//            for (OrderItem orderItem : orderItems) {
//                System.out.println("카테고리 = " + orderItem.getItem().getCategoryItems().get(0).getCategory().getName());
//                System.out.println("제품명 = " + orderItem.getItem().getItemName());
//                System.out.println("주문수량 = " + orderItem.getOrderCount());
//                System.out.println("주문가격 = " + orderItem.getOrderPrice());
//                System.out.println("==========================================================================");
//                System.out.println("==========================================================================");
//            }



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
