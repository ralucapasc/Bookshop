package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Direction {
    ASC("Asc"),
    DESC("Desc");

    private String key;
}