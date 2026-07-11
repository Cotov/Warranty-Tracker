package bg.softuni.warranty_tracker.web.user;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import bg.softuni.warranty_tracker.service.user.UserService;
import jakarta.validation.Valid;
import bg.softuni.warranty_tracker.model.dto.user.UserRegisterRequest;
import bg.softuni.warranty_tracker.security.UserPrincipal;
import bg.softuni.warranty_tracker.model.dto.user.UserProfileEditRequest;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.user.UserLoginRequest;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterForm() {
        ModelAndView modelAndView = new ModelAndView("users/register");
        modelAndView.addObject("userRegisterRequest", new UserRegisterRequest());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute UserRegisterRequest userRegisterRequest,
            BindingResult bindingResult, @AuthenticationPrincipal UserPrincipal principal) {

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("users/register");
            return modelAndView;
        }
        UUID userId = userService.register(userRegisterRequest);
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView modelAndView = new ModelAndView("users/login");
        modelAndView.addObject("userLoginRequest", new UserLoginRequest());
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView showProfileForm(@AuthenticationPrincipal UserPrincipal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/profile");

        UserDto user = userService.getById(principal.getId());
        UserProfileEditRequest userProfileEditRequest = userMapper.toUserProfileEditRequest(user);

        modelAndView.addObject("userProfileEditRequest", userProfileEditRequest);
        return modelAndView;
    }

    @PutMapping("/profile")
    public ModelAndView editProfile(@Valid @ModelAttribute UserProfileEditRequest userProfileEditRequest,
            BindingResult bindingResult, @AuthenticationPrincipal UserPrincipal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/profile");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("userProfileEditRequest", userProfileEditRequest);
            return modelAndView;
        }
        userService.editProfile(userProfileEditRequest, principal.getId());
        modelAndView.setViewName("redirect:/users/profile?updated=true");
        return modelAndView;
    }

}
