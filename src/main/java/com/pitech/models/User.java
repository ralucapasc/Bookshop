package com.pitech.models;

import com.pitech.enums.Education;
import com.pitech.enums.Gender;
import com.pitech.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Pasc Raluca on 27.07.2017.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String mobile;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private Education education;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Role role = Role.USER;

    @OneToMany
    private Set<ShoppingCart> shoppingCarts = new LinkedHashSet<>();

    @OneToOne
    private Address billingAddress;

    @OneToOne
    private Address shippingAddress;

    public Role getRole() {
        return role == null ? Role.USER : role;
    }

    public void addShoppingCart(ShoppingCart shoppingCart){

        shoppingCarts.add(shoppingCart);
    }
}
