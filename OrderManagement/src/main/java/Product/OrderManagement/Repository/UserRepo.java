package Product.OrderManagement.Repository;


import Product.OrderManagement.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByUsername(String username);
}
