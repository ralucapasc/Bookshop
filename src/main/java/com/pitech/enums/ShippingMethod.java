package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Pasc Raluca on 27.07.2017.
 */
@AllArgsConstructor
@Getter
public enum ShippingMethod {
    BANK_ORDER("Bank order"),
    ON_DELIVERY("On delivery");

    private String key;
}
