package com.pitech.repositories;

import com.pitech.models.ShoppingCart;
import com.pitech.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Set<ShoppingCart> findAllByUser(User user);
}
