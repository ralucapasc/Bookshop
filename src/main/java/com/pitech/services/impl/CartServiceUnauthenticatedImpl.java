package com.pitech.services.impl;

import com.pitech.dtos.BookDto;
import com.pitech.dtos.ItemCartDto;
import com.pitech.dtos.ShoppingCartDto;
import com.pitech.mappers.ShoppingCartMapper;
import com.pitech.services.BookService;
import com.pitech.services.CartServiceUnauthenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CartServiceUnauthenticatedImpl implements CartServiceUnauthenticated {

    private final ShoppingCartMapper shoppingCartMapper = ShoppingCartMapper.INSTANCE;
    private BookService bookService;

    @Autowired
    public CartServiceUnauthenticatedImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ShoppingCartDto addCartItemInShoppingCart(Long id, ShoppingCartDto shoppingCartDto, Integer quantity) throws Exception {

        ItemCartDto itemCart = bookService.getCartItemById(id);
        BookDto bookDto = bookService.getBookDtoById(id);

        return shoppingCartMapper.addItemInShoppingCart(setTotal(shoppingCartDto), itemCart, quantity, bookDto);
    }

    @Override
    public ShoppingCartDto removeItemFromShoppingCart(Long id, ShoppingCartDto shoppingCartDto) throws Exception {

        ItemCartDto itemCart = bookService.getCartItemById(id);

        return shoppingCartMapper.removeItemFromCart(setTotal(shoppingCartDto), itemCart);
    }

    @Override
    public ShoppingCartDto updateQuantityFromCart(ShoppingCartDto shoppingCartDto, List<ItemCartDto> booksKeyAndNewQuantities) {

        Set<ItemCartDto> itemCartDtos = shoppingCartDto.getItems();

        itemCartDtos.forEach(itemCartDto -> {
            booksKeyAndNewQuantities.forEach(book -> {
                if (book.getBook().getId().equals(itemCartDto.getBook().getId()) && !(book.getQty().equals(itemCartDto.getQty()))) {
                    itemCartDto.setQty(book.getQty());
                    itemCartDto.setSubtotal(itemCartDto.getQty() * itemCartDto.getBook().getPrice());
                }
            });
        });

        return setTotal(shoppingCartDto);
    }

    private ShoppingCartDto setTotal(ShoppingCartDto shoppingCartDto) {

        Set<ItemCartDto> itemCartDtos = shoppingCartDto.getItems();
        shoppingCartDto.setTotal(0L);

        itemCartDtos.forEach(item -> shoppingCartDto.setTotal(shoppingCartDto.getTotal() + item.getSubtotal()));

        return shoppingCartDto;
    }

}
