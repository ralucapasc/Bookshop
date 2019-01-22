package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Pasc Raluca on 27.07.2017.
 */
@AllArgsConstructor
@Getter
public enum OrderStatus {
    UNPROCESSED("Unprocessed"),
    IN_PROCESS("In process"),
    DELIVER("Deliver"),
    FAILED("Failed"),
    DONE("Done");

    private String key;

}
