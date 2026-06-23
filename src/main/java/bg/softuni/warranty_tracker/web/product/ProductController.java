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
import bg.softuni.warranty_tracker.mapper.product.ProductMapper;
import bg.softuni.warranty_tracker.model.dto.product.EditProductRequest;
import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.product.RegisterProductRequest;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.security.SessionUtils;
import bg.softuni.warranty_tracker.service.vendor.VendorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final VendorService vendorService;
    private final ProductMapper productMapper;

    public ProductController(VendorService vendorService, ProductService productService,
            ProductMapper productMapper) {
        this.vendorService = vendorService;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/register")
    public ModelAndView registerProduct(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/register-product");

        RegisterProductRequest registerProductRequest = new RegisterProductRequest();
        modelAndView.addObject("registerProductRequest", registerProductRequest);

        registerProductRequest.setRegisterVendorRequest(new RegisterVendorRequest());

        UserDto user = SessionUtils.getUserDto(session);
        List<VendorDto> vendors = vendorService.getAllByUser(user);
        modelAndView.addObject("vendors", vendors);
        return modelAndView;
    }

    // todo make serial unique
    @PostMapping("/register")
    public ModelAndView registerProduct(@Valid @ModelAttribute RegisterProductRequest registerProductRequest,
            BindingResult bindingResult, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/register-product");
        UserDto user = SessionUtils.getUserDto(session);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("vendors", vendorService.getAllByUser(user));
            return modelAndView;
        }

        productService.registerProduct(registerProductRequest, user);
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @DeleteMapping("/{productId}")
    public ModelAndView deleteProduct(@PathVariable String productId, HttpSession session) {
        UserDto userDto = SessionUtils.getUserDto(session);
        productService.deleteProductById(productId, userDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @GetMapping("/{productId}/edit")
    public ModelAndView getProduct(@PathVariable String productId, HttpSession session) {
        UserDto userDto = SessionUtils.getUserDto(session);
        ProductDto productDto = productService.getById(productId, userDto);
        EditProductRequest editProductRequest = productMapper.toEditProductRequest(productDto);
        editProductRequest.setRegisterVendorRequest(new RegisterVendorRequest());
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
        UserDto userDto = SessionUtils.getUserDto(session);
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
