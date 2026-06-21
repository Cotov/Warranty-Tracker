package bg.softuni.warranty_tracker.web.product;

import bg.softuni.warranty_tracker.service.product.ProductService;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.model.dto.product.EditProductRequest;
import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.product.ProductFormRequest;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.security.SessionUtils;
import bg.softuni.warranty_tracker.service.user.UserService;
import bg.softuni.warranty_tracker.service.vendor.VendorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
    public ModelAndView registerProduct(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/register-product");

        ProductFormRequest productFormRequest = new ProductFormRequest();
        modelAndView.addObject("productFormRequest", productFormRequest);

        productFormRequest.setRegisterVendorRequest(new RegisterVendorRequest());

        UserDto user = userService.getById(SessionUtils.getUserId(session));
        List<VendorDto> vendors = vendorService.getAllByUser(user);
        modelAndView.addObject("vendors", vendors);
        return modelAndView;
    }

    // todo make serial unique
    @PostMapping("/register")
    public ModelAndView registerProduct(@Valid @ModelAttribute ProductFormRequest productFormRequest,
            BindingResult bindingResult, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/register-product");

        if (bindingResult.hasErrors()) {
            UserDto user = userService.getById(SessionUtils.getUserId(session));
            modelAndView.addObject("vendors", vendorService.getAllByUser(user));
            return modelAndView;
        }

        UserDto userDto = userService.getById(SessionUtils.getUserId(session));
        productService.registerProduct(productFormRequest, userDto);
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @DeleteMapping("/{productId}")
    public ModelAndView deleteProduct(@PathVariable String productId, HttpSession session) {
        UserDto userDto = userService.getById(SessionUtils.getUserId(session));
        productService.deleteProductById(productId, userDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @GetMapping("/{productId}/edit")
    public ModelAndView getProduct(@PathVariable String productId, HttpSession session) {
        UserDto userDto = userService.getById(SessionUtils.getUserId(session));
        ProductDto productDto = productService.getById(productId, userDto);
        EditProductRequest editProductRequest = productService.getEditProductRequest(productDto);
        List<VendorDto> vendors = productService.getVendorsForEditProduct(productId, userDto);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/edit-product");
        modelAndView.addObject("editProductRequest", editProductRequest);
        modelAndView.addObject("vendors", vendors);
        return modelAndView;
    }

    @PutMapping("/{productId}")
    public ModelAndView editProduct(
            @PathVariable String productId,
            @Valid @ModelAttribute EditProductRequest editProductRequest,
            BindingResult bindingResult,
            HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/edit-product");
        UserDto userDto = userService.getById(SessionUtils.getUserId(session));
        if (productId == null) {
            throw new RuntimeException(ExceptionMessages.FAILED_TO_PARSE_UUID);
        }
        editProductRequest.setId(UUID.fromString(productId));
        if (bindingResult.hasErrors()) {
            List<VendorDto> vendors = productService.getVendorsForEditProduct(productId, userDto);
            editProductRequest.setVendorName(productService.getById(productId, userDto).getVendor().getName());
            modelAndView.addObject("editProductRequest", editProductRequest);
            modelAndView.addObject("vendors", vendors);
            return modelAndView;
        }
        productService.updateProduct(editProductRequest, userDto);
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }
}
