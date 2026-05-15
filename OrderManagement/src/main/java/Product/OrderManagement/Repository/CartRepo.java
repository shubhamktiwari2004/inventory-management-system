package Product.OrderManagement.Repository;


import Product.OrderManagement.Model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartModel , Long> {

    @Query(value = "SELECT * FROM cart where username = ?1" , nativeQuery = true)
    List<CartModel> findAllByUsername(String username);

    @Query(value = "SELECT * FROM cart WHERE product_id = ?1 AND username = ?2", nativeQuery = true)
    Optional<CartModel> findByProductIdAndUsername(long productId, String username);
}
