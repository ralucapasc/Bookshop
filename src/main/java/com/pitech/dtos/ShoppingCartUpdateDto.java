package com.pitech.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartUpdateDto {

    Long bookId;

    Integer quantity;
}
