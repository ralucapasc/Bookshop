package com.pitech.controllers;

/**
 * Created by Pasc Raluca on 27.07.2017.
 */

import com.pitech.dtos.BookDto;
import com.pitech.dtos.FilterDto;
import com.pitech.dtos.ShoppingCartDto;
import com.pitech.enums.Availability;
import com.pitech.enums.FilterInterval;
import com.pitech.enums.Username;
import com.pitech.services.AuthorService;
import com.pitech.services.BookService;
import com.pitech.services.CartService;
import com.pitech.services.CartServiceAuthenticated;
import com.pitech.services.CategoryService;
import com.pitech.services.FileService;
import com.pitech.services.UserService;
import com.pitech.utils.CookieUtils;
import com.pitech.utils.EncodeDecodeCookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class BookController {

    private AuthorService authorService;
    private CategoryService categoryService;
    private BookService bookService;
    private FileService fileService;
    private UserService userService;
    private CartService cartService;
    private CartServiceAuthenticated cartServiceAuthenticated;
    private static final String SHOPPING_CART_DTO = "shoppingCartDto";

    @Value("${default.page.size}")
    Integer defaultPageSize;

    @Autowired
    public BookController(AuthorService authorService, CategoryService categoryService, BookService bookService,
                          FileService fileService, UserService userService, CartService cartService, CartServiceAuthenticated cartServiceAuthenticated) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.fileService = fileService;
        this.userService = userService;
        this.cartService = cartService;
        this.cartServiceAuthenticated = cartServiceAuthenticated;
    }

    @RequestMapping(value = "/addBooks", method = RequestMethod.GET)
    public String addBooks(Model model) {

        addAttributesAtModel(model, new ShoppingCartDto());
        model.addAttribute("book", new BookDto());

        return "addBooks";
    }

    @RequestMapping(value = "/addBooks", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String addBooksProcess(@Valid BookDto bookDto, BindingResult validationResult, Model model,
                                  @RequestParam("bookImage") MultipartFile file) throws IOException {

        addAttributesAtModel(model, new ShoppingCartDto());
        if (!validationResult.hasErrors()) {
            fileService.uploadFile(file);
            bookService.saveBook(bookDto, file);
        }
        model.addAttribute("book", bookDto);

        return "addBooks";
    }


    @RequestMapping(value = {"/showBooks"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, FilterDto filterDto, @ModelAttribute("book") @Valid BookDto bookDto,
                       @Valid Model model) throws Exception {

        Page<BookDto> bookPage = bookService.getAllBooksByCategoryAndFilters(filterDto, new PageRequest(filterDto.getPage() - 1, defaultPageSize));

        model.addAttribute("books", bookPage);
        model.addAttribute("filterDto", filterDto);

        addAttributesAtModel(model, getShoppingCart(request));

        if (filterDto.getCategoryId() == null) {
            Page<BookDto> randomBooks = bookService.getRandomBooks(filterDto.getCategoryId(), 6);
            model.addAttribute("books", randomBooks);
            return "home";
        }

        return "showBooks";
    }

    @RequestMapping(value = {"/book/{id}"}, method = RequestMethod.GET)
    public String showBookDetail(@PathVariable Long id, Model model, HttpServletRequest request) throws Exception {

        BookDto book = bookService.getBookDtoById(id);
        Page<BookDto> randomBooks = bookService.getRandomBooks(book.getCategoryId(), 4);

        model.addAttribute("book", book);
        model.addAttribute("randomBooks", randomBooks);
        addAttributesAtModel(model, getShoppingCart(request));

        return "bookDetails";
    }

    private void addAttributesAtModel(Model model, ShoppingCartDto shoppingCartDto) {

        String username = userService.getUsernameAuthenticated();

        model.addAttribute("filterInterval", Arrays.asList(FilterInterval.values()));
        model.addAttribute("availability", Arrays.asList(Availability.values()));
        model.addAttribute("categories", categoryService.listAllCategories());
        model.addAttribute("shoppingCartDto", shoppingCartDto);
        model.addAttribute("username", username);
        model.addAttribute("authors", authorService.listAllAuthors());
        model.addAttribute(SHOPPING_CART_DTO, shoppingCartDto);

    }

    private ShoppingCartDto getShoppingCart(HttpServletRequest request) throws Exception {

        if (userService.getUsernameAuthenticated().equals(Username.GUEST.getKey())) {
            String cookie = CookieUtils.getCookie(request).getValue();

            return EncodeDecodeCookieUtils.decode(cookie, ShoppingCartDto.class);
        }

        return cartServiceAuthenticated.getShoppingCartDto(cartService.getShoppingCart());
    }
}
