package com.pitech.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartDto {

    private Long id;

    private BookDto book;

    private Integer qty = 0;

    private Long subtotal = 0L;
}
