package com.pitech.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto implements Serializable{

    private Long id;

    @Length(min = 2, message = "Title is invalid!")
    private String title;

    private Integer authorId;

    @Length(min = 2, max=5000, message = "Description is invalid!")
    private transient String details;

    @Min(value=1, message = "Price is invalid!")
    private Long price;

    private Integer categoryId;

    @Min(value = 1, message = "Quantity is invalid!")
    private Integer quantity;

    private String image;
}


