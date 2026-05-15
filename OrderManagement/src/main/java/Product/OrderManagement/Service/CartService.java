package Product.OrderManagement.Service;
import Product.OrderManagement.Model.CartModel;
import Product.OrderManagement.Model.PlaceOrderModel;
import Product.OrderManagement.Model.ProductModel;
import Product.OrderManagement.Repository.CartRepo;
import Product.OrderManagement.Repository.PlaceOrderRepo;
import Product.OrderManagement.Repository.ProductRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private PlaceOrderRepo placeOrderRepo;



    public String addToCart(long productId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        Optional<ProductModel> productOpt = productRepo.findById(productId);
        if (!productOpt.isPresent()) {
            return "Product not found";
        }

        ProductModel product = productOpt.get();
        Optional<CartModel> cartOpt = cartRepo.findByProductIdAndUsername(productId, username);
        if (!cartOpt.isPresent()) {
            CartModel newCart = new CartModel();
            newCart.setUsername(username);
            newCart.setName(product.getName());
            newCart.setPrice(product.getPrice());
            newCart.setQuantity(1);
            newCart.setProductId(product.getId());
            cartRepo.save(newCart);
            return "Added to cart successfully";
        }

        CartModel existingCart = cartOpt.get();
        if (product.getQuantity() > existingCart.getQuantity()) {
            existingCart.setQuantity(existingCart.getQuantity() + 1);
            cartRepo.save(existingCart);
            return "Item added successfully";
        } else {
            return "Not enough stock available";
        }
    }

    public String deleteFromCart(long productId, HttpSession session) {
        String username = (String) session.getAttribute("username");

        Optional<ProductModel> productOpt = productRepo.findById(productId);
        if (!productOpt.isPresent()) {
            System.out.println("Product not found");
            return "Product not found";
        }

        ProductModel product = productOpt.get();

        Optional<CartModel> cartOpt = cartRepo.findByProductIdAndUsername(productId, username);
        if (!cartOpt.isPresent()) {
            System.out.println("Cart item not found for this user and product");
            return "Cart item not found for this user and product";
        }

        CartModel cartModel = cartOpt.get();
        int currentQuantity = cartModel.getQuantity();

        if (currentQuantity > 0) {
            cartModel.setQuantity(currentQuantity - 1);

            if (cartModel.getQuantity() == 0) {
                cartRepo.delete(cartModel);
                System.out.println("Product removed from cart as quantity reached 0");
                return "Product removed from cart as quantity reached 0";
            } else {
                cartRepo.save(cartModel);
                System.out.println("Product quantity decremented by 1");
                return "Product quantity decremented by 1";
            }
        } else {
            System.out.println("Cart item has no quantity to decrement");
            return "Cart item has no quantity to decrement";
        }
    }

    public List<CartModel> viewCart(String username) {

        return cartRepo.findAllByUsername(username);
    }

    public void place(long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        Optional<ProductModel> productOpt = productRepo.findById(id);
        Optional<CartModel> cartOpt = cartRepo.findByProductIdAndUsername(id, username);
        PlaceOrderModel order = new PlaceOrderModel();

        if (productOpt.isPresent() && cartOpt.isPresent()) {
            ProductModel product = productOpt.get();
            CartModel cart = cartOpt.get();

            int updatedQuantity = product.getQuantity() - cart.getQuantity();

            if (updatedQuantity < 0) {
                throw new RuntimeException("Insufficient product quantity");
            }

            int totalPrice = cart.getQuantity() * cart.getPrice();

            order.setName(cart.getName());
            order.setUsername(username);
            order.setProductId(cart.getProductId());
            order.setPrice(totalPrice);
            order.setQuantity(cart.getQuantity());

            placeOrderRepo.save(order);

            product.setQuantity(updatedQuantity);
            productRepo.save(product);

            cartRepo.delete(cart);
        } else {
            throw new RuntimeException("Product or Cart not found");
        }
    }
}
