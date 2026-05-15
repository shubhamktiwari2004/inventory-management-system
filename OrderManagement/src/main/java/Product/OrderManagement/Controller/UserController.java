package Product.OrderManagement.Controller;

import Product.OrderManagement.Dto.UserDto;
import Product.OrderManagement.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        UserDto userDto = new UserDto();
        userDto.username = username;
        userDto.email = email;
        userDto.password = password;
        String result = userService.register(userDto);
        Map<String , String> response = new HashMap<>();

        if(result.equals("User already exists")){
            model.addAttribute("error" , "User already exists");
            return "register";
        }
        if(result.equals("Cannot assign ADMIN role")){
            model.addAttribute("error", "You cannot register as ADMIN");
            return "register";
        }
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model ,
            HttpSession session
    ){
        UserDto userDto = new UserDto();
        userDto.username = username;
        userDto.password = password;

        String userType = userService.login(userDto);
        session.setAttribute("role" ,userType);
        session.setAttribute("username" ,username);

        if(userType.equals("ADMIN") || userType.equals("USER")){
            return "redirect:/home";
        }
        else{
            model.addAttribute("error" , "InValid User");
            return "redirect:/login";
        }


    }



    @GetMapping("/userHomePage")
    public String userHome(){
        return "userHomePage";
    }



}
