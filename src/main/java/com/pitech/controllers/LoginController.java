package com.pitech.controllers;

import com.pitech.dtos.UserRegisterDto;
import com.pitech.enums.Education;
import com.pitech.services.CartService;
import com.pitech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * Created by Pasc Raluca on 28.07.2017.
 */
@Controller
public class LoginController {

    private UserService userService;
    private CartService cartService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {

        model.addAttribute("username", userService.getUsernameAuthenticated() == null ? "guest" : userService.getUsernameAuthenticated());

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginProcess(@Valid UserRegisterDto userDto, BindingResult validationResult, HttpServletResponse response) throws Exception {

        if (validationResult.hasErrors()) {
            return "login";
        }

        return "redirect:/showBooks";

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegister(Model model) {

        model.addAttribute("educations", Arrays.asList(Education.values()));
        model.addAttribute("user", new UserRegisterDto());

        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") @Valid UserRegisterDto userDto, BindingResult validationResult, Model model) {
        model.addAttribute("educations", Arrays.asList(Education.values()));
        if (validationResult.hasErrors()) {
            return "register";
        }
        userService.register(userDto);

        return "redirect:/login";
    }
}
