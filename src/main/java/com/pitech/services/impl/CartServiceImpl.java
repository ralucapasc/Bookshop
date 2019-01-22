package com.pitech.services.impl;

import com.pitech.dtos.ItemCartDto;
import com.pitech.dtos.ShoppingCartDto;
import com.pitech.enums.Status;
import com.pitech.enums.Username;
import com.pitech.mappers.ShoppingCartMapper;
import com.pitech.models.ItemCart;
import com.pitech.models.ShoppingCart;
import com.pitech.models.User;
import com.pitech.repositories.ShoppingCartRepository;
import com.pitech.services.CartService;
import com.pitech.services.CartServiceAuthenticated;
import com.pitech.services.CartServiceUnauthenticated;
import com.pitech.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    private final ShoppingCartMapper shoppingCartMapper = ShoppingCartMapper.INSTANCE;
    private ShoppingCartRepository shoppingCartRepository;
    private UserService userService;
    private CartServiceAuthenticated cartServiceAuthenticated;
    private CartServiceUnauthenticated cartServiceUnauthenticated;

    @Autowired
    public CartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserService userService, CartServiceAuthenticated cartServiceAuthenticated, CartServiceUnauthenticated cartServiceUnauthenticated) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.cartServiceAuthenticated = cartServiceAuthenticated;
        this.cartServiceUnauthenticated = cartServiceUnauthenticated;
    }

    @Override
    public ShoppingCartDto addItemInShoppingCart(Long id, ShoppingCartDto shoppingCartDto, Integer quantity) throws Exception {

        if (!userService.getUsernameAuthenticated().equals(Username.GUEST.getKey())) {

            return cartServiceAuthenticated.addBookInShoppingCart(id, quantity);
        }

        return cartServiceUnauthenticated.addCartItemInShoppingCart(id, shoppingCartDto, quantity);
    }

    @Override
    public ShoppingCartDto removeItemFromShoppingCart(Long id, ShoppingCartDto shoppingCartDto) throws Exception {

        if (!userService.getUsernameAuthenticated().equals(Username.GUEST.getKey())) {

            return cartServiceAuthenticated.removeBookFromShoppingCart(id);
        }

        return cartServiceUnauthenticated.removeItemFromShoppingCart(id, shoppingCartDto);
    }

    @Override
    public ShoppingCartDto removeAllItems(ShoppingCartDto shoppingCartDto) {


        if (!userService.getUsernameAuthenticated().equals(Username.GUEST.getKey())) {

            ShoppingCart shoppingCart = getShoppingCart();
            shoppingCart.removeAll(shoppingCart);
            shoppingCartRepository.saveAndFlush(shoppingCart);

            return shoppingCartMapper.toDtoWithTotal(shoppingCart);
        }

        shoppingCartDto.setItems(new LinkedHashSet<>());
        shoppingCartDto.setTotal(0L);
        return shoppingCartDto;
    }

    @Override
    public ShoppingCart getShoppingCart() {

        User user = userService.getUserByUsername(userService.getUsernameAuthenticated());

        Set<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser(user);
        Optional<ShoppingCart> maybeShoppingCart = shoppingCarts.stream().filter(shop -> shop.getStatus().equals(Status.ACTIVE)).findFirst();


        if (!maybeShoppingCart.isPresent()) {

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.addShoppingCart(shoppingCart);

            return shoppingCart;
        }

        return maybeShoppingCart.get();
    }

    @Override
    public ShoppingCart addItemsFromCookieInShoppingCart(ShoppingCartDto shoppingCartDto) {

        ShoppingCart shoppingCart = getShoppingCart();

        Set<ItemCart> itemCarts = shoppingCartMapper.getItemCartFromDto(shoppingCartDto, shoppingCart);

        itemCarts.forEach(item -> {
            try {
                cartServiceAuthenticated.addBookInShoppingCart(item.getId(), item.getQty());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });

        return shoppingCart;
    }

    @Override
    public ShoppingCartDto updateShoppingCart(ShoppingCartDto shoppingCartDto, List<ItemCartDto> booksKeyAndNewQuantities) {

        if (!userService.getUsernameAuthenticated().equals(Username.GUEST.getKey())) {

            ShoppingCart shoppingCart = getShoppingCart();
            return cartServiceAuthenticated.updateShoppingCart(shoppingCart, booksKeyAndNewQuantities);
        }

        return cartServiceUnauthenticated.updateQuantityFromCart(shoppingCartDto, booksKeyAndNewQuantities);
    }
}

