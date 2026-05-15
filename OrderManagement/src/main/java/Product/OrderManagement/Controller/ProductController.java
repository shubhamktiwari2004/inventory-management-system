package Product.OrderManagement.Controller;


import Product.OrderManagement.Dto.ProductDto;
import Product.OrderManagement.Model.CartModel;
import Product.OrderManagement.Model.ProductModel;
import Product.OrderManagement.Repository.CartRepo;
import Product.OrderManagement.Service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepo cartRepo;

    @GetMapping("/home")
    public String getProducts(Model model, HttpSession session){

        String username = (String) session.getAttribute("username");
        List<ProductDto> products = productService.getProducts(session);
        List<CartModel> carts = cartRepo.findAllByUsername(username);
        System.out.println("cart : "+carts);

        model.addAttribute("products" , products);
        model.addAttribute("carts" , carts);
        if(session.getAttribute("role").equals("ADMIN")){
            return "adminHomePage";
        }
        else if (session.getAttribute("role").equals("USER")){
            return "userHomePage";

        }
        else{
            return "redirect:/login";
        }
    }


    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name,
                             @RequestParam int price,
                             @RequestParam int quantity,
                             @RequestParam int enable ,
                             HttpSession session)
    {
        String username = (String) session.getAttribute("username");
        ProductDto dto = new ProductDto();
        dto.name = name;
        dto.price = price;
        dto.quantity = quantity;
        dto.enable = enable;

        productService.addProduct(dto,username);
        return "redirect:/home";

    }
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id , Model model)
    {
        Optional<ProductModel> product = productService.editProduct(id);
        System.out.println("shubham"+product);
        model.addAttribute("product" , product.get());
        return "editProduct";

    }

    @PostMapping("/updateProduct")
    public String updateProduct(@RequestParam String id,
                                @RequestParam String name,
                                @RequestParam long productId,
                                @RequestParam int price,
                                @RequestParam int quantity,
                                @RequestParam int enable ,
                                HttpSession session)
    {
        String username = (String) session.getAttribute("username");
        ProductDto dto = new ProductDto();
        dto.id = Long.parseLong(id);
        dto.name = name;
        dto.price = price;
        dto.quantity = quantity;
        dto.enable = enable;
        dto.productId = productId;

        productService.updateProduct(dto , username);
        return "redirect:/home";

    }


}
