package Product.OrderManagement.Controller;
import Product.OrderManagement.Model.CartModel;
import Product.OrderManagement.Service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService service;

    @GetMapping("/cart/add/{id}")
    public String addToCart(@PathVariable long id , RedirectAttributes redirectAttributes, Model model , HttpSession session)
    {


        String username = (String) session.getAttribute("username");
        String result = service.addToCart(id , session);
        System.out.println("Result :"+result);
        redirectAttributes.addFlashAttribute("message",result);


        return "redirect:/home";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteFromCart(@PathVariable long id , HttpSession session , Model model,RedirectAttributes redirectAttributes)
    {
        String username = (String) session.getAttribute("username");
        String result = service.deleteFromCart(id , session);
        redirectAttributes.addFlashAttribute("message",result);
        return "redirect:/home";
    }

    @GetMapping("/product/cart")
    public String viewCart(Model model , HttpSession session){

        String username = (String) session.getAttribute("username");
        List<CartModel> carts=service.viewCart(username);
        model.addAttribute("carts" , carts);
        return "cart";
    }

    @GetMapping("/cart/placeOrder/{id}")
    public String place(@PathVariable long id, Model model , HttpSession session){

        service.place(id , session);
        return "redirect:/home";
    }


}

