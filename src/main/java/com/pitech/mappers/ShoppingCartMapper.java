package com.pitech.mappers;

import com.pitech.dtos.BookDto;
import com.pitech.dtos.ItemCartDto;
import com.pitech.dtos.ShoppingCartDto;
import com.pitech.models.Book;
import com.pitech.models.ItemCart;
import com.pitech.models.ShoppingCart;
import com.pitech.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Optional;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShoppingCartMapper {

    public static ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    public abstract BookDto toBookDtoFromBook(Book book);

    public abstract ItemCartDto toItemCartDto(ItemCart itemCart);

    public abstract ShoppingCartDto toDto(ShoppingCart shoppingCart);

    public abstract Book toBookFromBookDto(BookDto bookDto);

    public abstract ItemCart toItemCartFormItemCartDto(ItemCartDto itemCartDto);

    public abstract ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);

    public ShoppingCartDto toDtoWithTotal(ShoppingCart shoppingCart) {

        ShoppingCartDto shoppingCartDto = toDto(shoppingCart);

        shoppingCart.getItems().forEach(itemCart ->
                shoppingCartDto.setTotal(shoppingCartDto.getTotal() +
                        (itemCart.getQty() * itemCart.getBook().getPrice()))
        );

        return shoppingCartDto;
    }

    public ShoppingCart addItemsInShoppingCart(ShoppingCart shoppingCart, User user, Integer quantity, Book book) {

        ItemCart itemCart = new ItemCart();
        itemCart.setBook(book);
        itemCart.setSubtotal(book.getPrice());

        Set<ItemCart> itemCarts = shoppingCart.getItems();

        Optional<ItemCart> maybeItemCart = itemCarts.stream().filter(item -> item.getBook().getId().equals(book.getId())).findAny();

        if (maybeItemCart.isPresent()) {
            maybeItemCart.get().setQty(maybeItemCart.get().getQty() + quantity);
            maybeItemCart.get().setSubtotal(maybeItemCart.get().getSubtotal() + itemCart.getSubtotal());
            return shoppingCart;

        }

        shoppingCart.addItem(itemCart, quantity);
        itemCart.setShoppingCart(shoppingCart);

        user.addShoppingCart(shoppingCart);

        return shoppingCart;
    }

    public ShoppingCartDto addItemInShoppingCart(ShoppingCartDto shoppingCartDto, ItemCartDto itemCartDto, Integer quantity,
                                                     BookDto bookDto) throws Exception {

        Set<ItemCartDto> items = shoppingCartDto.getItems();
        Optional<ItemCartDto> maybeItem = items.stream().filter(item -> item.getId().equals(itemCartDto.getId())).findAny();
        itemCartDto.setBook(bookDto);
        itemCartDto.setSubtotal(bookDto.getPrice());

        if (maybeItem.isPresent()) {
            maybeItem.get().setQty(maybeItem.get().getQty() + quantity);
            shoppingCartDto.setTotal(shoppingCartDto.getTotal() + (quantity * maybeItem.get().getBook().getPrice()));
            maybeItem.get().setSubtotal(maybeItem.get().getSubtotal() + itemCartDto.getSubtotal());
            return shoppingCartDto;

        }

        shoppingCartDto.addItem(itemCartDto, quantity);
        shoppingCartDto.setTotal(shoppingCartDto.getTotal() + (itemCartDto.getQty() * itemCartDto.getBook().getPrice()));

        return shoppingCartDto;
    }

    public ShoppingCartDto removeItemFromCart(ShoppingCartDto shoppingCartDto, ItemCartDto itemCartDto) throws Exception {

        Set<ItemCartDto> items = shoppingCartDto.getItems();
        Optional<ItemCartDto> maybeItem = items.stream().filter(item -> item.getId().equals(itemCartDto.getId())).findAny();

        if (maybeItem.isPresent()) {
            shoppingCartDto.setTotal(shoppingCartDto.getTotal() - (maybeItem.get().getQty() * maybeItem.get().getBook().getPrice()));
            shoppingCartDto.deleteItem(maybeItem.get());
        }

        return shoppingCartDto;
    }

    public Set<ItemCart> getItemCartFromDto(ShoppingCartDto shoppingCartDto, ShoppingCart shoppingCart) {

        ShoppingCart shop = toEntity(shoppingCartDto);

        Set<ItemCart> itemCarts = shop.getItems();

        itemCarts.forEach(item -> {
            item.setShoppingCart(shoppingCart);
        });

        return itemCarts;
    }
}
