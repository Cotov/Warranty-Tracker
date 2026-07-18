package bg.softuni.warranty_tracker.web.claimHistory;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.ClaimHistoryDto;
import bg.softuni.warranty_tracker.security.UserPrincipal;
import bg.softuni.warranty_tracker.service.audit.ClaimHistoryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/products/{productId}/claims/{claimId}/history")
@RequiredArgsConstructor
public class ClaimHistoryController {

    private final ClaimHistoryService claimHistoryService;

    @GetMapping
    public ModelAndView getClaimHistory(@PathVariable UUID productId, @PathVariable UUID claimId,
            @AuthenticationPrincipal UserPrincipal principal) {
        ClaimHistoryDto claimHistoryDto = claimHistoryService.getClaimHistory(claimId, principal.getId());
        ModelAndView modelAndView = new ModelAndView("claims/claim-history");
        modelAndView.addObject("claimHistoryDto", claimHistoryDto);
        modelAndView.addObject("productId", productId);
        modelAndView.addObject("claimId", claimId);
        return modelAndView;
    }

}
