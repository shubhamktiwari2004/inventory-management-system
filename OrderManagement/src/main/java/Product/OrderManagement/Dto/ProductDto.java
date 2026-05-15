package Product.OrderManagement.Dto;

public class ProductDto {
    public long id;
    public long productId;
    public String name ;
    public int price;
    public int quantity;
    public int enable;

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", enable=" + enable +
                '}';
    }
}
