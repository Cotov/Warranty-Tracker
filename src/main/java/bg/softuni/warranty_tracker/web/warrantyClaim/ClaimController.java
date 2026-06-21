package bg.softuni.warranty_tracker.web.warrantyClaim;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.security.SessionUtils;
import bg.softuni.warranty_tracker.service.warrantyClaim.ClaimService;
import bg.softuni.warranty_tracker.service.user.UserService;
import bg.softuni.warranty_tracker.service.product.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("products/{productId}/claims")
public class ClaimController {

    private final ClaimService claimService;
    private final UserService userService;
    private final ProductService productService;

    public ClaimController(ClaimService claimService, UserService userService, ProductService productService) {
        this.claimService = claimService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping()
    public ModelAndView getClaims(@PathVariable String productId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("claims/claims");

        UserDto userDto = userService.getById(SessionUtils.getUserId(session));
        List<ClaimDto> claims = claimService.getClaims(productId, userDto);
        modelAndView.addObject("claims", claims);

        ProductDto product = productService.getById(productId, userDto);
        modelAndView.addObject("product", product);

        return modelAndView;
    }

}
