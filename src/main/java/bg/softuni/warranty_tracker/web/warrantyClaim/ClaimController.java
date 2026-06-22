package bg.softuni.warranty_tracker.web.warrantyClaim;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.AddClaimRequest;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.EditClaimRequest;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;
import bg.softuni.warranty_tracker.security.SessionUtils;
import bg.softuni.warranty_tracker.service.warrantyClaim.ClaimService;
import bg.softuni.warranty_tracker.service.user.UserService;
import bg.softuni.warranty_tracker.service.product.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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

        boolean hasActiveClaim = claimService.hasActiveClaim(claims);
        modelAndView.addObject("hasActiveClaim", hasActiveClaim);

        ProductDto product = productService.getById(productId, userDto);
        modelAndView.addObject("product", product);

        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addClaim(@PathVariable String productId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("claims/add-claim");

        UserDto userDto = userService.getById(SessionUtils.getUserId(session));
        ProductDto product = productService.getById(productId, userDto);
        AddClaimRequest addClaimRequest = AddClaimRequest.builder()
                .product(product)
                .build();
        modelAndView.addObject("addClaimRequest", addClaimRequest);

        List<ClaimDto> claims = claimService.getClaims(productId, userDto);
        boolean hasActiveClaim = claimService.hasActiveClaim(claims);
        modelAndView.addObject("hasActiveClaim", hasActiveClaim);

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addClaim(@PathVariable String productId, @Valid @ModelAttribute AddClaimRequest addClaimRequest,
            BindingResult bindingResult, HttpSession session) {

        UserDto userDto = userService.getById(SessionUtils.getUserId(session));
        ProductDto product = productService.getById(productId, userDto);
        addClaimRequest.setProduct(product);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("claims/add-claim");
            modelAndView.addObject("addClaimRequest", addClaimRequest);
            List<ClaimDto> claims = claimService.getClaims(productId, userDto);
            boolean hasActiveClaim = claimService.hasActiveClaim(claims);
            modelAndView.addObject("hasActiveClaim", hasActiveClaim);
            return modelAndView;
        }
        claimService.addClaim(addClaimRequest, userDto);
        modelAndView.setViewName("redirect:/products/{productId}/claims");
        return modelAndView;
    }

    @GetMapping("/{claimId}/edit")
    public ModelAndView editClaim(@PathVariable String productId, @PathVariable String claimId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("claims/edit-claim");

        UserDto userDto = userService.getById(SessionUtils.getUserId(session));
        ProductDto product = productService.getById(productId, userDto);
        modelAndView.addObject("product", product);

        ClaimDto claim = claimService.getById(claimId, userDto);
        modelAndView.addObject("claim", claim);

        EditClaimRequest editClaimRequest = claimService.getEditClaimRequest(claim);
        modelAndView.addObject("editClaimRequest", editClaimRequest);

        Set<ClaimStatus> validStatusTransitions = claimService.getValidStatusTransitions(claim.getStatus());
        modelAndView.addObject("validStatusTransitions", validStatusTransitions);

        return modelAndView;
    }

    @PutMapping("/{claimId}/edit")
    public ModelAndView editClaim(@PathVariable String productId, @PathVariable String claimId,
            @Valid @ModelAttribute EditClaimRequest editClaimRequest,
            BindingResult bindingResult, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("claims/edit-claim");
        UserDto userDto = userService.getById(SessionUtils.getUserId(session));

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("editClaimRequest", editClaimRequest);

            ProductDto product = productService.getById(productId, userDto);
            modelAndView.addObject("product", product);

            ClaimDto claim = claimService.getById(claimId, userDto);
            modelAndView.addObject("claim", claim);

            Set<ClaimStatus> validStatusTransitions = claimService.getValidStatusTransitions(claim.getStatus());
            modelAndView.addObject("validStatusTransitions", validStatusTransitions);

            return modelAndView;
        }
        claimService.updateClaim(editClaimRequest, userDto);
        modelAndView.setViewName("redirect:/products/{productId}/claims");
        return modelAndView;
    }

}
