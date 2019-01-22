package com.pitech.mappers;

import com.pitech.dtos.BookDto;
import com.pitech.dtos.ItemCartDto;
import com.pitech.models.Author;
import com.pitech.models.Book;
import com.pitech.models.Category;
import com.pitech.models.ItemCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BookMapper {
    public static BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "author", ignore = true)
    public abstract Book toEntity(BookDto bookDto);

    public Book toEntityWithBookImageCategoryAndAuthor(BookDto bookDto, String imagePath, Category category, Author author){
        Book book = toEntity(bookDto);
        book.setImage(imagePath);
        book.setAuthor(author);
        book.setCategory(category);

        return book;
    }

    @Mapping(target = "id", ignore = true)
    public abstract ItemCart toItemCart(Book book);

    public abstract ItemCart toItemCartFromItemCartDto(ItemCartDto itemCartDto);

    @Mapping(source = "book.author.id", target="authorId")
    @Mapping(source = "book.category.id", target="categoryId")
    public abstract BookDto toDto(Book book);

    public abstract List<BookDto> toDtoList(List<Book> books);

    public Page<BookDto> toDtoPage(Page<Book> bookPage, Pageable page){
        return new PageImpl<> (toDtoList(bookPage.getContent()), page, bookPage.getTotalElements());
    }

    public abstract ItemCartDto toCartItemDto(Book book);
}
