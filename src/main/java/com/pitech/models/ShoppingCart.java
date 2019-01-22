package com.pitech.models;

import com.pitech.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Pasc Raluca on 27.07.2017.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Status status = Status.ACTIVE;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemCart> items = new LinkedHashSet<>();

    @ManyToOne
    private User user;


    public void removeItem(Long itemId) {
        Optional<ItemCart> maybeItemCart = items.stream().filter(item -> item.getBook().getId().equals(itemId)).findFirst();

        if (maybeItemCart.isPresent()) {
            items.remove(maybeItemCart.get());
        }

    }

    public void addItem(ItemCart itemCart, Integer quantity) {

        itemCart.setQty(quantity);
        items.add(itemCart);
    }

    public void removeAll(ShoppingCart shoppingCart) {

        items.removeAll(shoppingCart.getItems());
    }
}
