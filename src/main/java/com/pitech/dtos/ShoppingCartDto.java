package com.pitech.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDto {

    private Set<ItemCartDto> items = new LinkedHashSet<>();

    private Long total = 0L;

    public void addItem(ItemCartDto itemCartDto, Integer quantity) {

        itemCartDto.setQty(quantity);
        items.add(itemCartDto);
    }

    public void deleteItem(ItemCartDto itemCartDto) {
        items.removeIf(item ->
            item.getId().equals(itemCartDto.getId())
        );
    }
}
