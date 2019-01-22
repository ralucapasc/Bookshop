package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortBy {
    NAME("Name"),
    PRICE("Price");

    private String key;
}
