package Product.OrderManagement.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_id")
    private long productId;
    private String username;
    private String name;
    private int quantity;
    private int price;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "id=" + id +
                ", productId=" + productId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }


}
