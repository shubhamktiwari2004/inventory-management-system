package Product.OrderManagement.Service;

import Product.OrderManagement.Dto.ProductDto;
import Product.OrderManagement.Model.ProductModel;
import Product.OrderManagement.Repository.ProductRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.ExceptionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductDto> getProducts(HttpSession session) {

        String username = (String) session.getAttribute("username");
        List<ProductModel> products;
        if(session.getAttribute("role").equals("ADMIN")) {

            //products = productRepo.findProductQuantity();
            products = productRepo.findProductQuantity(username);
        }
        else{

            products = productRepo.findEnableProductQuantity();
        }

        List<ProductDto> dtoList = new ArrayList<>();

        for (ProductModel model : products) {

            ProductDto dto = new ProductDto();
            dto.name = model.getName();
            dto.productId = model.getProductId();
            dto.price = model.getPrice();
            dto.quantity = model.getQuantity();
            dto.enable = model.getEnable();
            dto.id = model.getId();
            dtoList.add(dto);
        }
        return dtoList;


    }

    public void addProduct (ProductDto dto , String username){

        ProductModel model = new ProductModel();
        model.setName(dto.name);
        model.setUsername(username);
        model.setPrice(dto.price);
        model.setQuantity(dto.quantity);
        model.setEnable(dto.enable);
        productRepo.save(model);
        model.setProductId(model.getId());
        productRepo.save(model);

    }

    public Optional<ProductModel> editProduct(long id) {

        Optional<ProductModel> model = productRepo.findById(id);

        return model;
    }

    public void updateProduct(ProductDto dto , String username) {

        ProductModel model = new ProductModel();
        model.setId(dto.id);
        model.setUsername(username);
        model.setProductId(dto.productId);
        model.setName(dto.name);
        model.setPrice(dto.price);
        model.setQuantity(dto.quantity);
        model.setEnable(dto.enable);

        System.out.println("Model"+model.toString());


        productRepo.save(model);
    }
}
