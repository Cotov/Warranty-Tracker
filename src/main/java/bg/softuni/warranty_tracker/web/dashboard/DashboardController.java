package bg.softuni.warranty_tracker.web.dashboard;

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
public class DashboardController {

    private final ProductService productService;
    private final UserService userService;

    public DashboardController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping()
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard/dashboard");
        UserDto userDto = userService.getById("5d2ccb89-21e6-4efc-8515-f5cd87ce1a2b"); // until session implemented
        List<ProductDto> products = productService.getAllProducts(userDto);
        modelAndView.addObject("userDto", userDto);
        modelAndView.addObject("products", products);
        return modelAndView;
    }
}
