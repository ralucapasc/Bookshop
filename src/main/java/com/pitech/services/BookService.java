package com.pitech.services;

/**
 * Created by Pasc Raluca on 19.07.2017.
 */

import com.pitech.dtos.BookDto;
import com.pitech.dtos.FilterDto;
import com.pitech.dtos.ItemCartDto;
import com.pitech.models.Book;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {

    BookDto getBookDtoById(Long id) throws NotFoundException;

    Book getBookById(Long id) throws NotFoundException;

    Book saveBook(BookDto bookDto, MultipartFile file) throws IOException;

    Page<BookDto> getAllBooksByCategoryAndFilters(FilterDto filterDto, Pageable page);

    Page<BookDto> getRandomBooks(Integer categoryId, Integer numberOfRandomBooks);

    ItemCartDto getCartItemById(Long id);
}
