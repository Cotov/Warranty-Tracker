// package bg.softuni.warranty_tracker.web.product;

// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.servlet.ModelAndView;

// import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;

// @Controller
// @RequestMapping("/products")
// public class ProductController {

//     @GetMapping("/register")
//     public ModelAndView registerProduct() {
//         ModelAndView modelAndView = new ModelAndView();
//         modelAndView.setViewName("register-product");
//         modelAndView.addObject("register-product", new RegisterProductRequest());
//         List<VendorDto> vendors = vendorService.getAllForUser();
//         return modelAndView;
//     }
// }
