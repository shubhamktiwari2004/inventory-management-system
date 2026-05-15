package Product.OrderManagement.Service;


import Product.OrderManagement.Dto.UserDto;
import Product.OrderManagement.Enum.Role;
import Product.OrderManagement.Model.UserModel;
import Product.OrderManagement.Repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    public String register(UserDto userDto) {
        Role role;

        if (userRepository.findByEmail(userDto.email).isPresent()) {
            return "User already exists";
        }

        try {
            role = Role.valueOf(userDto.role.toUpperCase());
        } catch (Exception e) {
            role = Role.USER;
        }

        if (role == Role.ADMIN) {
            return "Cannot assign ADMIN role";
        }

        UserModel user = new UserModel();
        user.setUsername(userDto.username);
        user.setEmail(userDto.email);

        user.setPassword((userDto.password));

        user.setRole(role);

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(UserDto userDto) {

        Optional<UserModel> optionalUser = userRepository.findByUsername(userDto.username);

        if (optionalUser.isEmpty()) {
            return "INVALID";
        }
        UserModel user = optionalUser.get();

        if (!user.getPassword().equals(userDto.password)) {
            return "INVALID";
        }

        return user.getRole().name(); // returns "ADMIN" or "USER"



    }
}
