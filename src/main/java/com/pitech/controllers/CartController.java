package com.pitech.controllers;

import com.pitech.dtos.ItemCartDto;
import com.pitech.dtos.ShoppingCartDto;
import com.pitech.enums.Username;
import com.pitech.services.CartService;
import com.pitech.services.CartServiceAuthenticated;
import com.pitech.services.CategoryService;
import com.pitech.services.UserService;
import com.pitech.utils.CookieUtils;
import com.pitech.utils.EncodeDecodeCookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CartController {

    private UserService userService;
    private CategoryService categoryService;
    private CartService cartService;
    private CartServiceAuthenticated cartServiceAuthenticated;
    private static final String SHOPPING_CART_DTO = "shoppingCartDto";

    @Autowired
    public CartController(UserService userService, CategoryService categoryService, CartService cartService, CartServiceAuthenticated cartServiceAuthenticated) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.cartServiceAuthenticated = cartServiceAuthenticated;
    }

    @RequestMapping(value = "/merge-shopping-cart", method = RequestMethod.GET)
    public String mergeShoppingCarts(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        ShoppingCartDto cookieShoppingCart = EncodeDecodeCookieUtils.decode(CookieUtils.getCookie(request).getValue(), ShoppingCartDto.class);
        cartService.addItemsFromCookieInShoppingCart(cookieShoppingCart);

        addAttributesAtModel(model, cookieShoppingCart);

        CookieUtils.setCookie(response, null);

        return "redirect:/showBooks";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String showCartAuthenticated(HttpServletRequest request, Model model) throws Exception {

        ShoppingCartDto shoppingCartDto = getShoppingCart(request);

        addAttributesAtModel(model, shoppingCartDto);

        return "cart";
    }

    @RequestMapping(value = {"/add-to-cart/{id}"}, method = RequestMethod.POST)
    public String addBookInShoppingCart(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {


        Integer quantity = Integer.parseInt(request.getParameter("qty"));
        ShoppingCartDto shoppingCartDto = getShoppingCart(request);

        shoppingCartDto = cartService.addItemInShoppingCart(id, shoppingCartDto, quantity);

        addAttributesAtModel(model, shoppingCartDto);

        saveShoppingCart(response, shoppingCartDto);

        return "fragments/rightSidebar";
    }

    @RequestMapping(value = {"remove-item/{id}"}, method = RequestMethod.POST)
    public String removeItemFromShoppingCart(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Model model)
            throws Exception {

        removeItem(id, model, response, request);

        return "fragments/rightSidebar";
    }

    @RequestMapping(value = {"remove-item/cart/{id}"}, method = RequestMethod.POST)
    public String removeItemFromCartPage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Model model)
            throws Exception {

        removeItem(id, model, response, request);

        return "fragments/formCart";
    }

    @RequestMapping(value = {"remove-all"}, method = RequestMethod.POST)
    public String removeAll(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        ShoppingCartDto shoppingCartDto = getShoppingCart(request);
        shoppingCartDto = cartService.removeAllItems(shoppingCartDto);

        addAttributesAtModel(model, shoppingCartDto);
        saveShoppingCart(response, shoppingCartDto);

        return "fragments/formCart";
    }

    @RequestMapping(value = {"update-quantity"}, method = RequestMethod.POST)
    public String updateQuantity(HttpServletRequest request, HttpServletResponse response, @RequestBody List<ItemCartDto> booksKeyAndNewQuantities,
                                 Model model) throws Exception {

        ShoppingCartDto shoppingCartDto = getShoppingCart(request);
        shoppingCartDto = cartService.updateShoppingCart(shoppingCartDto, booksKeyAndNewQuantities);

        addAttributesAtModel(model, shoppingCartDto);
        saveShoppingCart(response, shoppingCartDto);

        return "fragments/formCart";
    }

    private void addAttributesAtModel(Model model, ShoppingCartDto shoppingCartDto) {

        String username = userService.getUsernameAuthenticated();

        model.addAttribute("categories", categoryService.listAllCategories());
        model.addAttribute(SHOPPING_CART_DTO, shoppingCartDto);
        model.addAttribute("username", username);
    }

    private ShoppingCartDto getShoppingCart(HttpServletRequest request) throws Exception {

        if (userService.getUsernameAuthenticated().equals(Username.GUEST.getKey())) {
            String cookie = CookieUtils.getCookie(request).getValue();

            return EncodeDecodeCookieUtils.decode(cookie, ShoppingCartDto.class);
        }

        return cartServiceAuthenticated.getShoppingCartDto(cartService.getShoppingCart());
    }

    private void saveShoppingCart(HttpServletResponse response, ShoppingCartDto shoppingCartDto) throws Exception {

        if (userService.getUsernameAuthenticated().equals(Username.GUEST.getKey())) {

            String cookie = EncodeDecodeCookieUtils.encode(shoppingCartDto);
            CookieUtils.setCookie(response, cookie);
        }
    }

    private void removeItem(Long id, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {

        ShoppingCartDto shoppingCartDto = getShoppingCart(request);
        shoppingCartDto = cartService.removeItemFromShoppingCart(id, shoppingCartDto);

        addAttributesAtModel(model, shoppingCartDto);
        saveShoppingCart(response, shoppingCartDto);
    }
}
