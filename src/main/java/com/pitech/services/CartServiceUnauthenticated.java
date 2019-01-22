package com.pitech.services;

import com.pitech.dtos.ItemCartDto;
import com.pitech.dtos.ShoppingCartDto;

import java.util.List;

public interface CartServiceUnauthenticated {

    ShoppingCartDto addCartItemInShoppingCart(Long id, ShoppingCartDto shoppingCartDto, Integer quantity) throws Exception;

    ShoppingCartDto removeItemFromShoppingCart(Long id, ShoppingCartDto shoppingCartDto) throws Exception;

    ShoppingCartDto updateQuantityFromCart(ShoppingCartDto shoppingCartDto, List<ItemCartDto> booksKeyAndNewQuantities);

}
