package com.pitech.services;

import com.pitech.models.Author;

import java.util.Optional;

public interface AuthorService {
    Iterable<Author> listAllAuthors();

    Optional<Author> getAuthorById(Integer authorId);
}
