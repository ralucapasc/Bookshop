package com.pitech.services.impl;

import com.pitech.dtos.ItemCartDto;
import com.pitech.dtos.ShoppingCartDto;
import com.pitech.enums.Status;
import com.pitech.mappers.ShoppingCartMapper;
import com.pitech.models.Book;
import com.pitech.models.ItemCart;
import com.pitech.models.ShoppingCart;
import com.pitech.models.User;
import com.pitech.repositories.ShoppingCartRepository;
import com.pitech.services.BookService;
import com.pitech.services.CartServiceAuthenticated;
import com.pitech.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceAuthenticatedImpl implements CartServiceAuthenticated {

    private final ShoppingCartMapper shoppingCartMapper = ShoppingCartMapper.INSTANCE;
    private ShoppingCartRepository shoppingCartRepository;
    private BookService bookService;
    private UserService userService;

    @Autowired
    public CartServiceAuthenticatedImpl(ShoppingCartRepository shoppingCartRepository, BookService bookService, UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    @Override
    public ShoppingCartDto addBookInShoppingCart(Long id, Integer quantity) throws NotFoundException {

        String username = userService.getUsernameAuthenticated();
        User user = userService.getUserByUsername(username);
        Book book = bookService.getBookById(id);
        ShoppingCart shoppingCart = getShoppingCartByUser(user);

        shoppingCart = shoppingCartMapper.addItemsInShoppingCart(shoppingCart, user, quantity, book);
        shoppingCartRepository.save(shoppingCart);

        return getShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCart getShoppingCartByUser(User user) {

        Set<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser(user);

        Optional<ShoppingCart> maybeShoppingCart = shoppingCarts.stream().filter(shop -> shop.getStatus().equals(Status.ACTIVE)).findFirst();

        return maybeShoppingCart.orElse(new ShoppingCart());
    }

    @Override
    public ShoppingCartDto removeBookFromShoppingCart(Long id) {

        String username = userService.getUsernameAuthenticated();
        User user = userService.getUserByUsername(username);
        ShoppingCart shoppingCart = getShoppingCartByUser(user);

        shoppingCart.removeItem(id);
        shoppingCartRepository.saveAndFlush(shoppingCart);

        return getShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto getShoppingCartDto(ShoppingCart shoppingCart) {

        return shoppingCartMapper.toDtoWithTotal(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateShoppingCart(ShoppingCart shoppingCart, List<ItemCartDto> booksKeyAndNewQuantities) {

        Set<ItemCart> itemCarts = shoppingCart.getItems();

        itemCarts.forEach(itemCart -> {
            booksKeyAndNewQuantities.forEach(book -> {
                if (book.getBook().getId().equals(itemCart.getBook().getId()) && !(book.getQty().equals(itemCart.getQty()))) {
                    itemCart.setQty(book.getQty());
                }
            });
        });

        shoppingCartRepository.saveAndFlush(shoppingCart);

        return getShoppingCartDto(shoppingCart);
    }
}
