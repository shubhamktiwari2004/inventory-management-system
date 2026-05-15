package Product.OrderManagement.Repository;

import Product.OrderManagement.Model.PlaceOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceOrderRepo extends JpaRepository<PlaceOrderModel , Long> {

}
