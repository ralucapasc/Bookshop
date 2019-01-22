package com.pitech.dtos;

import com.pitech.enums.Availability;
import com.pitech.enums.FilterInterval;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FilterDto {

    private FilterInterval filterInterval;
    private Integer categoryId;
    private Availability availability;
    private Integer page = 1;
}
