package com.pitech.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum FilterInterval {
    FIRST_INTERVAL("first", 0L, 50L), SECOND_INTERVAL("second", 50L, 100L), THIRD_INTERVAL("third", 100L, 150L);

    private String key;
    private Long start;
    private Long end;

    public static FilterInterval valueOfString(String filterInterval) {
        for (FilterInterval filterIntervalEnum : Arrays.asList(FilterInterval.values())) {
            if (filterIntervalEnum.name().equals(filterInterval)) {
                continue;
            }
        }

        return null;
    }
}
