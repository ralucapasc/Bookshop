package com.pitech.services;

import com.pitech.dtos.ItemCartDto;
import com.pitech.dtos.ShoppingCartDto;
import com.pitech.models.ShoppingCart;
import com.pitech.models.User;
import javassist.NotFoundException;

import java.util.List;

public interface CartServiceAuthenticated {

    ShoppingCartDto addBookInShoppingCart(Long id, Integer quantity) throws NotFoundException;

    ShoppingCartDto removeBookFromShoppingCart(Long id);

    ShoppingCart getShoppingCartByUser(User user);

    ShoppingCartDto getShoppingCartDto(ShoppingCart shoppingCart);

    ShoppingCartDto updateShoppingCart(ShoppingCart shoppingCart,List<ItemCartDto> booksKeyAndNewQuantities);
}
