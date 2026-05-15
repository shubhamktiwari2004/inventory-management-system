package Product.OrderManagement.Repository;

import Product.OrderManagement.Model.ProductModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*@Repository*/
public interface ProductRepo extends JpaRepository<ProductModel,Long> {


    @Query(value = "SELECT * FROM products where enable = 0" , nativeQuery = true)
    List<ProductModel> findEnableProduct();



    @Query(value = "SELECT * FROM products where quantity > 5 and username = ?1" , nativeQuery = true)
    List<ProductModel> findProductQuantity(String username);


    @Query(value = "SELECT * FROM products where enable = 0 and quantity > 5 " , nativeQuery = true)
    List<ProductModel> findEnableProductQuantity();

}
