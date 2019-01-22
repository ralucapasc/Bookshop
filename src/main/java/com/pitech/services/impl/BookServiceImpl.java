package com.pitech.services.impl;

import com.pitech.dtos.BookDto;
import com.pitech.dtos.FilterDto;
import com.pitech.dtos.ItemCartDto;
import com.pitech.enums.Direction;
import com.pitech.enums.SortBy;
import com.pitech.mappers.BookMapper;
import com.pitech.models.Book;
import com.pitech.repositories.BookRepository;
import com.pitech.repositories.impl.BookSpecs;
import com.pitech.services.AuthorService;
import com.pitech.services.BookService;
import com.pitech.services.CategoryService;
import com.pitech.services.FileService;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Pasc Raluca on 19.07.2017.
 */
@Service
@Setter
@Getter
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private CategoryService categoryService;
    private final BookMapper bookMapper = BookMapper.INSTANCE;
    private FileService fileService;
    private AuthorService authorService;
    private SortBy sortBy;
    private Direction direction;


    @Value("${files.path}")
    private String applicationFilesPath;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, FileService fileService, CategoryService categoryService, AuthorService authorService) {

        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.fileService = fileService;
    }

    public BookDto getBookDtoById(Long id) throws NotFoundException{

        Optional<Book> maybeBook = bookRepository.findById(id);
        if(!maybeBook.isPresent()) {
            throw new NotFoundException("Book does not exist");
        }
        Book book = maybeBook.get();

        return bookMapper.toDto(book);
    }

    public Book getBookById(Long id) throws NotFoundException{

        Optional<Book> maybeBook = bookRepository.findById(id);

        return maybeBook.get();
    }

    @Override
    public Page<BookDto> getAllBooksByCategoryAndFilters(FilterDto filterDto, Pageable page) {

        Page<Book> bookPage = bookRepository.findAll(BookSpecs.booksWithFilters(filterDto), page);

        return bookMapper.toDtoPage(bookPage, page);
    }

    @Override
    public Book saveBook(BookDto bookDto, MultipartFile file) throws IOException {

        String fileDest = applicationFilesPath + fileService.uploadFile(file);
        file.transferTo(new File(fileDest));
        Book book = bookMapper.toEntityWithBookImageCategoryAndAuthor(bookDto, File.separator + "files" + File.separator + fileService.uploadFile(file),
                categoryService.getCategoryById(bookDto.getCategoryId()).get(), authorService.getAuthorById(bookDto.getAuthorId()).get());
        return bookRepository.save(book);
    }

    @Override
    public Page<BookDto> getRandomBooks(Integer categoryId, Integer numberOfRandomBooks) {

        Random random = new Random();
        Long numberOfBooks;
        Pageable page;

        if(categoryId == null) {

            numberOfBooks = bookRepository.count();
            page = new PageRequest(random.nextInt(numberOfBooks.intValue() / numberOfRandomBooks + 1), numberOfRandomBooks);
            return bookMapper.toDtoPage(bookRepository.findAll(page), page);
        }

        FilterDto filterDto = new FilterDto();
        filterDto.setCategoryId(categoryId);
        numberOfBooks = bookRepository.count(BookSpecs.booksByCategory(filterDto));
        page= new PageRequest(random.nextInt(numberOfBooks.intValue() / numberOfRandomBooks + 1), numberOfRandomBooks);
        return bookMapper.toDtoPage(bookRepository.findAllByCategoryId(categoryId, page), page);
    }

    @Override
    public ItemCartDto getCartItemById(Long id) {

        Optional<Book> maybeBook = bookRepository.findById(id);

        if (!maybeBook.isPresent()) {
            return null;
        }
        Book book = maybeBook.get();

        return bookMapper.toCartItemDto(book);
    }
}
