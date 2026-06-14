package bg.softuni.warranty_tracker.web.product;

import bg.softuni.warranty_tracker.service.product.ProductService;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bg.softuni.warranty_tracker.model.dto.product.RegisterProductRequest;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.service.user.UserService;
import bg.softuni.warranty_tracker.service.vendor.VendorService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final VendorService vendorService;

    public ProductController(UserService userService, VendorService vendorService, ProductService productService) {
        this.userService = userService;
        this.vendorService = vendorService;
        this.productService = productService;
    }

    @GetMapping("/register")
    public ModelAndView registerProduct() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/register-product");

        RegisterProductRequest registerProductRequest = new RegisterProductRequest();
        modelAndView.addObject("registerProductRequest", registerProductRequest);

        registerProductRequest.setRegisterVendorRequest(new RegisterVendorRequest());

        UserDto user = userService.getById("5d2ccb89-21e6-4efc-8515-f5cd87ce1a2b");
        List<VendorDto> vendors = vendorService.getAllByUser(user);
        modelAndView.addObject("vendors", vendors);
        return modelAndView;
    }

    //todo make serial unique
    @PostMapping("/register")
    public ModelAndView registerProduct(@Valid @ModelAttribute RegisterProductRequest registerProductRequest,
            BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/register-product");

        if (bindingResult.hasErrors()) {
            UserDto user = userService.getById("5d2ccb89-21e6-4efc-8515-f5cd87ce1a2b");
            modelAndView.addObject("vendors", vendorService.getAllByUser(user));
            return modelAndView; 
        }

        UserDto userDto = userService.getById("5d2ccb89-21e6-4efc-8515-f5cd87ce1a2b");
        productService.registerProduct(registerProductRequest, userDto);
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

}
