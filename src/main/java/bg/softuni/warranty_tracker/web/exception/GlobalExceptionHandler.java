package bg.softuni.warranty_tracker.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.customExceptions.ActiveClaimException;
import bg.softuni.warranty_tracker.customExceptions.DataMapException;
import bg.softuni.warranty_tracker.customExceptions.DuplicateEntityException;
import bg.softuni.warranty_tracker.customExceptions.InvalidSessionException;
import bg.softuni.warranty_tracker.customExceptions.InvalidStatusTransitionException;
import bg.softuni.warranty_tracker.customExceptions.ObjectNotFoundException;
import bg.softuni.warranty_tracker.customExceptions.UserException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public ModelAndView handleConflict(DuplicateEntityException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.CONFLICT.value());
        return modelAndView;
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleNotFound(ObjectNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.NOT_FOUND.value());
        return modelAndView;
    }

    @ExceptionHandler(ActiveClaimException.class)
    public ModelAndView handleActiveClaim(ActiveClaimException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.BAD_REQUEST.value());
        return modelAndView;
    }

    @ExceptionHandler(InvalidSessionException.class)
    public ModelAndView handleInvalidSession(InvalidSessionException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.UNAUTHORIZED.value());
        return modelAndView;
    }

    @ExceptionHandler(DataMapException.class)
    public ModelAndView handleDataMap(DataMapException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.BAD_REQUEST.value());
        return modelAndView;
    }

    @ExceptionHandler(UserException.class)
    public ModelAndView handleUser(UserException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.UNAUTHORIZED.value());
        return modelAndView;
    }

    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ModelAndView handleInvalidStatusTransition(InvalidStatusTransitionException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("statusCode", HttpStatus.BAD_REQUEST.value());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("errorMessage", ExceptionMessages.SOMETHING_WENT_WRONG);
        modelAndView.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return modelAndView;
    }
}
