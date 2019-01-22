package com.pitech.services;

import com.pitech.dtos.ItemCartDto;
import com.pitech.dtos.ShoppingCartDto;
import com.pitech.models.ShoppingCart;

import java.util.List;

public interface CartService {

    ShoppingCartDto removeItemFromShoppingCart(Long id, ShoppingCartDto shoppingCartDto) throws Exception;

    ShoppingCart getShoppingCart();

    ShoppingCartDto addItemInShoppingCart(Long id, ShoppingCartDto shoppingCartDto, Integer quantity) throws Exception;

    ShoppingCart addItemsFromCookieInShoppingCart(ShoppingCartDto shoppingCartDto);

    ShoppingCartDto removeAllItems(ShoppingCartDto shoppingCartDto);

    ShoppingCartDto updateShoppingCart(ShoppingCartDto shoppingCartDto,List<ItemCartDto> booksKeyAndNewQuantities);
}
