package com.pitech.dtos;

import com.pitech.enums.Direction;
import com.pitech.enums.SortBy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SortDto {

    private SortBy sortBy;
    private Direction direction;
}
