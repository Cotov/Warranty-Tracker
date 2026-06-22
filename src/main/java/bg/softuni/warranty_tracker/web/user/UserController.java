package bg.softuni.warranty_tracker.web.user;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import bg.softuni.warranty_tracker.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import bg.softuni.warranty_tracker.model.dto.user.UserRegisterRequest;
import bg.softuni.warranty_tracker.security.SessionUtils;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.user.UserLoginRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterForm() {
        ModelAndView modelAndView = new ModelAndView("users/register");
        modelAndView.addObject("userRegisterRequest", new UserRegisterRequest());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute UserRegisterRequest userRegisterRequest,
            BindingResult bindingResult, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("users/register");
            return modelAndView;
        }
        UUID userId = userService.register(userRegisterRequest);
        SessionUtils.setUserId(session, userId);
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView modelAndView = new ModelAndView("users/login");
        modelAndView.addObject("userLoginRequest", new UserLoginRequest());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute UserLoginRequest userLoginRequest, BindingResult bindingResult,
            HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("users/login");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        UserDto userDto = userService.login(userLoginRequest);
        SessionUtils.setUserId(session, userDto.getId());
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

}
