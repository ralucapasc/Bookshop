package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Pasc Raluca on 27.07.2017.
 */
@AllArgsConstructor
@Getter
public enum Availability {
    IN_STOCK("In stock"),
    OUT_OF_STOCK("Out of stock");

    private String key;
}
