package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Pasc Raluca on 27.07.2017.
 */
@AllArgsConstructor
@Getter
public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String key;

}
