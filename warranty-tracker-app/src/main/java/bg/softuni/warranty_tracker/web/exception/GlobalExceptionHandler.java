package bg.softuni.warranty_tracker.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;
import lombok.extern.slf4j.Slf4j;

//todo flash attributes and rest api error handling
//rest api errorResponse DTO
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseApplicationException.class)
    public ModelAndView handleBaseApplicationException(BaseApplicationException exception) {
        ModelAndView modelAndView = new ModelAndView();
        log.error("Exception: {}", exception.getMessage());
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("statusCode", exception.getStatusCode().value());
        modelAndView.setStatus(exception.getStatusCode());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        log.error("Exception: {}", exception.getMessage());
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", ExceptionMessages.SOMETHING_WENT_WRONG);
        modelAndView.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }
}
