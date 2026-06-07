package bg.softuni.warranty_tracker.web.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.service.product.ProductService;
import bg.softuni.warranty_tracker.service.user.UserService;

@Controller
@RequestMapping("/dashboard")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping()
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("products/dashboard");
        UserDto userDto = userService.getById("6e808b73-54aa-4d7b-9b2b-9975d72bb0ed"); // until session implemented
        List<ProductDto> products = productService.getAllProducts(userDto);
        modelAndView.addObject("userDto", userDto);
        modelAndView.addObject("products", products);
        return modelAndView;
    }
}
