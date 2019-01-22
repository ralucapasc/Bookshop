package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Username {

    GUEST("guest");

    private String key;
}
