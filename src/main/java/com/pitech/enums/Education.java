package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Pasc Raluca on 27.07.2017.
 */
@AllArgsConstructor
@Getter
public enum Education {
    UNDERGRADUATE("Under graduate"),
    GRADUATE("Graduate"),
    UNIVERSITY("University"),
    POST_UNIVERSITY("Post university");

    private String key;
}
