package com.pitech.repositories.impl;

import com.pitech.dtos.FilterDto;
import com.pitech.models.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class BookSpecs {

    public static Specification<Book> booksWithFilters(FilterDto filterDto) {

        if (filterDto.getFilterInterval() == null && filterDto.getAvailability() == null) {
            return booksByCategory(filterDto);
        }

        if (filterDto.getFilterInterval() == null) {
            return (root, query, builder) -> Specifications.where(bookByAvailability(filterDto))
                    .and(booksByCategory(filterDto)).toPredicate(root, query, builder);
        }

        final Specification<Book> specification = (root, query, builder) -> builder.between(root.get("price"),
                filterDto.getFilterInterval().getStart(), filterDto.getFilterInterval().getEnd());

        if (filterDto.getAvailability() == null) {
            return (root, query, builder) -> Specifications.where(booksByCategory(filterDto))
                    .and(specification).toPredicate(root, query, builder);
        }

        return (root, query, builder) ->
                Specifications.where(booksByCategory(filterDto)).and(specification).and(bookByAvailability(filterDto)).toPredicate(root, query, builder);
    }

    public static Specification<Book> booksByCategory(FilterDto filterDto) {
        if (filterDto.getCategoryId() != null) {

            return (root, query, builder) -> builder.equal(root.get("category").get("id"), filterDto.getCategoryId());
        }

        return null;
    }

    private static Specification<Book> bookByAvailability(FilterDto filterDto) {
        return (root, query, builder) -> builder.equal(root.get("availability"), filterDto.getAvailability());
    }
}
